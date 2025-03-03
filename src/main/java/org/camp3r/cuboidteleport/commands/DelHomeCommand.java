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
        String homeName = args[0];
        if (!homeSystem.isOwner(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("no_permission_home"));
            return true;
        }

        boolean deleted = homeSystem.removeHome(player, homeName);

        if (homeSystem.removeHome(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("home_removed", "home", homeName));
            localizationManager.playSound(player, "general_sound");
        } else {
            player.sendMessage(localizationManager.getMessage("home_not_exist", "home", homeName));
            localizationManager.playSound(player, "general_sound");
        }
        return true;
    }
}
