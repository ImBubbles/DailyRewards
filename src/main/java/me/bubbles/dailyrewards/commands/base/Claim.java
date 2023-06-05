package me.bubbles.dailyrewards.commands.base;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.commands.manager.Argument;
import me.bubbles.dailyrewards.util.UtilUser;
import org.bukkit.command.CommandSender;

public class Claim extends Argument {

    public Claim(DailyRewards plugin, int index) {
        super(plugin, "claim", index);
        setAlias("daily");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this!");
            return;
        }
        UtilUser utilUser = utilSender.getUser();
        if(!utilUser.hasStuffToRedeem()) {
            utilSender.sendMessage("%prefix% %primary%You don't have anything to claim!");
            return;
        }
        if(!utilUser.canRedeem()) {
            utilSender.sendMessage("%prefix% %primary%You can claim your daily rewards in %secondary%"+ utilUser.getFormattedTimeLeft()+"%primary%.");
            return;
        }
        utilUser.redeemRewards();
        utilSender.sendMessage("%prefix% %primary%You have claimed your daily rewards!");
        utilUser.restartCoolDown();
    }

}
