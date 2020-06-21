package com.f1remoon.fireplugin.npc;

import net.minecraft.server.v1_15_R1.EnumItemSlot;
import net.minecraft.server.v1_15_R1.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {

    private static List<NPC> NPCs = new ArrayList<>();

    public static void createNPC(Location l, String nickname) {
        createNPC(l, nickname, null, null);
    }

    public static NPC createNPC(Location l, String nickname, String texture, String signature) {
        NPC npc = new NPC(l, nickname, texture, signature);
        npc.spawn();

        NPCs.add(npc);

        return npc;
    }

    public static NPC getNPCByName(String name) {
        for(NPC npc : NPCs) {
            if(npc.getName().equals(name)) {
                return npc;
            }
        }
        return null;
    }

    public static void createChristophSchneider(Location l) {
        /* https://www.minecraftskins.com/skin/13627845/christop/ */
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjczNzU3MjIwMywKICAicHJvZmlsZUlkIiA6ICJiZWNkZGIyOGEyYzg0OWI0YTliMDkyMmE1ODA1MTQyMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdFR2IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2QxMjE3NTdiZTcwOWFhZmI0ZTBkNTBiNzhhYjY4YzhlMDUxZTljOWI0Njk3MjYzYTZkYWJiMzIwMzk4MmFiMzYiCiAgICB9CiAgfQp9";
        String signature = "kYH5MLarHdo8ORPdH09hlxTRUW4zfS8p4O9zLF1TveSiWEyQw4U8vb02iOIB9ZMMrcyO3ZJb8QWkB8LlKlcTy/Wtbc5rAfHYMFSGp10tBQKsq4rK2ey1NmMR97+0zdF6St9RG/nRiaGHdoLTcG7F7Brm81QXma9pfNvgWZT/VwRkMcnU++M7e8tKeo57qAXKqUXT5BSxtuVbsXyEgrT3lxxfb2suuV+TDjXZFNxqDl7a6gU5sc2hLfM3B+/nbHMCpNC4/RzX5bxAZiuZOzeCMPjVfTbaQllKduH3+psFMG502At+S/WDH5gHSLm0pWUk7t4x3asZ6+vcvQLF+V65vvFyuSRLr4QYcfO8f2zih7TWEgJ+ygVU6f9OOxNg9R7m3Q2F4Yy6wvfwu0GYHKp7qflhycSHRixxui8Br1+EZSoB5LPURTACzuoLJ/3NzhwxFgi/Cwp4r0eH4j7eQV+o6+lK0GqPS+JBX+pkGME+VimFX/ZnzOx15jQOk/QEwSNQu7tMCRnslTrrrG9i+cIbwvSR4zBJlOxDg/9UI66jYaooq6iM6qR+hWOfMAlOtCiJP4+mEHZ5YH/dIaHsYLlsuJszm/E71h2PfYG/o2rXrvblCGF0bcuyd5xIiTI7cxClZn0R36UnWQhfA6Lh7jz1Xko6kTd3vO95R5NXH7aUAmk=";
        NPC npc = createNPC(l, "Christoph", texture, signature);

        org.bukkit.inventory.ItemStack stick = new org.bukkit.inventory.ItemStack(Material.STICK);
        ItemStack stack = CraftItemStack.asNMSCopy(stick);
        npc.setEquipment(EnumItemSlot.MAINHAND, stack);
        npc.setEquipment(EnumItemSlot.OFFHAND, stack);
    }

    public static void createPaulLander(Location l) {
        /* https://www.minecraftskins.com/skin/444021/paul-landers/ */
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjY4NTA0OTMxNiwKICAicHJvZmlsZUlkIiA6ICI3MjI2Mjg2NzYyZWY0YjZlODRlMzc2Y2JkYWNhZjU1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJicmFuZG9uZDI2IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzUyZWZmMzI2N2ZmYjZhZDMzN2VlM2M4YzEyMmU4M2YyM2ZjZWJhNjVhZmNjZmU2ZWNlOTFmMDI2MzcyYWRhNTIiCiAgICB9CiAgfQp9";
        String signature = "Qf2XODckaUaPyKkLxcduojm1jkUbU898W19up6YbmCxDS0Ozw1Rc0Bu875e8jH+2Vz8b0o1PwCQUNZXzGJaMvPh1js5u6qZv6HUMpX/7nAVpoD8FYKs08LPJB5X8YhAFbNct78InnbPyQAuipIteF3dTXVlhR3WLaNn1E2dizOkaa9cs9UvrfKHEv0FpEQKLmIwukq9kCudiZX36rc7d1WxVfLmnixrhtxZKWTP7JTGrSle7j8zaTKsNlIV5nhoyZctt4WHotBo61OEpOdJ0xt/YjysDxMIMhNQmYKHhZwhXG+tEjwwlU76jD+uAnI+BdP737XW8C3HIHf2FPkyepe6ENun24DIZMYEnXePiBPRxfmkSTnchqu176rhPExGA6iPbYmAfy0x9eUoPo0DFXMWvNITHSxEXv9/9Ae6N7Kj10F67+omW9DJ/ZBggAVh8YN0cGnAeQ+Us/oGDEkvNth0E4jW1Q2BFGLbf4wqf0WIcAB6B6d8DTx27m2d7PlVeyOgxjJtmiNGkuQNaueRuxWM9vYRuTsp3ydLArR5JKQjl6Dxge4GSmEF1rwQPiDZfuboBKQN1C1dLlyt63M+JRwBRXTxq0ERr2c3x0kPLzlp4LkNn4fgm0Gwz3iuerMyRJmdM2QVzns+9/dvQrWVyF3atVDP0XDGKjzoJ2dwsLeY=";
        createNPC(l, "Paul", texture, signature);
    }

    public static void createTillLindemann(Location l) {
        /* https://www.minecraftskins.com/skin/2405085/till-lindemann/ */
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjY4NTEyNTU5NiwKICAicHJvZmlsZUlkIiA6ICI0Y2E2ODVhNjRmODc0ODE5YTcwNjlhNThhY2M5ZWQ5MyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaWtUb2trIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQxNTY2NDdmYjkyNWQ2MGJkY2VjNTY3ZjljYmZiM2ZiMDllOWJkYzU3MDI4ZTFhY2IwZDE5ZjNjYjI5MTA1YTciCiAgICB9CiAgfQp9";
        String signature = "AS8HNCiiXn1P78qGMar7cdfpvIDz4sQrTpZH3ER24HtrBpPLfG1mlW2KjaNrYjDfOTgz2Z4v9jJD5MsilaKLoyPQrBfUjrqSIlcDejhRqFROI1UVJ5hCZ/FpKT6F8tay12cUqDD2ZIcvWi3bsep2Pk18BfiNWPBboeb00cGVcwKHj9VG4vBia+CWiTXN0bwfNshrWmRW7aei9wI3eS+BoEVgHvYDUohRcDonKXKv4Z3218RC/LSj2uVL+rPBrX9UtA284uVXUHg8O39oPZkqQCP9uV4TQHHEnZDdbPdo8F/PL7r3670tMUiFoje+YZpBRzW3MATECht1LBQDvypS543hj4WyRI8lG1KkaUNFdJCwwitNT4P78fqwMjx2g7ZW6izQ+mlm+Xyj0D7uK895q8uGW9Q8OdEst/VNeJvZYfvg3dLovJZ9ClnMalop03vJzIxZ15z2zVbJ5kmETipne8bc7JCbRxE0Tc8tSz4p7tpv73qu9uA+p/0HvbO/Q8PFFx0xvmHdNH2lTwlY0rKjp3DIxEKQmAlpr9zg6ndbNp5Opn4FXwB87Uekngun0b8ZbpAUB8C/Yb4sDb7VyXDU68Rv2KajmK/cXtA3bRR/BIXMhP7URs1S7huK/go2nzNoUVxgAFYdQtm+et1V3ln+nBLmc45jpIAlCS/GLFJ52l8=";
        NPC npc = createNPC(l, "Till", texture, signature);

        org.bukkit.inventory.ItemStack crossbow = new org.bukkit.inventory.ItemStack(Material.CROSSBOW);
        CrossbowMeta meta = ((CrossbowMeta)crossbow.getItemMeta());
        meta.addChargedProjectile(new org.bukkit.inventory.ItemStack(Material.ARROW, 1));
        crossbow.setItemMeta(meta);
        ItemStack stack = CraftItemStack.asNMSCopy(crossbow);
        npc.setEquipment(EnumItemSlot.MAINHAND, stack);
    }

    public static void createRichardKruspe(Location l) {
        /* https://www.minecraftskins.com/skin/444015/richard-kruspe/ */
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjY4NTI0MTUwOSwKICAicHJvZmlsZUlkIiA6ICJmZDYwZjM2ZjU4NjE0ZjEyYjNjZDQ3YzJkODU1Mjk5YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSZWFkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzcwZThkNjUyODUwODdjNzc1MmVlNTMxZWU4OGM2NjU3NzQwMWFlNmZkNGY2N2VlZWUyZjM5ZWRmZGZhYjM0NzkiCiAgICB9CiAgfQp9";
        String signature = "xexVE3jzMEiPgdJnk87Xv6YCh8Dhp76kY1GtnIRtVHEBi5SFqOgx+JrnFcM2VX+hlqkFMrFlXKvWqsMnlmbAElaiVC5JzJPQ4XKlURyob7guNtLnzbNGUb9GUkw+96I2Auv57fxg4qTcnmW2T60qqGkfJaf1Zh6QnCK4O+bGMHo8v+OdE0ZkZLBVfmmNRmfLU737eoi+ngqOb8Ec2vdo3XkQYOlhyPQcAreSXSr5U3FxjXOzODeeaj7eBK1/RVJytv0qRY1t8fjGY+SIduFx6PBW5zmmwLWsglv92eQCAFefT7LDVd9vufNKw729hQjLWvUSU+D1LAoSNlOu3cnzfvVicyGa8slJ+g+xa0hKirlo/csPcDuHe8sbHHd3pbJdP/Vkvp4bB4Hy8UEb6wJKnwrAGxcJIpsnlqOEGWecbgf0Fo3eHsDH4A9Ew3rqjdR4hqZ0kFuzmYFC52spvsZp1Ne9ALaryVRIb8zz4o4cWu9Dy+BvdwTwF4Vt9CNa5ulK/NX07ZgbuPUXdFtw/mvSPnYxBKvAVwUKJLA0hD3X1IPXpTrcosE3GzaQFRC/BTVx2azNxKXlX/l8rbfQWvh0140TZpozCEnzNk9m6dBA2xB8XF4X3WJKhXNV/44Z+7AumkTOSBq9xlqPX2LQ1xXPkiWYC4Aw5Ycxt1fvtK5DMWo=";
        createNPC(l, "Richard", texture, signature);
    }

    public static void createChristianLorenz(Location l) {
        /* https://www.minecraftskins.com/skin/4304430/flacke-lorenz/ */
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjY4NTMzMzg1MywKICAicHJvZmlsZUlkIiA6ICI1OTgzZjkxY2UzY2M0MzdjYjc0ZTZlMTJmNWY0YzNlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJOaW5nYV9LaXR0eSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YzUzNmI5NTEzMDBiNWFjNTZlMjBkOTdkMGIwY2U2OGM2YWVjNzFlZjEwMTVjMWU4NmU0MTYyNzBiMTI3ODk5IgogICAgfQogIH0KfQ==";
        String signature = "N6zbgcsby2NfL3omCiTzTC+Fu0dlSSBqnWb7qX53AwPVriorknckL4Otz984egVQNXpkbNwu0WdzmFuJ8Zr+t+N+c2B2gfmPyxcFhmsK/uovGjdhs9QFN1mGuNJVt8QlDsYr5+183HRK5qiEJcDVpeQO0/vrfc3sSADAqrEN64Ho9blFdr5Uz8eByb5eIwHe7N8iHmc2VC3KaulwrfDpA0ZZcPLk74osSZWe2OcTiIZKKeqxcJ5gRyAO56pAHlu1JK7eXFyrnMdM1R3er4jDIaQHs6HNi/NfMpM2x8+RMiVtiN3B0iYydUhfZQ7VzOZBYiHBxD2RpPIpydUs0+643JANdM3/xpBG546x9VoquvdzxYyNNtA+lR8cv7TsRPN0U7wqIe34OYWCV0K3K4aiLh0Qsiq25C/nGBLkdBeyDDvs5BNAIIIKg3CoEf8f+ap96R91NyKKv3hxs+tKUHJZwIegh3q9Gph6Xd+jeT21cbrJRFAr3okn5g48CvnYzbVa3d2lIyAgwSYn7ylzpL8iquX0xruxaLKpmD0dBEt/JEKoaJjRX1AdSIrEqBnyEPil1DIOb8XNIbqATXMvNtGJdbfppngHQlIjikUY+O25Xxqp0G3V3WdHf7ufvSte1MuDDYSlQrRVimBgQ0GGJhbkybOhbdecnyWLF98LcF0QJ4s=";
        createNPC(l, "Christian", texture, signature);
    }

    public static void createOliverRiedel(Location l) {
        /* https://www.minecraftskins.com/skin/444026/oliver-riedel/ */
        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5MjY4NTQ3Nzk2NCwKICAicHJvZmlsZUlkIiA6ICJkODI0YWFjMjVmNzE0MjgyOTI0MTQyMmVjOWJhZDYzMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb25hMjQyNCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hNDNlMDEwZmE4OTQ2MGYwY2JmOWQzYWU4YmM4MTgxNjA5YzdkZjA1NDk0MTJjZjRjNjEzYjVlM2ZkZTM1ODkzIgogICAgfQogIH0KfQ==";
        String signature = "a/Fsh0FGMtxI+Z0DrQi7jEh7flAv/aQ0NGn36oo9cp9Pdt8OQLQNUCSWTC5d3+hXXsIgUxcKDCbgWD5TzCL5myBwRTjV2/POM1E21UElIN8A8V3cLI0SwJhwxJPIjPVmSNrrsiADSCWEy9pODVVV304r56uR7MYSkYAW9vbyFVw9FRr0FpEuodDPlclU/ZGCt73oej0Orb45aPAPuK460dnt7LDn5S3iPsbntvSG7WH6/+8bB7mvKoRSQcUS6t+nDijAGNdJJ1tnWcSekqrVYMusixWnbZGOqw+g6gHBtU7n9MEGoybEppVkpZ1wE7FX+nMFV16VNzKYMzagUKyBNOaU/nMYDM6Y7ibLr3XLGGu1eXCC4Xn+igjN+rsQxlpBE/K/alaFh+2YnT5hr3o6ekbglHzNh2Ln/s0vYKZegtJ6o0mz+31VXOQYwFGFy725Nzfct+/XTDwIHAt2yLMJ46syJ+EDW2xasy+3fuQ7MD4L455MyIGkPADLolrH114y5j2JByHrK3QbL4u/gJwexarWApinhsVedl0kEO0AOVSKSJ/v9GHTZOBsCHDU0dH61yA0OMFOexO8hmR0XXnrl8C4LQCw9kaCJTdXPNlVkhOsjbNMKhpvyxGSMAXodc/HyYg+KH9MC/VE0QFq59svrHKDG9FGUn6LG5a+DuDThE0=";
        createNPC(l, "Oliver", texture, signature);
    }

    public static List<NPC> getNPCs() {
        return NPCs;
    }

    public static void despawnByName(String name) {
        NPC npc = getNPCByName(name);
        if(npc != null) {
            NPCs.remove(npc);
            npc.despawn();
        }
    }
}
