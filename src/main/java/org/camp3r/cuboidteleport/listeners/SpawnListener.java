package org.camp3r.cuboidteleport.listeners;

import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListener implements Listener {
    private final SpawnManager spawnManager;

    public SpawnListener(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            Location spawn = spawnManager.getSpawnLocation();
            event.getPlayer().teleport(spawn);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!event.isBedSpawn() && !event.isAnchorSpawn()) {
            event.setRespawnLocation(spawnManager.getSpawnLocation());
        }
    }
}
