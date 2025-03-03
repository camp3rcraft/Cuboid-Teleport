package org.camp3r.cuboidteleport.warp;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarpManager {

    private final File warpsFile;
    private final YamlConfiguration warpsConfig;
    private final Map<String, Location> warps;

    public WarpManager(File dataFolder) {
        warpsFile = new File(dataFolder, "warps.yml");
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
        warps = new HashMap<>();
        loadWarps();
    }

    public void loadWarps() {
        warps.clear();
        for (String warpName : warpsConfig.getKeys(false)) {
            Location location = warpsConfig.getLocation(warpName);
            if (location != null) {
                warps.put(warpName, location);
            }
        }
    }

    public void saveWarps() {
        for (Map.Entry<String, Location> entry : warps.entrySet()) {
            warpsConfig.set(entry.getKey(), entry.getValue());
        }
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean setWarp(String warpName, Location location) {
        if (warps.containsKey(warpName)) {
            return false;
        }
        warps.put(warpName, location);
        saveWarps();
        return true;
    }

    public Location getWarp(String warpName) {
        return warps.get(warpName);
    }

    public boolean deleteWarp(String warpName) {
        if (!warps.containsKey(warpName)) {
            return false;
        }
        warps.remove(warpName);
        warpsConfig.set(warpName, null);
        saveWarps();
        return true;
    }

    public boolean warpExists(String warpName) {
        return warps.containsKey(warpName);
    }

    public boolean isOwner(Player player, String warpName) {
        String ownerPath = "warps." + warpName + ".owner";
        return warpsConfig.getString(ownerPath, "").equals(player.getUniqueId().toString());
    }


    public Set<String> getWarpNames() {
        return warps.keySet();
    }
}
