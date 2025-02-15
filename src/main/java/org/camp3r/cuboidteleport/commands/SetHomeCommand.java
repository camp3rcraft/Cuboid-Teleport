package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.HomeSystem;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    private final HomeSystem homeSystem;
    private final LocalizationManager localizationManager;

    public SetHomeCommand(HomeSystem homeSystem, LocalizationManager localizationManager) {
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
        Location location = player.getLocation();
        String homeName = (args.length > 0) ? args[0] : "default";
        homeSystem.setHome(player, homeName, location);
        player.sendMessage(localizationManager.getMessage("home_set", "home", homeName));
        localizationManager.playSound(player, "general_sound");
        return true;
    }
}
