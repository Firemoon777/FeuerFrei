package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
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
                Player p = (Player) sender;

                String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjY1NzQ1NDEyMywKICAicHJvZmlsZUlkIiA6ICI5MzUzMGFmZWNkMDI0MTY5YmM4YzkzZjYzNzU5ODc2ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJmMXJlbW9vbiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jNjExMWUyYjE0ZmIzNTZhNmU3NTE0MWVmNzhmYTBkMTBhODk1N2JkZGIxY2VhZWI3ZDQwY2U1YjI5ODZmYTk2IgogICAgfQogIH0KfQ==";
                String signature = "lGZQtOWnwt7M6waltyjkmo6f/QMleEwPpRaztHOmWPefYVRHXN1kD0XB3Vli1Pv34ZN1bLXLkg7xRKSIizA+7VGYCiDTDG0k9MP4ZJuLFuMj03QwiDYXh6dtMJAu48J+4SzTChS7bGR43hUfnxT7rSmA+WEuPJNdHWYCgeGqpD2SciZjZowlIRXFEjSmmtZzzE7N/0zx8wAHdTupxQLWGl57XcXtZZoCnzhHPjyVrFHT2DOcCsbt0f0wPqOtzV/kITWCrJNFq/VTO2BtXam2wgSS8Fzq5VMhwNEjZjp3aBxRA9wOfJxPN4sadlJ+w/V/uBqfUEB8B5lHMKRUpyQ5mphES1S92afbgmAawFH3bHFkqjDHfSF1Tit9MKiUNA5055/JvM2VPJHPltYLgMTNDcRzo5WKP/T1z0xoETfw3qcTeH4V3c22sMhhAYKR3Lo1cosJDUEsvanJlCYHDuf+pPezQZVmer2T5Wsuq/POR/AVbowCjkQJfLY+FWK0qav0WP5QAEexjwXunvNcJA9IQhh8hMt2uNCaY4dQSnELmjZJXLt3F+jE+oN58l0zEc50NgygKHdM5okXRtOKGcrGWCjzMlxSFJVZE4cOR7uisw/Sbn1sCO1yL3dilR3h4nfWHcEyswGiQqYZX6hN4I7mPKIkqze/UVygCglrNy4wp6M=";

                NPC.createNPC(p.getLocation(), args[0], texture, signature);
                p.sendMessage("NPC Created v7");
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
