package com.mateus.mflib.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class NMSReflect {
    private final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
    public Class getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    public Class getCraftBukkitClass(String prefix,String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + prefix  + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"))
                    .invoke(playerConnection, packet);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
