package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.TeleportManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.camp3r.cuboidteleport.utils.ColorUtil;

public class CallCommand implements CommandExecutor {

    private final TeleportManager teleportManager;
    private final LocalizationManager localizationManager;

    public CallCommand(TeleportManager teleportManager, LocalizationManager localizationManager) {
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

        if (args.length != 1) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("usage_call")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("player_not_found")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        teleportManager.sendRequest(player, target);
        return true;
    }
}
