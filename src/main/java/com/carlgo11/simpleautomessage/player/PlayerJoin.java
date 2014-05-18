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
                if (p.hasPermission("simpleautomessage.notify") && plugin.update) {
                    p.sendMessage(updateav);
                }
            }
        }
        if (!plugin.getConfig().getString("version").equals(plugin.configv)) {
            if (plugin.getConfig().getString("ignore-old-configs").equalsIgnoreCase("perm") && p.hasPermission("simpleautomessage.notify")) {
                p.sendMessage(prefix + ChatColor.GREEN + " Your config.yml is outdated. You should probably create a new one.");
            } else if (plugin.getConfig().getString("ignore-old-configs").equalsIgnoreCase("op") && p.isOp()) {
                p.sendMessage(prefix + ChatColor.GREEN + " Your config.yml is outdated. You should probably create a new one.");
            }
        }
    }
}
