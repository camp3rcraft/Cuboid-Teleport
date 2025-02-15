package org.camp3r.cuboidteleport.homesystem;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocalizationManager {

    private final CuboidTeleport plugin;
    private final Map<String, String> messages;
    private String messagePrefix;

    public LocalizationManager(CuboidTeleport plugin) {
        this.plugin = plugin;
        this.messages = new HashMap<>();
        reloadMessages();
    }

    public void reloadMessages() {
        messages.clear();
        String locale = plugin.getConfig().getString("locale", "en");
        messagePrefix = ColorUtil.color(plugin.getConfig().getString("message_prefix", "&#6000FF&lC&#5E0AFF&lu&#5C14FF&lb&#591FFF&lo&#5729FF&li&#5533FF&ld&#533DFF&lT&#5048FF&le&#4E52FF&ll&#4C5CFF&le&#4A66FF&lp&#4771FF&lo&#457BFF&lr&#4385FF&lt&r &7|&r "));

        File langFolder = new File(plugin.getDataFolder(), "lang");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }
        copyDefaultLangFile("msg_en.yml");
        copyDefaultLangFile("msg_ru.yml");

        File langFile = new File(langFolder, "msg_" + locale + ".yml");
        if (!langFile.exists()) {
            plugin.getLogger().warning("Locale file not found: " + langFile.getName());
            locale = "en"; // fallback to default
            langFile = new File(langFolder, "msg_en.yml");
        }
        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
        for (String key : langConfig.getKeys(false)) {
            messages.put(key, langConfig.getString(key));
        }
    }

    private void copyDefaultLangFile(String fileName) {
        File langFile = new File(plugin.getDataFolder(), "lang/" + fileName);
        if (!langFile.exists()) {
            plugin.saveResource("lang/" + fileName, false);
        }
    }

    public String getMessage(String key, String... replacements) {
        String message = messages.getOrDefault(key, key);
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace("%" + replacements[i] + "%", replacements[i + 1]);
            }
        }
        return ColorUtil.color(messagePrefix + message);
    }

    public String getRawMessage(String key, String... replacements) {
        String message = messages.getOrDefault(key, key);
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace("%" + replacements[i] + "%", replacements[i + 1]);
            }
        }
        return message;
    }

    public String getHelpMessage() {
        return getMessage("help_message");
    }

    public void playSound(Player player, String soundKey) {
        String soundName = plugin.getConfig().getString(soundKey, "ENTITY_FISHING_BOBBER_THROW");
        Sound sound = Sound.valueOf(soundName.toUpperCase());
        player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
    }
}
