// Specifies the package that this Java class belongs to
package com.github.minecraftcharms;

// Import base class for Minecraft Java Plugins
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftCharms extends JavaPlugin {

    // Define behavior when plugin is loaded
    @Override
    public void onLoad() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been loaded!");
    }

    // Define behavior when plugin is enabled
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CharmsListener(), this);
        saveResource("config.yml", /* replace */ false);
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been enabled!");
    }


    // Define behavior when plugin is disabled
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been disabled!");
    }
}