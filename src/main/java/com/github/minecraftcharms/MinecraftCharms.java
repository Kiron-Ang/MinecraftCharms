package com.github.minecraftcharms;

import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftCharms extends JavaPlugin {

    @Override
    public void onLoad() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been loaded!");
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("MinecraftCharms has been disabled!");
    }

}