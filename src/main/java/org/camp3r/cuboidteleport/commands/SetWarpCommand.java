package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.warp.WarpManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final WarpManager warpManager;
    private final LocalizationManager localizationManager;

    public SetWarpCommand(WarpManager warpManager, LocalizationManager localizationManager) {
        this.warpManager = warpManager;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.color(localizationManager.getMessage("only_players")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ctp.setwarp")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("usage_setwarp")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        String warpName = args[0];
        Location location = player.getLocation();

        if (warpManager.setWarp(warpName, location)) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("warp_set", "warp", warpName)));
            localizationManager.playSound(player, "general_sound");
        } else {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("warp_exists", "warp", warpName)));
            localizationManager.playSound(player, "general_sound");
        }

        return true;
    }
}
