package com.github.minecraftcharms;

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
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.Material;
import java.util.List;
import java.util.ArrayList;

public class MinecraftCharms extends JavaPlugin {
  @Override
  public void onEnable() {
    // Save the default config.yml file that comes with this plugin
    saveResource("config.yml", false);
    // Initialize a task that the server will run every 3 seconds
    // I need the "L" to specify LONG format
    // 20 ticks is 1 second
    BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, 
                      this::checkCharms, 0L, 60L);
    // Add an in-game command called "charms" that will use this
    // class to figure out what to do
    this.getCommand("charms").setExecutor(this);
  }

  public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
    if (a.length == 0) {
      s.sendMessage("Please try again: /charms <PLAYER> <ITEM> OR /charms reload");
      return true;
    }
    if (a[0].equals("reload")) {
      reloadConfig();
      s.sendMessage("Reloaded MinecraftCharms config.yml");
      return true;
    }
    if (a.length != 2) {
      s.sendMessage("Please try again: /charms <PLAYER> <ITEM> OR /charms reload");
      return true;
    }
    String playerName = a[0];
    String charmName = a[1];
    Player player = Bukkit.getServer().getPlayer(playerName);
    Set<String> charms = getConfig().getConfigurationSection("charms").getKeys(false);
    for (String charm : charms) {
      if (!charm.equals(charmName)) {
        continue;
      }
      ItemStack charmStack = new ItemStack(Material.matchMaterial(getConfig().getString("charms." + charm + ".material")));
      ItemMeta charmMeta = charmStack.getItemMeta();
      charmMeta.setDisplayName(getConfig().getString("charms." + charm + ".name"));
      List<String> loreList = new ArrayList<String>();
      loreList.add(getConfig().getString("charms." + charm + ".lore"));
      charmMeta.setLore(loreList);
      if (getConfig().getString("charms." + charm + ".glow").equals("true")) {
        charmMeta.setEnchantmentGlintOverride(true);
      }
      charmStack.setItemMeta(charmMeta);
      player.getInventory().addItem(charmStack);
      s.sendMessage("Added the item to the player's inventory!");
    }
    return true;
  }

  public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
    if (a.length == 2) {
      Set<String> charms = getConfig().getConfigurationSection("charms").getKeys(false);
      return new ArrayList<>(charms);
    }
    return new ArrayList<>(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
  }

  // Method to check if a player has a charm in their inventory
  // This is going to be the method that the task runs constantly
  // So it really needs to be efficient
  private void checkCharms() {
    // I need to initialize all of these Strings. Maven gives me an error
    // if I leave these declarations out.
    String material;
    String name;
    String lore;
    String charm_material;
    String charm_name;
    String charm_lore;
    // Get a set that represents all of the charms listed in the config
    Set<String> charms = getConfig().getConfigurationSection(
                         "charms").getKeys(false);
    // Repeat this code for every single player on the server
    for (Player player : Bukkit.getOnlinePlayers()) {
      // Get the player's inventory
      PlayerInventory playerInventory = player.getInventory();
      // Repeat this code for each of their hotbar slots (0 to 8)
      for (int slot = 0; slot < 9; slot++) {
        // Try this code because it might give an error if the hotbar
        // slot is empty or does not have lore, etc...
        // I know it's recommended to use hasLore, etc, but I feel
        // that this essentially achieves the same thing.
        try {
          // Get the ItemStack that represents in the item in the slot
          ItemStack itemStack = playerInventory.getItem(slot);
          // Get the ItemMeta associated with that ItemStack
          ItemMeta itemMeta = itemStack.getItemMeta();
          // Get the "type" AKA "material" , like "STONE" or "APPLE"
          // I use .strip() to get rid of leading and trailing whitespace
          material = itemStack.getType().toString().strip();
          // Get the "Display Name", which only is set if someone
          // added a custom name to the item, like through an anvil
          name = itemMeta.getDisplayName().toString().strip();
          // Get any lore that the item has
          lore = itemMeta.getLore().toString().strip();
        // Run this code if an error occurs in the Try block code above
        // This will happen frequently since most items will not have lore
        // and sometimes hotbar slots will be empty
        } catch (Exception e) {
          // Move onto the next slot number
          continue;
        }
        // Repeat this code for every single charm in the config.yml file
        for (String charm : charms) {
          // I put this try block here to account for situations
          // where something is missing
          // TODO: Consider checking the config.yml file beforehand to
          // make sure that it is valid.
          try {
            // Get the String in the field "material"  in the config.yml
            charm_material = getConfig().getString("charms." + charm + 
                             ".material").strip();
            // Get the String in the field "name"  in the config.yml
            charm_name = getConfig().getString("charms." + charm + 
                         ".name").strip();
            // Get the String in the field "lore"  in the config.yml
            // I have to add "[]" because that's how Minecraft
            // Gets the lore string from items in-game
            charm_lore = "[" + getConfig().getString("charms." + charm + 
                         ".lore").strip() + "]";
          // Run this code if the above code fails
          } catch (Exception e) {
            // Move onto the next charm listed in the config
            continue;
          }
          // Run this code if the material, name, and lore all match
          // This is how I define what a charm is!
          if (material.equals(charm_material) &&
              name.equals(charm_name) &&
              lore.equals(charm_lore)) {
            // Create a string that represents the charm's effects section
            // I have to do this because every single charm has a
            // separate section, like "charms.scarything.effects"
            String section = "charms." + charm + ".effects";
            // Get all the different sections in the "effects" section
            // Each of the effects in this Set represents a potion effect
            // The actual names of the things in the Set do not matter
            // so feel free to name them whatever you want in config.yml
            Set<String> effects = getConfig().getConfigurationSection(
                                  section).getKeys(false);
            // Run this code for every single potion effect
            for (String effect : effects) {
              // Get the "type", like "SPEED" or "ABSORPTION"
              String e_type = getConfig().getString(section + "." +
                                   effect + ".type");
              // Get the "amplifier", which is a number like "5" or "50"
              String amplifier = getConfig().getString(section + "." +
                                 effect + ".amplifier");
              // Create an instance of PotionEffectType based on the
              // effect type only
              PotionEffectType pet = PotionEffectType.getByName(e_type);
              // Create an Integer based on the amplifier string
              int amp = Integer.parseInt(amplifier);
              // Add the potion effect. It will last for 4 seconds,
              // and since the task runs every 3 seconds, the player
              // will always have the effect as long as they continue
              // to have the charm in their hotbar
              player.addPotionEffect(new PotionEffect(pet, 80, amp));
            }
          }
        }
      }
    }
  }
}