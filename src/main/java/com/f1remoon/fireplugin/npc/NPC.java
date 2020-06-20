package com.f1remoon.fireplugin.npc;

/**
 * Based on https://gist.github.com/602723113/bf9413568dd3005996a506f9210aa47b
 */

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class NPC {

    private String name;
    private Location location;
    private EntityPlayer npc;

    public NPC(Location l, String name) {
        this(l, name, null, null);
    }

    public NPC(Location l, String name, String texture, String signature) {
        this.name = name;
        this.location = l;

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(l.getWorld().getName())).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        this.npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));

        this.npc.setLocation(
                l.getX(),
                l.getY(),
                l.getZ(),
                l.getYaw(),
                l.getPitch()
        );

        if(texture != null && signature != null) {
            this.npc.getProfile().getProperties().put("textures", new Property("textures", texture, signature));
        }

    }

    public void spawn() {
        this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.npc));
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        this.setField(packet, "a", this.npc.getId());
        this.setField(packet, "b", this.npc.getProfile().getId());
        this.setField(packet, "c", location.getX());
        this.setField(packet, "d", location.getY());
        this.setField(packet, "e", location.getZ());
        this.setField(packet, "f", (byte) ((int) location.getYaw() * 256.0F / 360.0F));
        this.setField(packet, "g", (byte) ((int) location.getPitch() * 256.0F / 360.0F));
        this.sendPacket(packet);
    }

    public void move(double x, double y, double z) {
        this.npc.move(EnumMoveType.SELF, new Vec3D(x, y, z));
    }

    public void despawn() {
        this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.npc));
        this.sendPacket(new PacketPlayOutEntityDestroy(new int[] { this.npc.getId() }));
    }

    private void setField(Object obj, String field_name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(field_name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum NPCAnimation {

        SWING_MAIN_HAND(0),
        TAKE_DAMAGE(1),
        LEAVE_BED(2),
        SWING_OFFHAND(3),
        CRITICAL_EFFECT(4),
        MAGIC_CRITICAL_EFFECT(5);

        private int id;

        private NPCAnimation(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

    /*
     * set npc animation such as Swing arm ect. I recommend using method 'public void setAnimation(NPCAnimation animation)'
     */
    public void setAnimation(byte animation) {
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        this.setField(packet, "a", this.npc.getId());
        this.setField(packet, "b", animation);
        this.sendPacket(packet);
    }

    /*
     * set npc animation such as Swing arm ect.
     */
    public void setAnimation(NPCAnimation animation) {
        this.setAnimation((byte) animation.getId());
    }

    public enum Pose {
        STANDING,
        FALL_FLYING,
        SLEEPING,
        SWIMMING,
        SPIN_ATTACK,
        CROUCHING,
        DYING;
    }

    public void setAction(Pose pose) {
        DataWatcherObject obj = new DataWatcherObject<>(6, DataWatcherRegistry.s);

        DataWatcher dw = this.npc.getDataWatcher();
        EntityPose a = (EntityPose)dw.get(obj);
        dw.set(obj, EntityPose.valueOf(pose.name()));
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(this.npc.getId(), dw, false);
        this.sendPacket(packet);
    }

    public void setEquipment(EnumItemSlot slot, ItemStack item) {
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        this.setField(packet, "a", this.npc.getId());
        this.setField(packet, "b", slot);
        this.setField(packet, "c", item);
        this.sendPacket(packet);
    }


    private void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private void sendPacket(Packet<?> packet) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            this.sendPacket(packet, p);
        }
    }

}
