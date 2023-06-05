package me.bubbles.dailyrewards.commands.base;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.commands.manager.Argument;
import me.bubbles.dailyrewards.util.UtilUser;
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
        UtilUser utiluser = new UtilUser(Bukkit.getPlayer(args[index]),plugin);
        utiluser.resetCoolDown();
        UtilPlayerMessage utilPlayerMessage = new UtilPlayerMessage(plugin, utiluser.getPlayer());
        utilSender.sendMessage("%prefix% %primary%User %secondary%"+ utiluser.getPlayer().getName()+"'s%primary% cooldown has been reset.");
        utilPlayerMessage.sendMessage("%prefix% %primary%Your cooldown has been reset.");
    }

}
