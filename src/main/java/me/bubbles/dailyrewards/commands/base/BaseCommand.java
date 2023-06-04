package me.bubbles.dailyrewards.commands.base;

import me.bubbles.dailyrewards.commands.manager.Command;
import org.bukkit.command.CommandSender;

public class BaseCommand extends Command {

    public BaseCommand(me.bubbles.dailyrewards.DailyRewards plugin) {
        super("dailyrewards", plugin);
        addArguments(
                new Claim(plugin,index),
                new Reset(plugin,index),
                new Reload(plugin,index)
        );
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
    }

}
