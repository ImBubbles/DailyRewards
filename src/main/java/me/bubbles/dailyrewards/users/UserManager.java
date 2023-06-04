package me.bubbles.dailyrewards.users;

import me.bubbles.dailyrewards.DailyRewards;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class UserManager {

    private DailyRewards plugin;
    private HashSet<User> userList = new HashSet<>();

    public UserManager(DailyRewards plugin) {
        this.plugin=plugin;
    }

    public User getUser(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return user;
        }
        User user = new User(p,plugin);
        userList.add(user);
        return user;
    }

}
