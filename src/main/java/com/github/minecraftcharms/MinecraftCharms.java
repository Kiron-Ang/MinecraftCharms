// Specify package that this file belongs to
package com.github.minecraftcharms;

// Import lots of relevant classes
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;

import org.bukkit.scheduler.BukkitTask;

import java.util.Set;

import org.bukkit.entity.Player;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import org.bukkit.Material;

import java.util.List;
import java.util.ArrayList;

// Declare the main class of this file
public class MinecraftCharms extends JavaPlugin
implements CommandExecutor {

  // Called when the plugin is enabled after loading
  @Override
  public void onEnable() {
    // Save the config file from the jar with saveResource()
    // Will generate config file first time the plugin is loaded
    // but will not replace config file if it already exists
    saveResource("config.yml", false);

    // Use BukkitTask class to repeat a method again and again
    // the third argument is the delay in ticks while the fourth
    // argument is the time between method runs in ticks
    // 20 ticks is 1 second and the data type is "long" 
    BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, 
                      this::checkCharms, 0L, 100L);
    this.getCommand("charms").setExecutor(this);
  }


  @Override
  public boolean onCommand(CommandSender s, Command c, String l, 
                           String[] a) {
    
    List<String> loreList = new ArrayList<String>();
    if (s instanceof Player) {
      Player player = (Player) s;
      
      // Get set of charms in config.yml
      Set<String> charms = getConfig().getConfigurationSection("charms"
                           ).getKeys(false);

      // Loop through and give charms to player
      for (String charm : charms) {
        ItemStack charmStack = new ItemStack(Material.matchMaterial(
                               getConfig().getString("charms." + charm + 
                               ".material")));
        ItemMeta charmMeta = charmStack.getItemMeta();
        charmMeta.setDisplayName(getConfig().getString("charms." +
          charm + ".name"));
        loreList.clear();
        loreList.add(getConfig().getString("charms." + charm + ".lore"));
        charmMeta.setLore(loreList);
        charmStack.setItemMeta(charmMeta);
        player.getInventory().addItem(charmStack);
      }
    }
    return true;
  }

  private void checkCharms() {

    // Initialize variables so compiler does not throw a fit
    String material;
    String name;
    String lore;
    String charm_material;
    String charm_name;
    String charm_lore;

    // Get a set of the charms in the config
    // this can be iterated over later on to get traits of each charm
    Set<String> charms = getConfig().getConfigurationSection(
                         "charms").getKeys(false);

    // Loop over all players, then all hotbar slots, then all charms
    for (Player player : Bukkit.getOnlinePlayers()) {
      PlayerInventory playerInventory = player.getInventory();

      for (int slot = 0; slot < 9; slot++) {
        // Get all the information from an item in the hotbar
        // But use try-catch to ignore errors given by NULL values
        // like when a regular item does not have lore
        try {
          ItemStack itemStack = playerInventory.getItem(slot);
          ItemMeta itemMeta = itemStack.getItemMeta();
          material = itemStack.getType().toString().strip();
          name = itemMeta.getDisplayName().toString().strip();
          lore = itemMeta.getLore().toString().strip();
        } catch (Exception e) {
          continue;
        }

        // This will only execute if the above executed successfully
        // Now loop through the charms in the config and get their info
        // to then compare with items in the hotbar
        for (String charm : charms) {
          try {
            charm_material = getConfig().getString("charms." + charm + 
                             ".material").strip();
            charm_name = getConfig().getString("charms." + charm + 
                         ".name").strip();
            charm_lore = "[" + getConfig().getString("charms." + charm + 
                         ".lore").strip() + "]";
          } catch (Exception e) {
            continue;
          }

          // All three conditions must be met!
          if (material.equals(charm_material) &&
              name.equals(charm_name) &&
              lore.equals(charm_lore)) {

            // Create a string based on the current charm
            String section = "charms." + charm + ".effects";

            // Get a set of effects detailed in the config.yml file
            Set<String> effects = getConfig().getConfigurationSection(
                                  section).getKeys(false);

            // Loop through the effects and add them to the player
            for (String effect : effects) {
              String e_type = getConfig().getString(section + "." +
                                   effect + ".type");
              String amplifier = getConfig().getString(section + "." +
                                 effect + ".amplifier");
              PotionEffectType pet = PotionEffectType.getByName(e_type);
              int amp = Integer.parseInt(amplifier);

            // Duration for potion effects will always be 20 ticks more
            // than the BukkitTask interval
            player.addPotionEffect(new PotionEffect(pet, 120, amp));
            }
          }
        }
      }
    }
  }
}