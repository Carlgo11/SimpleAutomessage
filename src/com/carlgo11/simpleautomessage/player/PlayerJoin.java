package com.carlgo11.simpleautomessage.player;

import com.carlgo11.simpleautomessage.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Main plugin;

    public PlayerJoin(Main plug)
    {
        this.plugin = plug;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if (plugin.getConfig().getBoolean("auto-update") == false && !plugin.getDescription().getVersion().startsWith("dev-")) {
            String upd = plugin.getConfig().getString("warn-update");
            Player p = e.getPlayer();
            String pn = e.getPlayer().getName();
            String prefix = ChatColor.LIGHT_PURPLE + "[" + plugin.getDescription().getName() + "]" + ChatColor.RESET;
            if (upd.equalsIgnoreCase("op")) {
                if (p.isOp() && plugin.update) {
                    p.sendMessage(prefix + ChatColor.GREEN + " An update is available!\nType " + ChatColor.BLUE + ChatColor.ITALIC + "/simpleautomessage update" + ChatColor.RESET + ChatColor.GREEN + " to update the plugin.");
                }
            } else if (upd.equalsIgnoreCase("perm")) {
                if (p.hasPermission("simpleAutoMessage.notify")) {
                    p.sendMessage(prefix + "" + ChatColor.GREEN + " An update is available!\nType " + ChatColor.BLUE + ChatColor.ITALIC + "/simpleautomessage update" + ChatColor.RESET + ChatColor.GREEN + " to update the plugin.");
                }
            }
        }
    }
}
