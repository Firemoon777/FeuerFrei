package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tools {
    public static boolean checkPermission(CommandSender sender, String permission) {
        if(sender instanceof Player) {
            if(!((Player)sender).hasPermission(permission)) {
                return false;
            }
            return true;
        } else if (sender instanceof BlockCommandSender) {
            return true;
        } else {
            return false;
        }
    }

    public static Location extractLocation(CommandSender sender) {
        if(sender instanceof Player) {
            return  ((Player)sender).getLocation();
        } else if (sender instanceof BlockCommandSender) {
            return ((BlockCommandSender)sender).getBlock().getLocation();
        } else {
            return null;
        }
    }

    public static World extractWorld(CommandSender sender) {
        return Tools.extractLocation(sender).getWorld();
    }

    public static Location parseLocationArgs(CommandSender sender, String xArg, String yArg, String zArg) {
        World w = Tools.extractWorld(sender);
        Double x = Double.parseDouble(xArg);
        Double y = Double.parseDouble(yArg);
        Double z = Double.parseDouble(zArg);

        return new Location(w, x, y ,z);
    }

    public static Location parseLocationArgs(CommandSender sender, String[] args) {
        return Tools.parseLocationArgs(sender, args[0], args[1], args[2]);
    }
}
