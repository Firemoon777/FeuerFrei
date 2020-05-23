package com.f1remoon.fireplugin;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.GREEN + "Feuer Frei init done");
        getCommand("fire").setExecutor(new FireLine());
        getCommand("dispenser-fire").setExecutor(new FireLine());
        getCommand("explode").setExecutor(new Explosion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
