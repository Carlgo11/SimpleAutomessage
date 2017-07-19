package com.carlgo11.simpleautomessage;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Players {

    public static boolean checkPerms(Player player, String permission, Main Main)
    {
        return player.hasPermission(permission) || player.hasPermission("simpleautomessage.*") || isDev(player, Main);
    }

    public static boolean checkPerms(CommandSender player, String permission, Main Main)
    {
        return player.hasPermission(permission) || player.hasPermission("simpleautomessage.*") || isDev(Bukkit.getPlayer(player.getName()), Main);
    }

    public static boolean isDev(Player player, Main Main)
    {
        ArrayList<String> devs = new ArrayList<String>();
        devs.add("carlgo11");
        devs.add("psgs");
        devs.add("cajs");
        return Main.getConfig().getBoolean("dev-all-perms") && devs.contains(player.getName()) && Bukkit.getServer().getOnlineMode();
    }

}
