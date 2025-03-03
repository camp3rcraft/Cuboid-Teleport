package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelSpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public DelSpawnCommand(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ctp.delspawn")) {
            sender.sendMessage("§cУ вас нет прав!");
            return true;
        }

        spawnManager.deleteSpawn();
        sender.sendMessage("§cТочка спавна удалена!");
        return true;
    }
}
