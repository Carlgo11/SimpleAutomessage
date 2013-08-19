package com.carlgo11.simpleautomessage.commands;

import com.carlgo11.simpleautomessage.Main;
import com.carlgo11.simpleautomessage.language.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SimpleautomessageCommand implements CommandExecutor {

    private Main plugin;

    public SimpleautomessageCommand(Main plug) {
        this.plugin = plug;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String prefixToSend = plugin.getConfig().getString("prefix");
        String prefix = ChatColor.translateAlternateColorCodes('&', prefixToSend);
        if (cmd.getName().equalsIgnoreCase("simpleautomessage")) {
            if (args.length == 0) {
                if (sender.hasPermission("SimpleAutoMessage.cmd.main") || sender.hasPermission("SimpleAutoMessage.cmd.*")) {
                    sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + prefix + ChatColor.GREEN + " ======== ");
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "SimpleAutoMessage" + ChatColor.YELLOW + " Shows the commands");
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "SimpleAutoMessage Reload" + ChatColor.YELLOW + " Reloads the config.yml");
                } else {
                    sender.sendMessage(Lang.BAD_PERMS+"");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("SimpleAutoMessage.cmd.reload")) {
                        plugin.getServer().getPluginManager().disablePlugin(plugin);
                        plugin.getServer().getPluginManager().enablePlugin(plugin);
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + ChatColor.stripColor(prefix) + " " + Lang.PL_RELOADED);
                    } else {
                        sender.sendMessage(Lang.BAD_PERMS+"");
                    }
                } else {
                    sender.sendMessage(prefix + " " + Lang.UNKNOWN_CMD);
                }
            } else if (args.length > 1) {
                sender.sendMessage(prefix + " " + Lang.UNKNOWN_CMD);
            }
        }
        return true;
    }
}
