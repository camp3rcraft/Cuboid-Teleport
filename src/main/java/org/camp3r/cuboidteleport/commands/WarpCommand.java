package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.utils.CooldownManager;
import org.camp3r.cuboidteleport.warp.WarpManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    private final WarpManager warpManager;
    private final LocalizationManager localizationManager;
    private final CooldownManager cooldownManager;
    private final CuboidTeleport plugin;

    public WarpCommand(WarpManager warpManager, LocalizationManager localizationManager, CooldownManager cooldownManager, CuboidTeleport plugin) {
        this.warpManager = warpManager;
        this.localizationManager = localizationManager;
        this.cooldownManager = cooldownManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.color(localizationManager.getMessage("only_players")));
            return true;
        }

        Player player = (Player) sender;

        if (cooldownManager.isOnCooldown(player, "warp")) {
            long timeLeft = cooldownManager.getCooldownTimeLeft(player, "warp");
            player.sendMessage(localizationManager.getMessage("cooldown_active", "command", "/warp", "time", String.valueOf(timeLeft)));
            return true;
        }

        if (!player.hasPermission("ctp.warp")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("usage_warp")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        String warpName = args[0];
        Location location = warpManager.getWarp(warpName);

        if (location == null) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("warp_not_found", "warp", warpName)));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        player.teleport(location);
        cooldownManager.setCooldown(player, "warp");

        // Воспроизведение звука после телепортации
        String soundName = plugin.getConfig().getString("warp_sound", "ENTITY_ENDERMAN_TELEPORT");
        Sound sound = Sound.valueOf(soundName.toUpperCase());
        player.playSound(player.getLocation(), sound, 1.0F, 1.0F);

        player.sendMessage(ColorUtil.color(localizationManager.getMessage("warp_teleport", "warp", warpName)));
        return true;
    }
}
