package com.carlgo11.simpleautomessage.player;

import com.carlgo11.simpleautomessage.Main;
import com.carlgo11.simpleautomessage.Players;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final Main plugin;

    public PlayerJoin(Main plug)
    {
        this.plugin = plug;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        String upd = plugin.getConfig().getString("warn-update");
        Player p = e.getPlayer();
        String prefix = ChatColor.LIGHT_PURPLE + "[" + plugin.getDescription().getName() + "]" + ChatColor.RESET;
        if (!plugin.getConfig().getBoolean("auto-update")) {
            String updateav = prefix + ChatColor.GREEN + " An update is available!\nType " + ChatColor.BLUE + ChatColor.ITALIC + "/simpleautomessage update" + ChatColor.RESET + ChatColor.GREEN + " to update.";
            if (upd.equalsIgnoreCase("op")) {
                if (p.isOp() && plugin.update) {
                    p.sendMessage(updateav);
                }
            } else if (upd.equalsIgnoreCase("perm")) {
                if (Players.checkPerms(p, "simpleautomessage.notify", plugin) && plugin.update) {
                    p.sendMessage(updateav);
                }
            }
        }
    }
}
