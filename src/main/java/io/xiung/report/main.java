package io.xiung.report;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
    public static FileConfiguration config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("report").setExecutor(new command());
        getServer().getPluginManager().registerEvents(new event(), this);
        config = this.getConfig();
        config.addDefault("webhookURL", "URL");
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
