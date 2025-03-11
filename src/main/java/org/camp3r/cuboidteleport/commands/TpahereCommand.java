package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.homesystem.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpahereCommand implements CommandExecutor {

    private final TeleportManager teleportManager;
    private final LocalizationManager localizationManager;

    public TpahereCommand(TeleportManager teleportManager, LocalizationManager localizationManager) {
        this.teleportManager = teleportManager;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(localizationManager.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ctp.tpahere")) {
            player.sendMessage(localizationManager.getMessage("no_permission"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(localizationManager.getMessage("usage_tpahere"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(localizationManager.getMessage("player_not_found"));
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(localizationManager.getMessage("no_self_request"));
            return true;
        }

        if (teleportManager.hasPendingRequest(target)) {
            player.sendMessage(localizationManager.getMessage("request_pending"));
            return true;
        }

        teleportManager.sendTpahereRequest(player, target);
        player.sendMessage(localizationManager.getMessage("request_sent", "target", target.getName()));
        target.sendMessage(localizationManager.getMessage("tpahere_received", "sender", player.getName()));

        return true;
    }
}
