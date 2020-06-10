package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Testme implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("testme")) {
            if(sender instanceof Player) {
                Player p = (Player)sender;
                Bukkit.
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Location l = Tools.extractLocation(sender);
        if(l == null) {
            return ImmutableList.of();
        }
        List<String> com = new ArrayList<String>();
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
