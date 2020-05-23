package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Explosion implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("explode")) {
            if(args.length < 3) {
                sender.sendMessage("Incorrect location");
                return true;
            }
            Double x = Double.parseDouble(args[0]);
            Double y = Double.parseDouble(args[1]);
            Double z = Double.parseDouble(args[2]);
            Integer power;
            if(args.length > 3) {
                power = Integer.parseInt(args[3]);
            } else {
                power = 5;
            }

            World w;
            if(sender instanceof Player) {
                w = ((Player)sender).getWorld();
                if(!((Player)sender).hasPermission("fireplugin.create.explosion")) {
                    return false;
                }
            } else if (sender instanceof BlockCommandSender) {
                w = ((BlockCommandSender)sender).getBlock().getWorld();
            } else {
                return false;
            }

            Location l = new Location(w, x, y, z);

            w.createExplosion(l, power, false, false);

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
        }
        return com;
    }
}
