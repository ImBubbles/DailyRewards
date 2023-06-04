package me.bubbles.dailyrewards.util;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.config.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class UtilPlayerData {

    private Player player;
    private DailyRewards plugin;

    public UtilPlayerData(DailyRewards plugin, Player player) {
        this.plugin=plugin;
        this.player=player;
        setupPlayer();
    }

    public long getCoolDown() {
        if(plugin.isMariaDB()) {
            long result=0;
            try {
                result = plugin.getMariaDB().getTime(player.getUniqueId().toString());
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
            return result;
        }
        FileConfiguration data = plugin.getConfigManager().getConfig("data.yml").getFileConfiguration();
        return data.getLong(player.getUniqueId().toString());
    }

    public void restartCoolDown() {
        if(plugin.isMariaDB()) {
            plugin.getMariaDB().setTime(player.getUniqueId().toString(), plugin.getEpochTimestamp()+Values.EPOCH_DAY.getNum());
            return;
        }
        Config config = plugin.getConfigManager().getConfig("data.yml");
        FileConfiguration data = config.getFileConfiguration();
        data.set(player.getUniqueId().toString(),plugin.getEpochTimestamp()+Values.EPOCH_DAY.getNum());
    }

    public void resetCoolDown() {
        if(plugin.isMariaDB()) {
            plugin.getMariaDB().setTime(player.getUniqueId().toString(), plugin.getEpochTimestamp());
            return;
        }
        Config config = plugin.getConfigManager().getConfig("data.yml");
        FileConfiguration data = config.getFileConfiguration();
        data.set(player.getUniqueId().toString(),plugin.getEpochTimestamp());
    }

    private void setupPlayer() {
        if(plugin.isMariaDB()) {
            if(plugin.getMariaDB().getTime(player.getUniqueId().toString())==null) {
                plugin.getMariaDB().writeUser(player.getUniqueId().toString(),0);
            }
            return;
        }
        Config config = plugin.getConfigManager().getConfig("data.yml");
        FileConfiguration data = config.getFileConfiguration();
        if(!data.getKeys(false).contains(player.getUniqueId().toString())) {
            data.set(player.getUniqueId().toString(),0);
            config.save();
        }
    }

}
