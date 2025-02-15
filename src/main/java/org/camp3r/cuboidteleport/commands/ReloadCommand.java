package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.CuboidTeleport;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final CuboidTeleport plugin;
    private final LocalizationManager localizationManager;

    public ReloadCommand(CuboidTeleport plugin) {
        this.plugin = plugin;
        this.localizationManager = plugin.getLocalizationManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(localizationManager.getHelpMessage());
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("ctp.reload")) {
                sender.sendMessage(localizationManager.getMessage("no_permission"));
                return true;
            }
            plugin.reloadPlugin();
            sender.sendMessage(localizationManager.getMessage("config_reloaded"));
            return true;
        }
        sender.sendMessage(localizationManager.getHelpMessage());
        return true;
    }
}
