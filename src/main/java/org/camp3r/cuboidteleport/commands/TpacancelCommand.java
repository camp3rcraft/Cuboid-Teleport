package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.TeleportManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacancelCommand implements CommandExecutor {

    private final TeleportManager teleportManager;
    private final LocalizationManager localizationManager;

    public TpacancelCommand(TeleportManager teleportManager, LocalizationManager localizationManager) {
        this.teleportManager = teleportManager;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.color(localizationManager.getMessage("only_players")));
            return true;
        }

        Player player = (Player) sender;
        teleportManager.cancelPendingRequest(player);
        return true;
    }
}
