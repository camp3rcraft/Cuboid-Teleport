package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.TeleportManager;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.camp3r.cuboidteleport.utils.CooldownManager;

public class TpaCommand implements CommandExecutor {

    private final TeleportManager teleportManager;
    private final LocalizationManager localizationManager;
    private final CooldownManager cooldownManager;

    public TpaCommand(TeleportManager teleportManager, LocalizationManager localizationManager, CooldownManager cooldownManager) {
        this.teleportManager = teleportManager;
        this.localizationManager = localizationManager;
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.color(localizationManager.getMessage("only_players")));
            return true;
        }

        Player player = (Player) sender;

        if (cooldownManager.isOnCooldown(player, "tpa")) {
            long timeLeft = cooldownManager.getCooldownTimeLeft(player, "tpa");
            player.sendMessage(localizationManager.getMessage("cooldown_active", "command", "/tpa", "time", String.valueOf(timeLeft)));
            return true;
        }


        if (args.length != 1) {
            player.sendMessage(ColorUtil.color(localizationManager.getMessage("usage_tpa")));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            player.sendMessage(localizationManager.getMessage("player_not_found"));
            return true;
        }

        teleportManager.sendRequest(player, target);
        return true;
    }
}
