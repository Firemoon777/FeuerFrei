package com.f1remoon.fireplugin;

import com.f1remoon.fireplugin.npc.NPC;
import com.f1remoon.fireplugin.npc.NPCManager;
import com.f1remoon.fireplugin.tools.Tools;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Testme implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("testme")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;

                if("ChristophSchneider".equals(args[0])) {
                    NPCManager.createChristophSchneider(p.getLocation());
                } else if("PaulLander".equals(args[0])) {
                    NPCManager.createPaulLander(p.getLocation());
                } else if("TillLindemann".equals(args[0])) {
                    NPCManager.createTillLindemann(p.getLocation());
                } else if("RichardKruspe".equals(args[0])) {
                    NPCManager.createRichardKruspe(p.getLocation());
                } else if("ChristianLorenz".equals(args[0])) {
                    NPCManager.createChristianLorenz(p.getLocation());
                } else if("OliverRiedel".equals(args[0])) {
                    NPCManager.createOliverRiedel(p.getLocation());
                }
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
            return Arrays.asList(
              "ChristophSchneider",
              "PaulLander",
              "TillLindemann",
              "RichardKruspe",
              "ChristianLorenz",
              "OliverRiedel"
            );
        }
        return com;
    }
}
