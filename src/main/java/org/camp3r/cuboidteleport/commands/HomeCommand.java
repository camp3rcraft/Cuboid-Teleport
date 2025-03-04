package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.homesystem.HomeSystem;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.CooldownManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeCommand implements CommandExecutor {

    private final HomeSystem homeSystem;
    private final LocalizationManager localizationManager;
    private final CooldownManager cooldownManager;
    private final CuboidTeleport plugin;

    public HomeCommand(HomeSystem homeSystem, LocalizationManager localizationManager, CooldownManager cooldownManager, CuboidTeleport plugin) {
        this.homeSystem = homeSystem;
        this.localizationManager = localizationManager;
        this.cooldownManager = cooldownManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.color(localizationManager.getMessage("only_players")));
            return true;
        }

        Player player = (Player) sender;
        String homeName = args.length > 0 ? args[0] : "home";
        Location homeLocation = homeSystem.getHome(player, homeName);

        if (args.length > 0) {
            homeName = args[0];
        } else {
            // Если аргумента нет, пытаемся выбрать дом автоматически
            List<String> homes = homeSystem.getHomes(player);
            if (homes.isEmpty()) {
                player.sendMessage(localizationManager.getMessage("home_not_exist"));
                return true;
            } else if (homes.size() == 1) {
                homeName = homes.get(0); // Если только один дом, выбираем его
            } else {
                player.sendMessage(localizationManager.getMessage("home_specify"));
                return true;
            }
        }

        if (!homeSystem.hasHome(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("home_not_exist", "home", homeName));
            return true;
        }

        if (cooldownManager.isOnCooldown(player, "home")) {
            long timeLeft = cooldownManager.getCooldownTimeLeft(player, "home");
            player.sendMessage(localizationManager.getMessage("cooldown_active", "command", "/home", "time", String.valueOf(timeLeft)));
            return true;
        }

        if (!homeSystem.isOwner(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("no_permission_home"));
            return true;
        }

        if (!player.hasPermission("ctp.home")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        if (homeLocation == null) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("home_not_exist", "%home%", homeName)));
            localizationManager.playSound(player, "general_sound");
            return true;
        }

        player.teleport(homeLocation);

        String soundName = plugin.getConfig().getString("home_sound", "ENTITY_ENDERMAN_TELEPORT");
        Sound sound = Sound.valueOf(soundName.toUpperCase());
        player.playSound(player.getLocation(), sound, 1.0F, 1.0F);

        player.sendMessage(ColorUtil.color(localizationManager.getMessage("home_teleported", "home", homeName)));
        return true;
    }
}
