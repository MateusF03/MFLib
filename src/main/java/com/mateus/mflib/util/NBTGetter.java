package com.mateus.mflib.util;

import com.mateus.mflib.nms.NMSReflect;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class NBTGetter {
    public static String getTagValue(String tag, ItemStack itemStack) {
        NMSReflect nmsReflect = new NMSReflect();
        try {
            Object nmsCopy = nmsReflect.getCraftBukkitClass("inventory", "CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null,itemStack);
            if (nmsCopy.getClass().getMethod("getTag").invoke(nmsCopy) != null) {
                Object tagCompound = nmsCopy.getClass().getMethod("getTag").invoke(nmsCopy);
                return (String) tagCompound.getClass().getMethod("getString", String.class).invoke(tagCompound, tag);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
