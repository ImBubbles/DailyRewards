package me.bubbles.dailyrewards.users;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.util.UtilPlayerData;
import me.bubbles.dailyrewards.util.UtilRewardsData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class User {

    private Player player;
    private DailyRewards plugin;

    public User(Player player, DailyRewards plugin) {
        this.player=player;
        this.plugin=plugin;
    }

    // GETTERS
    public Player getPlayer() {
        return player;
    }

    public void redeemRewards() {
        if(!canRedeem()) {
            return;
        }
        UtilRewardsData utilRewardsData = new UtilRewardsData(plugin);
        for(String reward : utilRewardsData.getRewardsList()) {
            if(player.hasPermission(plugin.getName().toLowerCase()+"."+reward)) {
                for(String command : utilRewardsData.getCommandsList(reward)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command.replace("%name%", player.getName()));
                }
            }
        }
    }

    public boolean canRedeem() {
        UtilPlayerData playerData = new UtilPlayerData(plugin,player);
        return playerData.getCoolDown() <= plugin.getEpochTimestamp();
    }

    public boolean hasStuffToRedeem() {
        UtilRewardsData utilRewardsData = new UtilRewardsData(plugin);
        for(String reward : utilRewardsData.getRewardsList()) {
            if(player.hasPermission(plugin.getName().toLowerCase()+"."+reward)) {
                return true;
            }
        }
        return false;
    }

    public void restartCoolDown() {
        new UtilPlayerData(plugin,player).restartCoolDown();
    }

    public void resetCoolDown() {
        new UtilPlayerData(plugin,player).resetCoolDown();
    }

    public String getFormattedTimeLeft() {
        int coolDown = (int) (new UtilPlayerData(plugin,player).getCoolDown()-plugin.getEpochTimestamp());
        int hours = coolDown/60/60;
        int minutes = (coolDown/60)-(hours*60);
        int seconds = coolDown-(minutes*60)-(hours*60*60);

        StringBuilder stringBuilder = new StringBuilder();

        if(hours>0) {
            stringBuilder.append(hours).append("h");
            if(minutes>0) {
                stringBuilder.append(" ");
            }
        }

        if(minutes>0) {
            stringBuilder.append(minutes).append("m");
            if(seconds>0) {
                stringBuilder.append(" ");
            }
        }

        if(seconds>0) {
            stringBuilder.append(seconds).append("s");
        }

        return stringBuilder.toString();
    }

}
