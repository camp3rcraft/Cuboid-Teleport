package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelSpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;
    private final LocalizationManager localizationManager;

    public DelSpawnCommand(SpawnManager spawnManager, LocalizationManager localizationManager) {
        this.spawnManager = spawnManager;
        this.localizationManager = localizationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ctp.delspawn")) {
            sender.sendMessage(localizationManager.getMessage("no_permission"));
            return true;
        }

        if (!spawnManager.hasSpawn()) {
            sender.sendMessage(localizationManager.getMessage("spawn_not_set"));
            return true;
        }

        spawnManager.deleteSpawn();
        sender.sendMessage(localizationManager.getMessage("spawn_deleted"));
        return true;
    }
}
