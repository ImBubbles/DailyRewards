package me.bubbles.dailyrewards.util;

import me.bubbles.dailyrewards.DailyRewards;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class UtilString {

    private DailyRewards plugin;

    public UtilString(DailyRewards plugin) {
        this.plugin=plugin;
    }

    public String colorFillPlaceholders(String message) {
        FileConfiguration config = plugin.getConfigManager().getConfig("config.yml").getFileConfiguration();
        return ChatColor.translateAlternateColorCodes('&',message
                .replace("%prefix%", config.getString("prefix"))
                .replace("%primary%", config.getString("primaryColor"))
                .replace("%secondary%", config.getString("secondaryColor"))
        );
    }
}
