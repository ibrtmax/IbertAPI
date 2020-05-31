package me.ibrt.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class spigotmain extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Aktiviert");
    }

    @Override
    public void onDisable() {
        System.out.println("Deaktiviert");
    }
}
