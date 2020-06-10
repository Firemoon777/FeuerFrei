package com.f1remoon.fireplugin;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class FirePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.GREEN + "Feuer Frei init done");
        getCommand("fire").setExecutor(new FireLine());
        getCommand("dispenser-fire").setExecutor(new FireLine());
        getCommand("explode").setExecutor(new Explosion());
        getCommand("script-execute").setExecutor(new ScriptExecutor());
        getCommand("light").setExecutor(new Light());
        getCommand("testme").setExecutor(new Testme());

        File f = this.getDataFolder();
        if(!f.exists()) {
            getLogger().info("creating data folder");
            f.mkdirs();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
