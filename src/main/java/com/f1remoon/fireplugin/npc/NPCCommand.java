package com.f1remoon.fireplugin.npc;

import com.f1remoon.fireplugin.tools.Tools;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPCCommand implements TabExecutor {

    public boolean createNPC(CommandSender sender, String[] args) {
        // 0      1      2   3   4   5     6
        // create <name> <x> <y> <z> <yaw> <pitch>
        if(args.length != 7) {
            return false;
        }
        Location l = Tools.parseLocationArgs(sender, args[2], args[3], args[4], args[5], args[6]);
        if("ChristophSchneider".equals(args[1])) {
            NPCManager.createChristophSchneider(l);
        } else if("PaulLander".equals(args[1])) {
            NPCManager.createPaulLander(l);
        } else if("TillLindemann".equals(args[1])) {
            NPCManager.createTillLindemann(l);
        } else if("RichardKruspe".equals(args[1])) {
            NPCManager.createRichardKruspe(l);
        } else if("ChristianLorenz".equals(args[1])) {
            NPCManager.createChristianLorenz(l);
        } else if("OliverRiedel".equals(args[1])) {
            NPCManager.createOliverRiedel(l);
        } else {
            if(args[1].length() > 16) {
                sender.sendMessage("Name cannot be larger than 16!");
                return true;
            }
            NPCManager.createNPC(l, args[1]);
        }
        return true;
    }
    
    public boolean listNPC(CommandSender sender, String[] args) {
        // list
        sender.sendMessage("Current NPCs:");
        for(NPC npc : NPCManager.getNPCs()) {
            sender.sendMessage(String.format("- %s", npc.getName()));
        }
        return true;
    }
    
    public boolean destroyNPC(CommandSender sender, String[] args) {
        // destroy name
        NPCManager.despawnByName(args[1]);
        sender.sendMessage("despawned");
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!Tools.checkPermission(sender, "fireplugin.npc")) {
            return false;
        }
        if(args.length == 0) {
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "create":
                return createNPC(sender, args);
            case "list":
                return listNPC(sender, args);
            case "destroy":
                return destroyNPC(sender, args);
            case "move":
                break;
            case "look":
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Location l = Tools.extractLocation(sender);
        if(args.length == 1) {
            return Arrays.asList(
              "create",
              "list",
              "destroy",
              "move",
              "look"
            );
        }
        if(args[0].equals("create")) {
            if(args.length == 2) {
                return Arrays.asList(
                        "ChristophSchneider",
                        "PaulLander",
                        "TillLindemann",
                        "RichardKruspe",
                        "ChristianLorenz",
                        "OliverRiedel",
                        "custom"
                );
            } else if(l != null && args.length == 3) {
                return Arrays.asList(
                  String.format("%d", l.getBlockX()),
                  String.format("%d %d", l.getBlockX(), l.getBlockY()),
                  String.format("%d %d %d", l.getBlockX(), l.getBlockY(), l.getBlockZ())
                );
            } else if(l != null && args.length == 6) {
                return Arrays.asList(
                        String.format("%.2f %.2f", l.getYaw(), l.getPitch())
                );
            }
        } else if(args[0].equals("destroy")) {
            List<String> names = new ArrayList<>();
            for(NPC npc : NPCManager.getNPCs()) {
                names.add(npc.getName());
            }
            return names;
        }
        return Arrays.asList();
    }
}
