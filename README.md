# minecraft-charms
This repository contains Java code for a Paper 1.20.6 (Build #134) Minecraft server plugin to add custom charms.

## What the project does

Charms are items that, when placed in the nine slots in a player's hotbar, confer potion effects continuously to that player. This plugin implements charms for Minecraft servers and allows for the addition of custom charms.

All charms must have a custom name and lore. Define those in the ``config.yml`` file and run the command ``/charms`` in-game to add all available charms to your inventory. Below are some links that are useful for people that want to add new charms or edit this plugin themselves with Java programming:

- Potion Effects List: hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html
- Materials List: hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
- Paper API Documentation: papermc.io/javadocs

## Why the project is useful

Currently, no plugins implement charms or charm-like items in an easy way. Server staff can achieve the same effect with other plugins, but only after cumbersome changes.

## How users can get started with the project

If you'd like to contribute, please create a pull request on the GitHub page for this project! You can also email Kiron at kiron_ang1@baylor.edu.

## Where users can get help with the project

If you need help, please create an issue on the GitHub page for this project! You can also email Kiron at kiron_ang1@baylor.edu.

## Who maintains and contributes to the project

- Kiron Ang: https://github.com/Kiron-Ang, kiron_ang1@baylor.edu

GOALS:

- Add more options for the ``/charms`` command: ``/charms (player name) (charm name)``
- Add custom model support! Involves ``config.yml`` file
- Add option to make the items glow! Involves ``config.yml`` file
