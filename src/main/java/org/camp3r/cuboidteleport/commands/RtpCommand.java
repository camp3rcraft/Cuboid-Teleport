package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RtpCommand implements CommandExecutor {

    private final int radius;
    private final LocalizationManager localizationManager;
    private final CuboidTeleport plugin;

    public RtpCommand(int radius, LocalizationManager localizationManager, CuboidTeleport plugin) {
        this.radius = radius;
        this.localizationManager = localizationManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.color(localizationManager.getMessage("only_players")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ctp.rtp")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        Location randomLocation = getRandomLocation(player);

        if (randomLocation != null) {
            player.teleport(randomLocation);

            // Воспроизведение звука после телепортации
            String soundName = plugin.getConfig().getString("rtp_sound", "ENTITY_PLAYER_LEVELUP");
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);

            player.sendMessage(ColorUtil.color(localizationManager.getMessage("rtp_success")));
        } else {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("rtp_fail")));
            localizationManager.playSound(player, "general_sound");
        }

        return true;
    }

    private Location getRandomLocation(Player player) {
        Random random = new Random();
        Location origin = player.getWorld().getSpawnLocation();
        Location randomLocation;
        int attempts = 0;

        do {
            if (attempts > 10) return null;
            double x = origin.getX() + (random.nextDouble() * radius * 2) - radius;
            double z = origin.getZ() + (random.nextDouble() * radius * 2) - radius;
            double y = player.getWorld().getHighestBlockYAt((int) x, (int) z);
            randomLocation = new Location(player.getWorld(), x, y, z);
            attempts++;
        } while (!isSafeLocation(randomLocation));

        return randomLocation;
    }

    private boolean isSafeLocation(Location location) {
        Material block = location.getBlock().getType();
        return block != Material.WATER && block != Material.LAVA && block != Material.CACTUS && block != Material.FIRE;
    }
}
