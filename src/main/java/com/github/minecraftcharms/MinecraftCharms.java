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

  private void checkCharms() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage("Giving you a potion effect...");
      player.addPotionEffect(new PotionEffect(
                             PotionEffectType.STRENGTH, 100, 4));
    }
  }


}