package com.mateus.mflib.util;

import com.mateus.mflib.nms.NMSReflect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
    public ItemBuilder setDisplayName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }
    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreRestrictions) {
        itemMeta.addEnchant(enchantment, level, ignoreRestrictions);
        return this;
    }
    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }
    public ItemBuilder setNBTTag(String key, String value) {
        itemStack.setItemMeta(itemMeta);
        NMSReflect nmsReflect = new NMSReflect();
        try {
            Object nmsCopy = nmsReflect.getCraftBukkitClass("inventory", "CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null,itemStack);
            Object nbtTagCompound = nmsReflect.getNMSClass("NBTTagCompound").getConstructor().newInstance();
            boolean b = nmsCopy.getClass().getMethod("getTag").invoke(nmsCopy) != null;
            Object nbtTag = b ? nmsCopy.getClass().getMethod("getTag").invoke(nmsCopy) : nbtTagCompound;
            Constructor nbsString = nmsReflect.getNMSClass("NBTTagString").getConstructor(String.class);
            nbtTag.getClass().getMethod("set", String.class, nmsReflect.getNMSClass("NBTBase"))
                    .invoke(nbtTag ,key, nbsString.newInstance(value));
            nmsCopy.getClass().getMethod("setTag", nmsReflect.getNMSClass("NBTTagCompound")).invoke(nmsCopy, nbtTag);
            //this.nmsCopy = nmsCopy;
            this.itemStack = (ItemStack) nmsReflect.getCraftBukkitClass("inventory", "CraftItemStack").getMethod("asBukkitCopy", nmsReflect.getNMSClass("ItemStack"))
                    .invoke(null,nmsCopy);
            this.itemMeta = itemStack.getItemMeta();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        /*if (nmsCopy != null) {
            NMSReflect nmsReflect = new NMSReflect();
            try {
                itemStack = (ItemStack) nmsReflect.getCraftBukkitClass("inventory", "CraftItemStack").getMethod("asBukkitCopy", nmsReflect.getNMSClass("ItemStack"))
                        .invoke(null,nmsCopy);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }*/
        return itemStack;
    }
}
