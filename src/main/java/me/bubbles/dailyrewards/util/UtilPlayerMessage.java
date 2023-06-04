package me.bubbles.dailyrewards.util;

import me.bubbles.dailyrewards.DailyRewards;
import org.bukkit.entity.Player;

public class UtilPlayerMessage {

    private Player player;
    private DailyRewards plugin;

    public UtilPlayerMessage(DailyRewards plugin, Player player) {
        this.player=player;
        this.plugin=plugin;
    }

    public void sendMessage(String string) {
        player.sendMessage(new UtilString(plugin).colorFillPlaceholders(string));
    }

}
