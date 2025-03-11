package org.camp3r.cuboidteleport.homesystem;

import org.bukkit.Bukkit;
import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager {

    private final CuboidTeleport plugin;
    private final Map<UUID, UUID> teleportRequests = new HashMap<>();
    private final Map<UUID, BukkitRunnable> requestTimeouts = new HashMap<>();

    public TeleportManager(CuboidTeleport plugin) {
        this.plugin = plugin;
    }

    public void sendRequest(Player sender, Player target) {
        if (sender.equals(target)) {
            sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("no_self_request")));
            return;
        }

        public void sendTpahereRequest(Player sender, Player target) {
            requests.put(target.getUniqueId(), sender);
        }

        public boolean hasPendingRequest(Player player) {
            return requests.containsKey(player.getUniqueId());
        }

        UUID senderId = sender.getUniqueId();
        UUID targetId = target.getUniqueId();

        if (teleportRequests.containsKey(senderId)) {
            sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_pending")));
            return;
        }

        teleportRequests.put(senderId, targetId);
        sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_sent", "target", target.getName())));

        String requestReceivedMessage = plugin.getLocalizationManager().getMessage("request_received", "sender", sender.getName());
        requestReceivedMessage = ColorUtil.color(requestReceivedMessage);
        target.sendMessage(requestReceivedMessage);

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (teleportRequests.containsKey(senderId) && teleportRequests.get(senderId).equals(targetId)) {
                    teleportRequests.remove(senderId);
                    sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_timed_out")));
                    target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_timed_out")));
                }
            }
        };
        timeoutTask.runTaskLater(plugin, 1200); // 60 seconds timeout
        requestTimeouts.put(senderId, timeoutTask);
    }

    public void acceptRequest(Player target) {
        UUID targetId = target.getUniqueId();

        if (!teleportRequests.containsValue(targetId)) {
            target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("no_pending_request")));
            return;
        }

        UUID senderId = null;
        for (Map.Entry<UUID, UUID> entry : teleportRequests.entrySet()) {
            if (entry.getValue().equals(targetId)) {
                senderId = entry.getKey();
                break;
            }
        }

        if (senderId != null) {
            Player sender = Bukkit.getPlayer(senderId);
            if (sender != null) {
                sender.teleport(target.getLocation());
                sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_accepted")));
                target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_accepted")));
                teleportRequests.remove(senderId);
                requestTimeouts.get(senderId).cancel();
                requestTimeouts.remove(senderId);
            } else {
                target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("sender_offline")));
            }
        }
    }

    public void denyRequest(Player target) {
        UUID targetId = target.getUniqueId();

        if (!teleportRequests.containsValue(targetId)) {
            target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("no_pending_request")));
            return;
        }

        UUID senderId = null;
        for (Map.Entry<UUID, UUID> entry : teleportRequests.entrySet()) {
            if (entry.getValue().equals(targetId)) {
                senderId = entry.getKey();
                break;
            }
        }

        if (senderId != null) {
            Player sender = Bukkit.getPlayer(senderId);
            if (sender != null) {
                sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_denied")));
                target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_denied")));
                teleportRequests.remove(senderId);
                requestTimeouts.get(senderId).cancel();
                requestTimeouts.remove(senderId);
            } else {
                target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("sender_offline")));
            }
        }
    }

    public void cancelPendingRequest(Player sender) {
        UUID senderId = sender.getUniqueId();

        if (!teleportRequests.containsKey(senderId)) {
            sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("no_pending_request")));
            return;
        }

        UUID targetId = teleportRequests.remove(senderId);
        Player target = Bukkit.getPlayer(targetId);

        if (target != null) {
            target.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_cancelled", "sender", sender.getName())));
        }

        requestTimeouts.get(senderId).cancel();
        requestTimeouts.remove(senderId);
        sender.sendMessage(ColorUtil.color(plugin.getLocalizationManager().getMessage("request_cancelled", "sender", sender.getName())));
    }
}
