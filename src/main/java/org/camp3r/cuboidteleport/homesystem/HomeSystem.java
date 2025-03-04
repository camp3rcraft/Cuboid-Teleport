package org.camp3r.cuboidteleport.homesystem;

import org.camp3r.cuboidteleport.CuboidTeleport;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeSystem {

    private final CuboidTeleport plugin;
    private final LocalizationManager localizationManager;
    private final Map<UUID, Map<String, Location>> homes;
    private final File homesFile;
    private final YamlConfiguration homesConfig;

    public HomeSystem(CuboidTeleport plugin, LocalizationManager localizationManager) {
        this.plugin = plugin;
        this.localizationManager = localizationManager;
        this.homes = new HashMap<>();
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.homesFile = new File(dataFolder, "homes.yml");
        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        loadHomes();
    }

    public LocalizationManager getLocalizationManager() {
        return localizationManager;
    }

    public void setHome(Player player, String homeName, Location location) {
        UUID playerId = player.getUniqueId();
        homes.computeIfAbsent(playerId, k -> new HashMap<>()).put(homeName, location);
        saveHomes();
    }

    public Location getHome(Player player, String homeName) {
        UUID playerId = player.getUniqueId();
        return homes.getOrDefault(playerId, new HashMap<>()).get(homeName);
    }

    public boolean removeHome(Player player, String homeName) {
        String path = player.getUniqueId().toString() + "." + homeName;

        if (!homesConfig.contains(path)) {
            return false; // Дом не найден
        }

        homesConfig.set(path, null);
        saveHomes();

        return !homesConfig.contains(path);
    }

    public boolean isOwner(Player player, String homeName) {
        return homesConfig.contains(player.getUniqueId().toString() + "." + homeName);
    }

    public boolean hasHome(Player player, String homeName) {
        return homesConfig.contains(player.getUniqueId().toString() + "." + homeName);
    }

    public void saveHomes() {
        for (Map.Entry<UUID, Map<String, Location>> entry : homes.entrySet()) {
            UUID playerId = entry.getKey();
            for (Map.Entry<String, Location> homeEntry : entry.getValue().entrySet()) {
                String homeName = homeEntry.getKey();
                Location location = homeEntry.getValue();
                homesConfig.set(playerId + "." + homeName + ".world", location.getWorld().getName());
                homesConfig.set(playerId + "." + homeName + ".x", location.getX());
                homesConfig.set(playerId + "." + homeName + ".y", location.getY());
                homesConfig.set(playerId + "." + homeName + ".z", location.getZ());
                homesConfig.set(playerId + "." + homeName + ".yaw", location.getYaw());
                homesConfig.set(playerId + "." + homeName + ".pitch", location.getPitch());
            }
        }
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHomes() {
        for (String key : homesConfig.getKeys(false)) {
            UUID playerId = UUID.fromString(key);
            Map<String, Object> playerHomes = homesConfig.getConfigurationSection(key).getValues(false);
            Map<String, Location> homeMap = new HashMap<>();
            for (String homeName : homesConfig.getConfigurationSection(key).getKeys(false)) {
                String world = homesConfig.getString(key + "." + homeName + ".world");
                double x = homesConfig.getDouble(key + "." + homeName + ".x");
                double y = homesConfig.getDouble(key + "." + homeName + ".y");
                double z = homesConfig.getDouble(key + "." + homeName + ".z");
                float yaw = (float) homesConfig.getDouble(key + "." + homeName + ".yaw");
                float pitch = (float) homesConfig.getDouble(key + "." + homeName + ".pitch");
                Location location = new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
                homeMap.put(homeName, location);
            }
            homes.put(playerId, homeMap);
        }
    }

    public List<String> getHomes(Player player) {
        String playerPath = player.getUniqueId().toString();

        if (!homesConfig.contains(playerPath)) {
            return new ArrayList<>();
        }

        return new ArrayList<>(homesConfig.getConfigurationSection(playerPath).getKeys(false));
    }
}
