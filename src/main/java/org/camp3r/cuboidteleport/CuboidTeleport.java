package org.camp3r.cuboidteleport;

import org.camp3r.cuboidteleport.commands.*;
import org.camp3r.cuboidteleport.homesystem.HomeSystem;
import org.camp3r.cuboidteleport.homesystem.LocalizationManager;
import org.camp3r.cuboidteleport.homesystem.TeleportManager;
import org.camp3r.cuboidteleport.listeners.SpawnListener;
import org.camp3r.cuboidteleport.spawn.SpawnManager;
import org.camp3r.cuboidteleport.utils.ColorUtil;
import org.camp3r.cuboidteleport.utils.CooldownManager;
import org.camp3r.cuboidteleport.warp.WarpManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CuboidTeleport extends JavaPlugin {

    private HomeSystem homeSystem;
    private LocalizationManager localizationManager;
    private TeleportManager teleportManager;
    private WarpManager warpManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        localizationManager = new LocalizationManager(this);
        homeSystem = new HomeSystem(this, localizationManager);
        teleportManager = new TeleportManager(this);
        warpManager = new WarpManager(getDataFolder());
        CooldownManager cooldownManager = new CooldownManager(this);
        SpawnManager spawnManager = new SpawnManager(this);
        getLogger().info(ColorUtil.color("&#6000FFC&#5E0AFFu&#5C14FFb&#591FFFo&#5729FFi&#5533FFd&#533DFFT&#5048FFe&#4E52FFl&#4C5CFFe&#4A66FFp&#4771FFo&#457BFFr&#4385FFt v2.0.0&r &#09ff00enabled.&r"));

        int teleportRadius = getConfig().getInt("teleport_radius", 1000);
        getCommand("sethome").setExecutor(new SetHomeCommand(homeSystem, localizationManager));
        getCommand("home").setExecutor(new HomeCommand(homeSystem, localizationManager, cooldownManager, this));
        getCommand("delhome").setExecutor(new DelHomeCommand(homeSystem, localizationManager));
        getCommand("ctp").setExecutor(new CtpCommand(this));
        getCommand("tpa").setExecutor(new TpaCommand(teleportManager, localizationManager, cooldownManager));
        getCommand("call").setExecutor(new CallCommand(teleportManager, localizationManager, cooldownManager));
        getCommand("tpaccept").setExecutor(new TpacceptCommand(teleportManager, localizationManager, this));
        getCommand("tpacancel").setExecutor(new TpacancelCommand(teleportManager, localizationManager));
        getCommand("tpdeny").setExecutor(new TpdenyCommand(teleportManager, localizationManager));
        getCommand("rtp").setExecutor(new RtpCommand(teleportRadius, localizationManager, cooldownManager, this));
        getCommand("tpr").setExecutor(new TprCommand(teleportRadius, localizationManager, cooldownManager, this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(warpManager, localizationManager));
        getCommand("warp").setExecutor(new WarpCommand(warpManager, localizationManager, cooldownManager, this));
        getCommand("delwarp").setExecutor(new DelWarpCommand(warpManager, localizationManager));
        getCommand("warps").setExecutor(new WarpsCommand(warpManager, localizationManager));
        getCommand("spawn").setExecutor(new SpawnCommand(spawnManager));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(spawnManager));
        getCommand("delspawn").setExecutor(new DelSpawnCommand(spawnManager));
        getServer().getPluginManager().registerEvents(new SpawnListener(spawnManager), this);
    }

    @Override
    public void onDisable() {
        if (homeSystem != null) {
            homeSystem.saveHomes();
        }
        getLogger().info(ColorUtil.color("&#6000FFC&#5E0AFFu&#5C14FFb&#591FFFo&#5729FFi&#5533FFd&#533DFFT&#5048FFe&#4E52FFl&#4C5CFFe&#4A66FFp&#4771FFo&#457BFFr&#4385FFt v2.0.0&r &#ff0000disabled.&r"));
    }

    public HomeSystem getHomeSystem() {
        return homeSystem;
    }

    public LocalizationManager getLocalizationManager() {
        return localizationManager;
    }

    public void reloadPlugin() {
        reloadConfig();
        localizationManager.reloadMessages();
        homeSystem.saveHomes();
        homeSystem.loadHomes();
        warpManager.loadWarps();
    }
}
