package com.f1remoon.fireplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class FireLine implements TabExecutor {

    public void createFireLevel(Location l, int duration) {
        l.getWorld().getBlockAt(l).setType(Material.ACACIA_FENCE);
        for(int xOffset = -1; xOffset <= 1; xOffset++) {
            for(int zOffset = -1; zOffset <= 1; zOffset++) {
                Location fire = new Location(l.getWorld(), l.getX() + xOffset, l.getY(), l.getZ() + zOffset);
                if(xOffset == 0 && zOffset == 0) {
                    continue;
                }
                l.getWorld().getBlockAt(fire).setType(Material.FIRE);
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                l.getWorld().getBlockAt(l).setType(Material.AIR);
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("FirePlugin"), duration);
    }

    public void burn(Location start, int height, int duration) {
        new BukkitRunnable() {
            int levels = 0;
            @Override
            public void run() {
                Location l = start.clone();
                l.setY(l.getY() + levels);
                createFireLevel(l, duration);
                if(++levels >= height) this.cancel();
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("FirePlugin"), 0, 5);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("fire")) {
            World w;
            if(sender instanceof Player) {
                w = ((Player)sender).getWorld();
            } else if (sender instanceof BlockCommandSender) {
                w = ((BlockCommandSender)sender).getBlock().getWorld();
            } else {
                return false;
            }
            getLogger().info(String.format("%s", sender.getClass()));

            Double x = Double.parseDouble(args[0]);
            Double y = Double.parseDouble(args[1]);
            Double z = Double.parseDouble(args[2]);
            Integer height = Integer.parseInt(args[3]);
            Integer duration = Integer.parseInt(args[4]);
            Location l = new Location(w, x, y, z);

            burn(l, height, duration);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
