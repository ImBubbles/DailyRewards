package me.bubbles.dailyrewards.commands.manager;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.commands.base.BaseCommand;

import java.util.HashSet;

public class CommandManager {
    private DailyRewards plugin;

    public CommandManager(DailyRewards plugin) {
        this.plugin=plugin;
        registerCommands();
    }

    public void registerCommands() {
        addCommand(
                new BaseCommand(plugin)
        );
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            try {
                plugin.getCommand(command.getCommand()).setExecutor(command);
                if(!command.getArguments().isEmpty()) {
                    registerArguments(command.getArguments());
                }
            } catch (NullPointerException e) {
                plugin.getLogger().warning("Command /"+command.getCommand()+", could not be registered. Most likely due to improper plugin.yml");
            }
        }
    }

    public void registerArguments(HashSet<Argument> arguments) {
        for(Argument argument : arguments) {
            if(argument.getAlias()!=null) {
                try {
                    plugin.getCommand(argument.getAlias()).setExecutor(argument);
                } catch (NullPointerException e) {
                    plugin.getLogger().warning("Command /"+argument.getAlias()+", could not be registered. Most likely due to improper plugin.yml");
                }
            }
            if(!argument.getArguments().isEmpty()) {
                registerArguments(argument.getArguments());
            }
        }
    }

}
