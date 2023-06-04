package me.bubbles.dailyrewards.commands.manager;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.util.UtilSender;
import me.bubbles.dailyrewards.util.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;

public class Argument implements CommandExecutor {

    public DailyRewards plugin;
    public UtilSender utilSender;
    public int index;
    private HashSet<Argument> arguments = new HashSet<>();
    public String no_perms;
    private String arg;
    private String display;
    private String permission;
    private String alias;

    // CONSTRUCTORS
    public Argument(DailyRewards plugin, String arg, String display, int index) {
        this.plugin=plugin;
        this.index=index+1;
        this.arg=arg;
        this.display=display;
    }

    public Argument(DailyRewards plugin, String arg, int index) {
        this(plugin,arg,arg,index);
    }

    // ON RUN
    public void run(CommandSender sender, String[] args, boolean alias) {
        this.utilSender=new UtilSender(plugin,sender);
        if(alias) {
            index=0;
        }
        if(!(args.length==index)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : getArguments()) { // ARGUMENTS
                if(argument.getArg().equalsIgnoreCase(args[index])) {
                    argument.run(sender, args,false);
                    return;
                }
            }
        }
    }

    // ON ALIAS
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        run(sender,args,true);
        return true;
    }

    // ARGUMENTS
    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public String getArg() {
        return arg;
    }

    public HashSet<Argument> getArguments() {
        return arguments;
    }

    // ALIAS
    public void setAlias(String alias) {
        this.alias=alias;
    }

    public String getAlias() {
        return alias;
    }

    // PERMISSION

    public void setPermission(String permission) {
        String node = plugin.getName().toLowerCase() + "." + permission;
        this.permission=node;
        this.no_perms=Values.NO_PERMS.getString().replace("%node%",node);
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

    // GETTERS

    public String getDisplay() {
        return display;
    }

    public String getArgsMessage() {

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = "%prefix%" + "%primary%" + " " + display;
        stringBuilder.append(topLine);

        for(Argument argument : arguments) {
            String command = "\n" + "%primary%" + argument.getDisplay();
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

}
