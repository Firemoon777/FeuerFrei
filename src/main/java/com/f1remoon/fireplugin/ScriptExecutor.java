package com.f1remoon.fireplugin;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.v1_15_R1.EntityLightning;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
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

public class ScriptExecutor implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equals("script-execute")) {
            if(!Tools.checkPermission(sender, "fireplugin.script.execute")) {
                return false;
            }
            File f = new File(Bukkit.getPluginManager().getPlugin("FirePlugin").getDataFolder().getAbsolutePath() + "/" + args[0]);
            if(!f.exists()) {
                sender.sendMessage("no such file");
                return true;
            }
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(f));
                String line = reader.readLine();
                while (line != null) {
                    if(line.startsWith("#") == false && line.length() > 0) {
                        String[] data = line.split(":", 2);
                        int delay = Integer.parseInt(data[0]);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getServer().dispatchCommand(sender, data[1].trim());
                            }
                        }.runTaskLater(Bukkit.getPluginManager().getPlugin("FirePlugin"), delay);
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();
        File f = Bukkit.getPluginManager().getPlugin("FirePlugin").getDataFolder();
        for(File file : f.listFiles()) {
            if(file.isFile()) {
                result.add(file.getName());
            }
        }
        return result;
    }
}
