package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public SpawnCommand(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cТолько игроки могут использовать эту команду!");
            return true;
        }

        Player player = (Player) sender;
        Location spawn = spawnManager.getSpawnLocation();
        player.teleport(spawn);
        player.sendMessage("§aВы были телепортированы на спавн!");
        return true;
    }
}
