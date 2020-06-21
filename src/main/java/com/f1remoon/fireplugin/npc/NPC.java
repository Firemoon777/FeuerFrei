package com.f1remoon.fireplugin.npc;

/**
 * Based on https://gist.github.com/602723113/bf9413568dd3005996a506f9210aa47b
 */

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        this.npc.setNoGravity(false);

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
        this.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        this.sendPacket(new PacketPlayOutEntityHeadRotation(this.npc, (byte)(this.npc.yaw * 256 / 360)));
    }

    BukkitRunnable movement = null;
    public void move(final float x, final float y, final float z, final float speed) {
        movement = new BukkitRunnable() {
            final private float e = 0.001f;

            private float estimate_x = x;
            private float estimate_y = y;
            private float estimate_z = z;
            final private float velocity = speed;

            private float sign(float a) {
                if(a < 0) {
                    return -1;
                } else {
                    return 1;
                }
            }

            @Override
            public void run() {
                float x_offset = sign(estimate_x) * Math.min(velocity, Math.abs(estimate_x));
                float y_offset = sign(estimate_y) * Math.min(velocity, Math.abs(estimate_y));
                float z_offset = sign(estimate_z) * Math.min(velocity, Math.abs(estimate_z));

                PacketPlayOutEntity.PacketPlayOutRelEntityMove packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(
                        npc.getId(),
                        (short)Math.floor(x_offset*32*128),
                        (short)Math.floor(y_offset*32*128),
                        (short)Math.floor(z_offset*32*128),
                        true
                );
                sendPacket(packet);

                estimate_x += -1 * x_offset;
                estimate_y += -1 * y_offset;
                estimate_z += -1 * z_offset;
                if (Math.abs(estimate_x) < e && Math.abs(estimate_y) < e && Math.abs(estimate_z) < e) {
                    this.cancel();
                }
            }
        };
        movement.runTaskTimer(Bukkit.getPluginManager().getPlugin("FirePlugin"), 0, 1);

    }

    public void cancelMove() {
        if(this.movement == null || this.movement.isCancelled()) {
            return;
        }
        this.movement.cancel();
    }

    BukkitRunnable lookMovement = null;
    public void look(final float yaw, final float pitch, final float speed) {
        lookMovement = new BukkitRunnable() {
            final float e = 0.1f;

            float current_yaw = npc.yaw;
            float current_pitch = npc.pitch;

            private float sign(float a) {
                if(a < 0) {
                    return -1;
                } else {
                    return 1;
                }
            }

            @Override
            public void run() {
                float pitch_degrees = npc.pitch * 360 / 256.f;

                float yaw_offset = sign(yaw - current_yaw) * Math.min(speed, Math.abs(yaw - current_yaw));
                current_yaw += yaw_offset;

                float pitch_offset = sign(pitch - current_pitch) * Math.min(speed, Math.abs(pitch - current_pitch));
                current_pitch += pitch_offset;

                Bukkit.getLogger().info(String.format("yaw = %.2f ( %.2f )", npc.yaw, yaw_offset));
                Bukkit.getLogger().info(String.format("pitch = %.2f ( %.2f )", npc.pitch, pitch_offset));
                PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(
                        npc.getId(),
                        (byte)(current_yaw * 256.f / 360),
                        (byte)(current_pitch * 256.f / 360),
                        true
                );
                sendPacket(packet);

                if(Math.abs(current_pitch - pitch) < e && Math.abs(current_yaw - yaw) < e) {
                    this.cancel();
                }
            }
        };
        lookMovement.runTaskTimer(Bukkit.getPluginManager().getPlugin("FirePlugin"), 0, 1);
    }

    public void cancelLook() {
        if(this.lookMovement == null || this.lookMovement.isCancelled()) {
            return;
        }
        this.lookMovement.cancel();
    }

    public void despawn() {
        this.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.npc));
        this.sendPacket(new PacketPlayOutEntityDestroy(new int[] { this.npc.getId() }));

        this.cancelLook();
        this.cancelMove();
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

    public String getName() {
        return this.name;
    }

    public EntityPlayer getNpc() {
        return npc;
    }

    public void setMainHand(org.bukkit.inventory.ItemStack material) {
        ItemStack stack = CraftItemStack.asNMSCopy(material);
        //this.reload();
        npc.setEquipment(EnumItemSlot.MAINHAND, stack);
    }

    public void setOffHand(org.bukkit.inventory.ItemStack material) {
        ItemStack stack = CraftItemStack.asNMSCopy(material);
        npc.setEquipment(EnumItemSlot.OFFHAND, stack);
        this.reload();
    }

    public void reload() {
        this.sendPacket(new PacketPlayOutEntityDestroy(new int[] { this.npc.getId() }));
        this.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        this.sendPacket(new PacketPlayOutEntityHeadRotation(this.npc, (byte)(this.npc.yaw * 256 / 360)));
    }
}
