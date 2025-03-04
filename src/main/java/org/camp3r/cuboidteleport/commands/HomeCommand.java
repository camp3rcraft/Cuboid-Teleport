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

        // Проверяем права на команду
        if (!player.hasPermission("ctp.home")) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("no_permission")));
            return true;
        }

        // Проверяем кулдаун
        if (cooldownManager.isOnCooldown(player, "home")) {
            long timeLeft = cooldownManager.getCooldownTimeLeft(player, "home");
            player.sendMessage(localizationManager.getMessage("cooldown_active", "command", "/home", "time", String.valueOf(timeLeft)));
            return true;
        }

        // Определяем имя дома
        String homeName = args.length > 0 ? args[0] : null;

        if (homeName == null) {
            List<String> homes = homeSystem.getHomes(player);
            if (homes.isEmpty()) {
                player.sendMessage(localizationManager.getMessage("home_not_exist", "home", "default"));
                return true;
            } else if (homes.size() == 1) {
                homeName = homes.get(0); // Если у игрока один дом, используем его
            } else {
                player.sendMessage(localizationManager.getMessage("home_specify"));
                return true;
            }
        }

        // Проверяем, существует ли дом
        if (!homeSystem.hasHome(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("home_not_exist", "home", homeName));
            return true;
        }

        // Проверяем, владеет ли игрок этим домом
        if (!homeSystem.isOwner(player, homeName)) {
            player.sendMessage(localizationManager.getMessage("no_permission_home"));
            return true;
        }

        // Получаем координаты дома
        Location homeLocation = homeSystem.getHome(player, homeName);
        if (homeLocation == null) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("home_not_exist", "home", homeName)));
            return true;
        }

        // Телепортируем игрока
        player.teleport(homeLocation);
        player.sendMessage(localizationManager.getMessage("home_teleported", "home", homeName));

        // Устанавливаем кулдаун
        cooldownManager.setCooldown(player, "home");

        // Проигрываем звук телепортации
        String soundName = plugin.getConfig().getString("home_sound", "ENTITY_ENDERMAN_TELEPORT");
        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
        } catch (IllegalArgumentException e) {
            player.sendMessage(ColorUtil.color("&cInvalid sound in config: " + soundName));
        }

        return true;
    }
}
