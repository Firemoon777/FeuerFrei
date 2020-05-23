package com.f1remoon.fireplugin;

import org.bukkit.*;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class FirePlugin extends JavaPlugin{

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.GREEN + "Feuer Frei init done");
        getCommand("fire").setExecutor(new FireLine());
        getCommand("explode").setExecutor(new Explosion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
