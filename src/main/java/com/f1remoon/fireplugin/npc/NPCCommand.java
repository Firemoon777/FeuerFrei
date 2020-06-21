package com.f1remoon.fireplugin.npc;

import com.f1remoon.fireplugin.tools.Tools;
import net.minecraft.server.v1_15_R1.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

    public boolean moveNPC(CommandSender sender, String[] args) {
        NPC npc = NPCManager.getNPCByName(args[1]);
        if(npc == null) {
            sender.sendMessage("no such npc");
            return true;
        }
        float x = Float.parseFloat(args[2]);
        float y = Float.parseFloat(args[3]);
        float z = Float.parseFloat(args[4]);
        float speed = Float.parseFloat(args[5]);
        npc.move(x, y, z, speed);
        return true;
    }

    public boolean lookNPC(CommandSender sender, String[] args) {
        NPC npc = NPCManager.getNPCByName(args[1]);
        if(npc == null) {
            sender.sendMessage("no such npc");
            return true;
        }
        float yaw = Float.parseFloat(args[2]);
        float pitch = Float.parseFloat(args[3]);
        float speed = Float.parseFloat(args[4]);
        npc.look(yaw, pitch, speed);
        return true;
    }

    public boolean setNPC(CommandSender sender, String[] args) {
        NPC npc = NPCManager.getNPCByName(args[1]);
        if(npc == null) {
            sender.sendMessage("no such npc");
            return true;
        }
        ItemStack stack = new ItemStack(Material.matchMaterial(args[3], true),1);
        if(args[2].equals("main")) {
            npc.setEquipment(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(stack));
        } else {
            npc.setEquipment(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(stack));
        }
        return true;
    }

    public boolean animateNPC(CommandSender sender, String[] args) {
        // 0       1      2      3        4
        // animate <name> <type> <period> <times>
        NPC npc = NPCManager.getNPCByName(args[1]);
        if(npc == null) {
            sender.sendMessage("no such npc");
            return true;
        }
        int period = Integer.parseInt(args[3]);
        int n = Integer.parseInt(args[4]);
        new BukkitRunnable() {

            int i = 0;
            @Override
            public void run() {
                if(args[2].equals("guitar")) {
                    npc.setAnimation(NPC.NPCAnimation.SWING_MAIN_HAND);
                } else if(args[2].equals("drum")) {
                    if(i % 2 == 0) {
                        npc.setAnimation(NPC.NPCAnimation.SWING_MAIN_HAND);
                    } else {
                        npc.setAnimation(NPC.NPCAnimation.SWING_OFFHAND);
                    }
                } else if(args[2].equals("fire")) {
                    npc.setAnimation(NPC.NPCAnimation.SWING_MAIN_HAND);
                } else if(args[2].equals("crunch")) {
                    npc.setAction(NPC.Pose.CROUCHING);
                }

                i++;
                if(i >= n) {
                    if(args[2].equals("crunch")) {
                        npc.setAction(NPC.Pose.STANDING);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("FirePlugin"), 0, period);

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
                return moveNPC(sender, args);
            case "look":
                return lookNPC(sender, args);
            case "set":
                return setNPC(sender, args);
            case "animation":
                return animateNPC(sender, args);
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
              "look",
              "animation"
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
        } else if((args[0].equals("destroy") || args[0].equals("move") || args[0].equals("set") || args[0].equals("animation")) && args.length == 2) {
            List<String> names = new ArrayList<>();
            for(NPC npc : NPCManager.getNPCs()) {
                names.add(npc.getName());
            }
            return names;
        } else if(args[0].equals("look")) {
            if(args.length == 2) {
                List<String> names = new ArrayList<>();
                for(NPC npc : NPCManager.getNPCs()) {
                    names.add(npc.getName());
                }
                return names;
            } else if(args.length == 3) {
                NPC npc = NPCManager.getNPCByName(args[1]);
                if(npc == null) {
                    return Arrays.asList();
                }
                return Arrays.asList(
                        String.format("%.2f %.2f", npc.getNpc().yaw, npc.getNpc().pitch)
                );
            }
        }
        return Arrays.asList();
    }
}
