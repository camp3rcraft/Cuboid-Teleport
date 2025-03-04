package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.warp.WarpManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor {

    private final WarpManager warpManager;
    private final LocalizationManager localizationManager;

    public DelWarpCommand(WarpManager warpManager, LocalizationManager localizationManager) {
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

        if (!player.hasPermission("ctp.delwarp")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("usage_delwarp")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        String warpName = args[0];

        if (!warpManager.isOwner(player, warpName) && !player.hasPermission("ctp.admin")) {
            player.sendMessage(localizationManager.getMessage("no_permission_warp"));
            return true;
        }

        boolean deleted = warpManager.deleteWarp(warpName);

        if (warpManager.deleteWarp(warpName)) {
            player.sendMessage(localizationManager.getMessage("warp_deleted", "warp", warpName));
        } else {
            player.sendMessage(localizationManager.getMessage("warp_not_found", "warp", warpName));
        }

        return true;
    }
}
