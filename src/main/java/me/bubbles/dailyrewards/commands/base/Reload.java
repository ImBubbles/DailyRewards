package me.bubbles.dailyrewards.commands.base;

import me.bubbles.dailyrewards.DailyRewards;
import me.bubbles.dailyrewards.commands.manager.Argument;
import org.bukkit.command.CommandSender;

public class Reload extends Argument {

    public Reload(DailyRewards plugin, int index) {
        super(plugin, "reload", index);
        setPermission("reload");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        utilSender.sendMessage("%prefix% %primary%Config reloaded.");
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.reload();
    }

}
