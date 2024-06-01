package com.github.minecraftcharms;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.configuration.ConfigurationSection;
import java.util.Set;

public class MinecraftCharms extends JavaPlugin {

  private BukkitTask task;

  // Called when the plugin is loaded
  @Override
  public void onLoad() {
    getServer().getConsoleSender().sendMessage("Loaded MinecraftCharms");
  }

  // Called when the plugin is enabled after loading
  @Override
  public void onEnable() {
    saveResource("config.yml", false);

    task = Bukkit.getScheduler().runTaskTimer(
                                       this, this::checkCharms, 0L, 200L);

    getServer().getConsoleSender().sendMessage("Enabled MinecraftCharms");
  }

  // Called when the server stops and the plugin is disabled
  @Override
  public void onDisable() {
    task.cancel();
    getServer().getConsoleSender().sendMessage("DisabledMinecraftCharms");
  }

  // Check if any of the players on the server have a charm in their
  // hotbar. This currently requires three for loops. Although I see
  // that optimizing this is important, I am struggling to find a 
  // way to remove the correct potion effect with removing effects
  // that players might get from other means like regular potions.
  // So, I currently rely on running this function constantly on a timer
  private void checkCharms() {
    // Get the names of the charms only once
    Set<String> keys = getConfig().getConfigurationSection(
                                  "charms").getKeys(false);
    for (Player player : Bukkit.getOnlinePlayers()) {
      for (int slot = 0; slot < 9; slot++) {
        player.sendMessage("Looking at slot " + slot);
        for (String charm : keys) {
          player.sendMessage("Seeing if you have charm " + charm);

        }
      }
    }
  }


}