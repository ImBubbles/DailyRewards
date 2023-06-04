package me.bubbles.dailyrewards.commands.base;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.commands.manager.Argument;
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
        if(!utilSender.getUser().hasStuffToRedeem()) {
            utilSender.sendMessage("%prefix% %primary%You don't have anything to claim!");
            return;
        }
        if(!utilSender.getUser().canRedeem()) {
            utilSender.sendMessage("%prefix% %primary%You can claim your daily rewards in %secondary%"+utilSender.getUser().getFormattedTimeLeft()+"%primary%.");
            return;
        }
        utilSender.getUser().redeemRewards();
        utilSender.sendMessage("%prefix% %primary%You have claimed your daily rewards!");
        utilSender.getUser().restartCoolDown();
    }

}
