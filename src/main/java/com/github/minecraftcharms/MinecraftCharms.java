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
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.Material;
import java.util.List;
import java.util.ArrayList;
public class MinecraftCharms extends JavaPlugin
implements CommandExecutor {
  @Override
  public void onEnable() {
    saveResource("config.yml", false);
    BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, 
                      this::checkCharms, 0L, 60L);
    this.getCommand("charms").setExecutor(this);
  }
  @Override
  public boolean onCommand(CommandSender s, Command c, String l,
                           String[] a) {
    List<String> loreList = new ArrayList<String>();
    if (s instanceof Player) {
      Player player = (Player) s;
      Set<String> charms = getConfig().getConfigurationSection("charms"
                           ).getKeys(false);
      for (String charm : charms) {
        ItemStack charmStack = new ItemStack(Material.matchMaterial(
                               getConfig().getString("charms." + charm + 
                               ".material")));
        ItemMeta charmMeta = charmStack.getItemMeta();
        charmMeta.setDisplayName(getConfig().getString("charms." + charm +
          ".name"));
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
    String material;
    String name;
    String lore;
    String charm_material;
    String charm_name;
    String charm_lore;
    Set<String> charms = getConfig().getConfigurationSection(
                         "charms").getKeys(false);
    for (Player player : Bukkit.getOnlinePlayers()) {
      PlayerInventory playerInventory = player.getInventory();
      for (int slot = 0; slot < 9; slot++) {
        try {
          ItemStack itemStack = playerInventory.getItem(slot);
          ItemMeta itemMeta = itemStack.getItemMeta();
          material = itemStack.getType().toString().strip();
          name = itemMeta.getDisplayName().toString().strip();
          lore = itemMeta.getLore().toString().strip();
        } catch (Exception e) {
          continue;
        }
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
          if (material.equals(charm_material) &&
              name.equals(charm_name) &&
              lore.equals(charm_lore)) {
            String section = "charms." + charm + ".effects";
            Set<String> effects = getConfig().getConfigurationSection(
                                  section).getKeys(false);
            for (String effect : effects) {
              String e_type = getConfig().getString(section + "." +
                                   effect + ".type");
              String amplifier = getConfig().getString(section + "." +
                                 effect + ".amplifier");
              PotionEffectType pet = PotionEffectType.getByName(e_type);
              int amp = Integer.parseInt(amplifier);
            player.addPotionEffect(new PotionEffect(pet, 80, amp));
            }
          }
        }
      }
    }
  }
}