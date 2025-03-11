package org.camp3r.cuboidteleport.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final String modrinthUrl = "https://api.modrinth.com/v2/project/cuboidteleport/version";
    private final String githubUrl = "https://api.github.com/repos/camp3rcraft/Cuboid-Teleport/releases/latest";
    private final boolean updateCheckEnabled;
    private final String lang;

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.updateCheckEnabled = config.getBoolean("update_check", true);
        this.lang = config.getString("language", "en"); // Определяем язык
    }

    public void checkForUpdates() {
        if (!updateCheckEnabled) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String latestVersion = getLatestVersion(modrinthUrl);
            if (latestVersion == null) {
                latestVersion = getLatestVersion(githubUrl);
            }
            if (latestVersion != null && !latestVersion.equalsIgnoreCase(plugin.getDescription().getVersion())) {
                String message = getUpdateMessage(latestVersion);
                Bukkit.getConsoleSender().sendMessage(ColorUtil.color(message));

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.isOp()) {
                        player.sendMessage(ColorUtil.color(message));
                    }
                }
            }
        });
    }

    private String getLatestVersion(String urlString) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return parseVersion(response.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private String parseVersion(String json) {
        if (json.contains("\"version_number\"")) {
            return json.split("\"version_number\":\"")[1].split("\"")[0];
        } else if (json.contains("\"tag_name\"")) {
            return json.split("\"tag_name\":\"")[1].split("\"")[0];
        }
        return null;
    }

    private String getUpdateMessage(String latestVersion) {
        if ("ru".equalsIgnoreCase(lang)) {
            return "&#34c3ebДоступна новая версия &#6000FF&lC&#5E0AFF&lu&#5C14FF&lb&#591FFF&lo&#5729FF&li&#5533FF&ld&#533DFF&lT&#5048FF&le&#4E52FF&ll&#4C5CFF&le&#4A66FF&lp&#4771FF&lo&#457BFF&lr&#4385FF&lt&r&#34c3eb: &n" + latestVersion + "&r#34c3eb! Скачайте с Modrinth: https://modrinth.com/plugin/cuboidteleport";
        } else {
            return "&#34c3ebNew version of &#6000FF&lC&#5E0AFF&lu&#5C14FF&lb&#591FFF&lo&#5729FF&li&#5533FF&ld&#533DFF&lT&#5048FF&le&#4E52FF&ll&#4C5CFF&le&#4A66FF&lp&#4771FF&lo&#457BFF&lr&#4385FF&lt&r &#34c3ebavailable: &n" + latestVersion + "&r&#34c3eb! Download from Modrinth: https://modrinth.com/plugin/cuboidteleport";
        }
    }
}
