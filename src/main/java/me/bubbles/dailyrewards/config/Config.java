package me.bubbles.dailyrewards.config;

import me.bubbles.dailyrewards.DailyRewards;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    private String name;
    private FileConfiguration fileConfiguration;
    private DailyRewards plugin;

    public Config(DailyRewards plugin, String name) {
        this(plugin,new File(plugin.getDataFolder(),name));
    }

    public Config(DailyRewards plugin, File file) {

        this.plugin=plugin;

        plugin.saveResource(file.getName(),false);

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            // poop
        }

        this.file=file;
        this.name=file.getName();

    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
    public String getName() {
        return name;
    }
    public File getFile() {
        return file;
    }

    public void save() {
        try {
            getFileConfiguration().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
