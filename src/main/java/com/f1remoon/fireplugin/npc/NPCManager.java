package com.f1remoon.fireplugin.npc;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPCManager {

//    private static List<EntityPlayer> NPC = new ArrayList<>();
//
//    public static int createNPC() {
//
//        addNPCPacket(npc);
//
//        NPC.add(npc);
//
//        return npc.getId();
//    }
//
//    public static void addNPCPacket(EntityPlayer npc) {
//        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
//        sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
//        sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte)(npc.yaw * 256 / 360)));
//    }
//
//    private Object getField(Object obj, String field_name) {
//        try {
//            Field field = obj.getClass().getDeclaredField(field_name);
//            field.setAccessible(true);
//            return field.get(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void setField(Object obj, String field_name, Object value) {
//        try {
//            Field field = obj.getClass().getDeclaredField(field_name);
//            field.setAccessible(true);
//            field.set(obj, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void sendPacket(Packet<?> packet, Player player) {
//        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
//    }
//
//    private static void sendPacket(Packet<?> packet) {
//        for (Player p : Bukkit.getOnlinePlayers()) {
//            sendPacket(packet, p);
//        }
//    }
//
//    enum NPCAnimation {
//
//        SWING_MAIN_HAND(0),
//        TAKE_DAMAGE(1),
//        LEAVE_BED(2),
//        SWING_OFFHAND(3),
//        CRITICAL_EFFECT(4),
//        MAGIC_CRITICAL_EFFECT(5);
//
//        private int id;
//
//        private NPCAnimation(int id) {
//            this.id = id;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//    }
//
//    /*
//     * set npc animation such as Swing arm ect. I recommend using method 'public void setAnimation(NPCAnimation animation)'
//     */
//    public static void setAnimation(int id, byte animation) {
//        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
//        setField(packet, "a", id);
//        setField(packet, "b", animation);
//        sendPacket(packet);
//    }
//
//    /*
//     * set npc animation such as Swing arm ect.
//     */
//    public static void setAnimation(int id, NPCAnimation animation) {
//        setAnimation(id, (byte) animation.getId());
//    }
//
//    /*
//     * delete the npc from the server.
//     */
//    public static void destroy(int id) {
//        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { id });
//        sendPacket(packet);
//        for(EntityPlayer p : NPC) {
//            if (p.getId() == id) {
//                sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, p));
//                NPC.remove(p);
//            }
//        }
//    }
}
