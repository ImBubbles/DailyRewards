package me.bubbles.dailyrewards;

import me.bubbles.dailyrewards.commands.manager.CommandManager;
import me.bubbles.dailyrewards.util.MariaDB;
import me.bubbles.dailyrewards.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.time.Instant;

public final class DailyRewards extends JavaPlugin {

    private CommandManager commandManager;
    private ConfigManager configManager;
    private MariaDB mariaDB;
    private boolean useMariaDB;

    @Override
    public void onEnable() {
        // CONFIG
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager=new ConfigManager(this);
        configManager.addConfig(
                "config.yml",
                "data.yml",
                "rewards.yml"
        );
        // LOAD COMMANDS
        commandManager=new CommandManager(this);

        /// MARIADB
        this.useMariaDB=false;
        boolean valid = false;
        MariaDB mariaDB = null;
        if(getConfigManager().getConfig("config.yml").getFileConfiguration().getBoolean("MariaDB")) {
            FileConfiguration config = getConfigManager().getConfig("config.yml").getFileConfiguration();
            mariaDB = new MariaDB(
                    config.getString("endpoint"),
                    config.getString("database"),
                    config.getString("username"),
                    config.getString("password")
            ).connect();
            valid = mariaDB!=null;
        }
        // START MariaDB
        this.mariaDB = mariaDB;
        if(valid) {
            // SETUP TABLE MYSQL
            mariaDB.execute(
                    "create table IF NOT EXISTS dailyrewards(\n" +
                            "    uuid VARCHAR(255) not null,\n" +
                            "    time BIGINT UNSIGNED not null,\n" +
                            "    primary key(uuid)\n" +
                            ");"
            );
            useMariaDB=true;
        }else{
            getLogger().warning("MariaDB is either not valid or set to false, using data.yml");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public long getEpochTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public MariaDB getMariaDB() {
        return mariaDB;
    }
    public boolean isMariaDB() {
        return useMariaDB;
    }

    public void reload() {
        configManager.reloadAll();
        reloadConfig();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

}
