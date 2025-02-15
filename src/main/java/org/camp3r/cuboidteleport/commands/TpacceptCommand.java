package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.TeleportManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TpacceptCommand implements CommandExecutor {

    private final TeleportManager teleportManager;
    private final LocalizationManager localizationManager;
    private final Plugin plugin;

    public TpacceptCommand(TeleportManager teleportManager, LocalizationManager localizationManager, Plugin plugin) {
        this.teleportManager = teleportManager;
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
        teleportManager.acceptRequest(player);

        String soundName = plugin.getConfig().getString("tpa_sound", "ENTITY_ENDERMAN_TELEPORT");
        Sound sound = Sound.valueOf(soundName.toUpperCase());
        player.playSound(player.getLocation(), sound, 1.0F, 1.0F);

        return true;
    }
}
