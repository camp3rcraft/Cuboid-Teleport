package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.warp.WarpManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class WarpsCommand implements CommandExecutor {

    private final WarpManager warpManager;
    private final LocalizationManager localizationManager;

    public WarpsCommand(WarpManager warpManager, LocalizationManager localizationManager) {
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

        if (!player.hasPermission("ctp.warps")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            return true;
        }

        Set<String> warpNames = warpManager.getWarpNames();

        if (warpNames.isEmpty()) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_warps")));
            return true;
        }

        String warpsList = warpNames.stream().collect(Collectors.joining(", "));
        player.sendMessage(ColorUtil.color(localizationManager.getMessage("warps_list", "warps", warpsList)));
        return true;
    }
}
