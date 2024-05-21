// Specifies the package that this Java class belongs to
package com.github.minecraftcharms;

// Import relevant classes
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;


public class CharmsListener implements Listener {

    // Define behavior when a player closes their inventory
    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        player.sendMessage("You closed your inventory!");
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 9; i++) {
            String current_slot = Integer.toString(i);
            ItemStack item = inventory.getItem(i);
            if (item ==  null) {
                player.sendMessage("There is nothing in Item Slot " + current_slot);
            } else {
                String item_type = item.getType().toString();
                player.sendMessage("The item in Item Slot " + current_slot + " is " + item_type);
            }
        }
    }


    // Define behavior when a player picks up an item
    @EventHandler
    public void pickupItem(EntityPickupItemEvent event) {
        HumanEntity player = (HumanEntity) event.getEntity();
        player.sendMessage("You picked up an item!");
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 9; i++) {
            String current_slot = Integer.toString(i);
            ItemStack item = inventory.getItem(i);
            if (item ==  null) {
                player.sendMessage("There is nothing in Item Slot " + current_slot);
            } else {
                String item_type = item.getType().toString();
                player.sendMessage("The item in Item Slot " + current_slot + " is " + item_type);
            }
        }
    }

    // Define behavior when a player drops an item
    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        HumanEntity player = event.getPlayer();
        player.sendMessage("You dropped an item!");
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 9; i++) {
            String current_slot = Integer.toString(i);
            ItemStack item = inventory.getItem(i);
            if (item ==  null) {
                player.sendMessage("There is nothing in Item Slot " + current_slot);
            } else {
                String item_type = item.getType().toString();
                player.sendMessage("The item in Item Slot " + current_slot + " is " + item_type);
            }
        }
    }
}