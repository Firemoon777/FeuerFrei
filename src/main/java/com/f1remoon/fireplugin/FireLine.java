package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;
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

import java.util.ArrayList;
import java.util.List;

public class FireLine implements TabExecutor {

    public void createFireLevel(Location l, int duration, boolean simple) {
        if(simple) {
            l.getWorld().getBlockAt(l).setType(Material.FIRE);
        } else {
            l.getWorld().getBlockAt(l).setType(Material.ACACIA_FENCE);
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    Location fire = new Location(l.getWorld(), l.getX() + xOffset, l.getY(), l.getZ() + zOffset);
                    if (xOffset == 0 && zOffset == 0) {
                        continue;
                    }
                    l.getWorld().getBlockAt(fire).setType(Material.FIRE);
                }
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
                createFireLevel(l, duration, levels == 0);
                if(++levels >= height) this.cancel();
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("FirePlugin"), 0, 5);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("fire")) {
            if(args.length < 3) {
                sender.sendMessage("Incorrect location");
                return true;
            }
            Double x = Double.parseDouble(args[0]);
            Double y = Double.parseDouble(args[1]);
            Double z = Double.parseDouble(args[2]);
            Integer height;
            if(args.length > 3) {
                height = Integer.parseInt(args[3]);
            } else {
                height = 5;
            }
            Integer duration;
            if(args.length > 4) {
                duration = Integer.parseInt(args[4]);
            } else {
                duration = 10;
            }

            World w;
            if(sender instanceof Player) {
                w = ((Player)sender).getWorld();
                if(!((Player)sender).hasPermission("fireplugin.create.line")) {
                    return false;
                }
            } else if (sender instanceof BlockCommandSender) {
                w = ((BlockCommandSender)sender).getBlock().getWorld();
            } else {
                return false;
            }

            Location l = new Location(w, x, y, z);
            burn(l, height, duration);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> com = new ArrayList<String>();
        Location l;
        if(sender instanceof Player) {
            l = ((Player)sender).getLocation();
        } else if (sender instanceof BlockCommandSender) {
            l = ((BlockCommandSender)sender).getBlock().getLocation();
        } else {
            return ImmutableList.of();
        }
        if(args.length == 1) {
            com.add(String.format("%2.0f", l.getX()));
            com.add(String.format("%2.0f %2.0f", l.getX(), l.getY()));
            com.add(String.format("%2.0f %2.0f %2.0f", l.getX(), l.getY(), l.getZ()));
        } else if(args.length == 2) {
            com.add(String.format("%2.0f", l.getY()));
            com.add(String.format("%2.0f %2.0f", l.getY(), l.getZ()));
        } else if(args.length == 3) {
            com.add(String.format("%2.0f", l.getZ()));
        } else if(args.length == 4) {
            com.add("5");
        } else if(args.length == 5) {
            com.add("10");
        }
        return com;
    }
}
