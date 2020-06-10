package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.scheduler.BukkitRunnable;
import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.LightType;
import ru.beykerykt.lightapi.chunks.ChunkInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Light  implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("light")) {
            if(!Tools.checkPermission(sender, "fireplugin.light")) {
                return false;
            }
            if(args.length != 4 && args.length != 5) {
                sender.sendMessage("incorrect command");
                return true;
            }
            Location l = Tools.parseLocationArgs(sender, args[1], args[2], args[3]);
            int level = 15;
            if (args.length == 5) {
                level = Integer.parseInt(args[4]);
            }
            if(args[0].equalsIgnoreCase("create")) {
                LightAPI.createLight(l, LightType.BLOCK, level, true);
                for(ChunkInfo info : LightAPI.collectChunks(l, LightType.BLOCK, level)) {
                    LightAPI.updateChunk(info, LightType.BLOCK);
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                LightAPI.deleteLight(l, LightType.BLOCK, true);
                for(ChunkInfo info : LightAPI.collectChunks(l, LightType.BLOCK, level)) {
                    LightAPI.updateChunk(info, LightType.BLOCK);
                }
            } else {
                sender.sendMessage("incorrect mode. create or remove possible");
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
            com.add("create");
            com.add("remove");
        } else if(args.length == 2) {
            com.add(String.format("%2.0f", l.getX()));
            com.add(String.format("%2.0f %2.0f", l.getX(), l.getY()));
            com.add(String.format("%2.0f %2.0f %2.0f", l.getX(), l.getY(), l.getZ()));
        } else if(args.length == 3) {
            com.add(String.format("%2.0f", l.getY()));
            com.add(String.format("%2.0f %2.0f", l.getY(), l.getZ()));
        } else if(args.length == 4) {
            com.add(String.format("%2.0f", l.getZ()));
        } else if(args.length == 5) {
            com.add("15");
        }
        return com;
    }
}
