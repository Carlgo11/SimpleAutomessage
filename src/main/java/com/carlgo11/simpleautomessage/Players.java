package com.carlgo11.simpleautomessage;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Players {

    public static boolean checkPerms(Player player, String permission, Main Main)
    {
        return player.hasPermission(permission) || player.hasPermission("simpleautomessage.*");
    }

    public static boolean checkPerms(CommandSender player, String permission, Main Main)
    {
        return player.hasPermission(permission) || player.hasPermission("simpleautomessage.*");
    }
}
