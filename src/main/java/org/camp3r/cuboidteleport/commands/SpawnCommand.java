package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;
    private final LocalizationManager localizationManager;

    public SpawnCommand(SpawnManager spawnManager, LocalizationManager localizationManager) {
        this.spawnManager = spawnManager;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(localizationManager.getMessage("only_players"));
            return true;
        }

        Player player = (Player) sender;
        Location spawn = spawnManager.getSpawnLocation();
        if (spawn == null) {
            player.sendMessage(localizationManager.getMessage("spawn_not_set"));
            return true;
        }
        player.teleport(spawn);
        player.sendMessage(localizationManager.getMessage("spawn_teleported"));
        return true;
    }
}
