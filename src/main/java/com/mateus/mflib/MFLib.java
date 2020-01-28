package com.mateus.mflib;

import com.mateus.mflib.util.ItemBuilder;
import com.mateus.mflib.util.NBTGetter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MFLib extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("teste").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ItemStack itemStack = new ItemBuilder(Material.GOLDEN_APPLE)
                    .setDisplayName(ChatColor.AQUA + "Teste")
                    .setNBTTag("teste", "EPICO MEMO VIU")
                    .build();
            p.getInventory().addItem(itemStack);
            p.sendMessage(NBTGetter.getTagValue("teste", itemStack));
        }
        return super.onCommand(sender, command, label, args);
    }
}
