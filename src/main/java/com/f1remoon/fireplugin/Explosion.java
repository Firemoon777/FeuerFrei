package com.f1remoon.fireplugin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class Explosion implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("explode")) {
            World w;
            if(sender instanceof Player) {
                w = ((Player)sender).getWorld();
            } else if (sender instanceof BlockCommandSender) {
                w = ((BlockCommandSender)sender).getBlock().getWorld();
            } else {
                return false;
            }
            Double x = Double.parseDouble(args[0]);
            Double y = Double.parseDouble(args[1]);
            Double z = Double.parseDouble(args[2]);
            Integer power = Integer.parseInt(args[3]);
            Location l = new Location(w, x, y, z);

            w.createExplosion(l, power, false, false);

            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
