package me.bubbles.dailyrewards.util;

import me.bubbles.dailyrewards.DailyRewards;

import java.util.List;
import java.util.Set;

public class UtilRewardsData {

    private DailyRewards plugin;

    public UtilRewardsData(DailyRewards plugin) {
        this.plugin=plugin;
    }

    public Set<String> getRewardsList() {
        return plugin.getConfigManager().getConfig("rewards.yml").getFileConfiguration().getKeys(false);
    }

    public List<String> getCommandsList(String key) {
        return plugin.getConfigManager().getConfig("rewards.yml").getFileConfiguration().getStringList(key);
    }

}
