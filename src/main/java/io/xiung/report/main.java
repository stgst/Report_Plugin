package io.xiung.report;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
    public static FileConfiguration config;
    @Override
    public void onEnable() {
        // Plugin startup logic
        config = this.getConfig();
        config.addDefault("webhookURL", "URL");
        config.addDefault("cooldowns", 600);
        config.options().copyDefaults(true);
        saveConfig();
        getCommand("report").setExecutor(new command(this));
        getServer().getPluginManager().registerEvents(new event(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
