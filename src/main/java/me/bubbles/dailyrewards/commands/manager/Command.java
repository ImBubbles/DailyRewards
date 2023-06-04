package me.bubbles.dailyrewards.commands.manager;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.util.UtilSender;
import me.bubbles.dailyrewards.util.Values;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;

public class Command implements CommandExecutor {

    public DailyRewards plugin;
    public String no_perms;

    private String command;
    private String permission;
    private HashSet<Argument> arguments = new HashSet<>();
    public UtilSender utilSender;
    public final int index=0;

    public Command(String command, DailyRewards plugin) {
        this.command=command;
        this.plugin=plugin;
    }

    public void run(CommandSender sender, String[] args) {
        this.utilSender=new UtilSender(plugin,sender);
        if(!(args.length==0)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : getArguments()) { // ARGUMENTS
                if(argument.getArg().equalsIgnoreCase(args[index])) {
                    argument.run(sender, args,false);
                    return;
                }
            }
        }
        utilSender.sendMessage(getArgsMessage());
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        this.utilSender=new UtilSender(plugin,sender);
        run(sender,args); // this is so I can use super statements for run
        return true;
    }

    public String getCommand() {
        return command;
    }

    public boolean permissionCheck() {
        if(permission==null)
            return true;
        if(!utilSender.isPlayer()) {
            return true;
        }
        Player player = utilSender.getPlayer();
        if(!player.hasPermission(permission)) {
            utilSender.sendMessage(no_perms);
            return false;
        }else{
            return true;
        }
    }

    public void setPermission(String permission) {
        String node = plugin.getName().toLowerCase() + "." + permission;
        this.permission=node;
        this.no_perms=Values.NO_PERMS.getString().replace("%node%",node);
    }

    public String getArgsMessage() {

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = "%prefix%" + "%primary%" + " Commands:";
        stringBuilder.append(topLine);

        for(Argument arg : arguments) {
            String command = "\n" + "%primary%" + "/" + getCommand() + "%secondary%" + " " + arg.getDisplay() + "\n";
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public HashSet<Argument> getArguments() {
        return arguments;
    }

}
