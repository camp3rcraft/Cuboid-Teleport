package org.camp3r.cuboidteleport.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Plugin plugin;
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public CooldownManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean isOnCooldown(Player player, String command) {
        UUID playerId = player.getUniqueId();
        if (!cooldowns.containsKey(playerId)) {
            return false;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (!playerCooldowns.containsKey(command)) {
            return false;
        }

        long cooldownEnd = playerCooldowns.get(command);
        return System.currentTimeMillis() < cooldownEnd;
    }

    public long getCooldownTimeLeft(Player player, String command) {
        UUID playerId = player.getUniqueId();
        if (!cooldowns.containsKey(playerId)) {
            return 0;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (!playerCooldowns.containsKey(command)) {
            return 0;
        }

        long cooldownEnd = playerCooldowns.get(command);
        long timeLeft = (cooldownEnd - System.currentTimeMillis()) / 1000;
        return Math.max(timeLeft, 0);
    }

    public void setCooldown(Player player, String command) {
        int cooldownTime = plugin.getConfig().getInt("cooldowns." + command, 0);
        if (cooldownTime <= 0) return;

        UUID playerId = player.getUniqueId();
        cooldowns.putIfAbsent(playerId, new HashMap<>());
        cooldowns.get(playerId).put(command, System.currentTimeMillis() + (cooldownTime * 1000L));
    }
}
