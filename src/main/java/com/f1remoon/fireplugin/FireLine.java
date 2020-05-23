package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Fence;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        if (command.getLabel().equalsIgnoreCase("dispenser-fire")) {
            if(!Tools.checkPermission(sender, "fireplugin.create.line.sequence")) {
                return false;
            }
            if(args.length < 6) {
                sender.sendMessage("Incorrect location");
                return true;
            }
            List<Location> dispensers = new ArrayList<>();

            Location l1 = Tools.parseLocationArgs(sender, args[0], args[1], args[2]);
            Location l2 = Tools.parseLocationArgs(sender, args[3], args[4], args[5]);

            Integer height;
            if(args.length > 6) {
                height = Integer.parseInt(args[6]);
            } else {
                height = 5;
            }
            Integer duration;
            if(args.length > 7) {
                duration = Integer.parseInt(args[7]);
            } else {
                duration = 10;
            }

            int x_min = Math.min(l1.getBlockX(), l2.getBlockX());
            int x_max = Math.max(l1.getBlockX(), l2.getBlockX());
            int y_min = Math.min(l1.getBlockY(), l2.getBlockY());
            int y_max = Math.max(l1.getBlockY(), l2.getBlockY());
            int z_min = Math.min(l1.getBlockZ(), l2.getBlockZ());
            int z_max = Math.max(l1.getBlockZ(), l2.getBlockZ());

            for(int x = x_min; x <= x_max; x++) {
                for(int y = y_min; y <= y_max; y++) {
                    for(int z = z_min; z <= z_max; z++) {
                        Location block = new Location(l1.getWorld(), x, y, z);
                        if(block.getBlock().getType() == Material.DISPENSER) {
                            if(((Directional)block.getBlock().getBlockData()).getFacing() == BlockFace.UP) {
                                block.add(0, 1, 0);
                                dispensers.add(block);
                            }
                        }
                    }
                }
            }

            for(Location l : dispensers) {
                burn(l, height, duration);
            }

            return true;

        } else if (command.getLabel().equalsIgnoreCase("fire")) {
            if(!Tools.checkPermission(sender, "fireplugin.create.line")) {
                return false;
            }
            if(args.length < 3) {
                sender.sendMessage("Incorrect location");
                return true;
            }
            Location l = Tools.parseLocationArgs(sender, args);

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

            burn(l, height, duration);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getLabel().equalsIgnoreCase("dispenser-fire")) {
            return ImmutableList.of();
        } else if (command.getLabel().equalsIgnoreCase("fire")) {
            Location l = Tools.extractLocation(sender);
            if (l == null) {
                return ImmutableList.of();
            }
            List<String> com = new ArrayList<String>();
            if (args.length == 1) {
                com.add(String.format("%2.0f", l.getX()));
                com.add(String.format("%2.0f %2.0f", l.getX(), l.getY()));
                com.add(String.format("%2.0f %2.0f %2.0f", l.getX(), l.getY(), l.getZ()));
            } else if (args.length == 2) {
                com.add(String.format("%2.0f", l.getY()));
                com.add(String.format("%2.0f %2.0f", l.getY(), l.getZ()));
            } else if (args.length == 3) {
                com.add(String.format("%2.0f", l.getZ()));
            } else if (args.length == 4) {
                com.add("5");
            } else if (args.length == 5) {
                com.add("10");
            }
            return com;
        }
        return ImmutableList.of();
    }
}
