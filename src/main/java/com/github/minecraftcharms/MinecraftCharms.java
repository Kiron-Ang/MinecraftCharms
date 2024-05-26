package com.github.minecraftcharms;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftCharms extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been loaded!");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveResource("config.yml", /* replace */ false);
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been disabled!");
    }

    public void checkCharms(HumanEntity humanEntity) {
        PlayerInventory playerInventory = humanEntity.getInventory();
        for (int slot = 0; slot < 9; slot++) {
            ItemStack itemStack = playerInventory.getItem(slot);
            if (itemStack == null) {
                continue;
            }
            String string = itemStack.getType().toString().strip();
            String charm = getConfig().getString("item").strip();
            humanEntity.sendMessage(string);
            humanEntity.sendMessage(charm);
            if (string.equals(charm)) {
                humanEntity.sendMessage("You have a charm!");
            } else {
                humanEntity.sendMessage("You don't have a charm!"); 
            }
        }
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        checkCharms(humanEntity);
    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        checkCharms(humanEntity);
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        checkCharms(humanEntity);
    }
}