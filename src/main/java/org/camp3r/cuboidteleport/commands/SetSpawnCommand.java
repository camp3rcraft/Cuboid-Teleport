package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public SetSpawnCommand(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cТолько игроки могут использовать эту команду!");
            return true;
        }

        Player player = (Player) sender;
        spawnManager.saveSpawn(player.getLocation());
        player.sendMessage("§aТочка спавна установлена!");
        return true;
    }
}
