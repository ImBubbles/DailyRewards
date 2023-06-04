package me.bubbles.dailyrewards.commands.base;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.commands.manager.Argument;
import me.bubbles.dailyrewards.users.User;
import me.bubbles.dailyrewards.util.UtilPlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Reset extends Argument {

    public Reset(DailyRewards plugin, int index) {
        super(plugin, "reset", "reset <player>", index);
        setPermission("reset");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(args.length==index) {
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        if(Bukkit.getPlayer(args[index])==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find player.");
            return;
        }
        User user = plugin.getUserManager().getUser(Bukkit.getPlayer(args[index]));
        user.resetCoolDown();
        UtilPlayerMessage utilPlayerMessage = new UtilPlayerMessage(plugin,user.getPlayer());
        utilSender.sendMessage("%prefix% %primary%User %secondary%"+user.getPlayer().getName()+"'s%primary% cooldown has been reset.");
        utilPlayerMessage.sendMessage("%prefix% %primary%Your cooldown has been reset.");
    }

}
