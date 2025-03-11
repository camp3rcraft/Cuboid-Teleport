package org.camp3r.cuboidteleport.commands;

import org.camp3r.cuboidteleport.homesystem.HomeSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class DelHomeTabCompleter implements TabCompleter {
    private final HomeSystem homeSystem;

    public DelHomeTabCompleter(HomeSystem homeSystem) {
        this.homeSystem = homeSystem;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            return homeSystem.getHomes((Player) sender);
        }
        return Collections.emptyList();
    }
}
