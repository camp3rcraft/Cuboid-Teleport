package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.homesystem.HomeSystem;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.CooldownManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private final HomeSystem homeSystem;
    private final LocalizationManager localizationManager;
    private final CooldownManager cooldownManager;
    private final CuboidTeleport plugin;

    public HomeCommand(HomeSystem homeSystem, LocalizationManager localizationManager, CooldownManager cooldownManager, CuboidTeleport plugin) {
        this.homeSystem = homeSystem;
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

        if (cooldownManager.isOnCooldown(player, "home")) {
            long timeLeft = cooldownManager.getCooldownTimeLeft(player, "home");
            player.sendMessage(localizationManager.getMessage("cooldown_active", "command", "/home", "time", String.valueOf(timeLeft)));
            return true;
        }

        if (!player.hasPermission("ctp.home")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        String homeName = args.length > 0 ? args[0] : "home";
        Location homeLocation = homeSystem.getHome(player, homeName);

        if (homeLocation == null) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("home_not_exist", "%home%", homeName)));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        player.teleport(homeLocation);

        String soundName = plugin.getConfig().getString("home_sound", "ENTITY_ENDERMAN_TELEPORT");
        Sound sound = Sound.valueOf(soundName.toUpperCase());
        player.playSound(player.getLocation(), sound, 1.0F, 1.0F);

        player.sendMessage(ColorUtil.color(localizationManager.getMessage("home_teleported", "home", homeName)));
        return true;
    }
}
