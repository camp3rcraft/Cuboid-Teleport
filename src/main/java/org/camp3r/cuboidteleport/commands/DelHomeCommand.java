package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.HomeSystem;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {

    private final HomeSystem homeSystem;
    private final LocalizationManager localizationManager;

    public DelHomeCommand(HomeSystem homeSystem, LocalizationManager localizationManager) {
        this.homeSystem = homeSystem;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(localizationManager.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(localizationManager.getMessage("usage_delhome"));
            return true;
        }

        String homeName = args[0];

        if (!homeSystem.hasHome(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("home_not_exist", "home", homeName));
            return true;
        }

        if (!homeSystem.isOwner(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("no_permission_home"));
            return true;
        }

        boolean deleted = homeSystem.removeHome(player, homeName);

        if (deleted) {
            player.sendMessage(localizationManager.getMessage("home_removed", "home", homeName));
        } else {
            player.sendMessage(localizationManager.getMessage("home_not_exist", "home", homeName));
        }
        return true;
    }
}
