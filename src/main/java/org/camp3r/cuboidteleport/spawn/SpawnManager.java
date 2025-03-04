package org.camp3r.cuboidteleport.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SpawnManager {
    private final Plugin plugin;
    private Location spawnLocation;
    private final File spawnFile;
    private final YamlConfiguration spawnConfig;

    public SpawnManager(Plugin plugin) {
        this.plugin = plugin;
        this.spawnFile = new File(plugin.getDataFolder() + "/data", "spawn.yml");
        this.spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);
        loadSpawn();
    }

    public void loadSpawn() {
        if (spawnConfig.contains("spawn")) {
            spawnLocation = (Location) spawnConfig.get("spawn");
        }
    }

    public void saveSpawn(Location location) {
        spawnLocation = location;
        spawnConfig.set("spawn", location);
        try {
            spawnConfig.save(spawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSpawn() {
        spawnLocation = null;
        spawnConfig.set("spawn", null);
        try {
            spawnConfig.save(spawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasSpawn() {
        return spawnConfig.contains("spawn");
    }

    public Location getSpawnLocation() {
        if (!spawnConfig.contains("spawn")) {
            return null; // Теперь возвращаем `null`, если спавн не установлен
        }
        return (Location) spawnConfig.get("spawn");
    }
}
