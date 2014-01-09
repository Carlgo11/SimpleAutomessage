package com.carlgo11.simpleautomessage.commands;

import com.carlgo11.simpleautomessage.Main;
import com.carlgo11.simpleautomessage.NothingHere;
import com.carlgo11.simpleautomessage.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SimpleautomessageCommand implements CommandExecutor {

    private Main plugin;

    public SimpleautomessageCommand(Main plug)
    {
        this.plugin = plug;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        String senderToSend = plugin.getConfig().getString("sender");
        String sender0 = ChatColor.translateAlternateColorCodes('&', senderToSend);
        String prefixToSend = plugin.getConfig().getString("prefix");
        String prefix = ChatColor.translateAlternateColorCodes('&', prefixToSend);
        String suffixToSend = plugin.getConfig().getString("suffix");
        String suffix = ChatColor.translateAlternateColorCodes('&', suffixToSend);
        String pn = sender.getName();
        if (cmd.getName().equalsIgnoreCase("simpleautomessage")) {
            if (args.length == 0) {
                if (sender.hasPermission("simpleAutoMessage.simpleautomessage")) {
                    sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + "[" + plugin.getDescription().getName() + "]" + ChatColor.GREEN + " ======== ");
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + ChatColor.YELLOW + Lang.Simplemsg_Main);
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " Reload" + ChatColor.YELLOW + Lang.Simplemsg_Reload);
                    if (sender.hasPermission("simpleAutoMessage.simpleautomessage.update")) {
                        if (plugin.update) {
                            sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.GREEN + cmd.getName() + " Update" + ChatColor.GREEN + Lang.Simplemsg_Update);
                        } else {
                            sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " Update" + ChatColor.YELLOW + Lang.Simplemsg_Update);
                        }
                    }
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " Update" + ChatColor.YELLOW + Lang.Simplemsg_Update);
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " List" + ChatColor.YELLOW + Lang.Simplemsg_List);
                } else {
                    sender.sendMessage(Lang.BAD_PERMS + "");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("simpleAutoMessage.simpleautomessage.reload")) {
                        plugin.getServer().getPluginManager().disablePlugin(plugin);
                        plugin.getServer().getPluginManager().enablePlugin(plugin);
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + ChatColor.stripColor(sender0) + ChatColor.GREEN + " " + Lang.PL_RELOADED);
                    } else {
                        sender.sendMessage(Lang.BAD_PERMS + "");
                    }
                } else {
                    if (args[0].equalsIgnoreCase("list")) {
                        if (sender.hasPermission("simpleAutoMessage.simpleautomessage.list")) {
                            sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + sender0 + ChatColor.GREEN + " ======== ");
                            sender.sendMessage(ChatColor.GRAY + "min-players" + ": " + ChatColor.RESET + plugin.getConfig().getInt("min-players"));
                            sender.sendMessage(ChatColor.GRAY + "time" + ": " + ChatColor.RESET + plugin.getConfig().getInt("time"));
                            sender.sendMessage(ChatColor.GRAY + "random" + ": " + ChatColor.RESET + plugin.getConfig().getBoolean("random"));
                            sender.sendMessage(ChatColor.GRAY + "prefix" + ": " + ChatColor.RESET + prefix);
                            sender.sendMessage(ChatColor.GRAY + "sender" + ": " + ChatColor.RESET + sender0);
                            sender.sendMessage(ChatColor.GRAY + "suffix" + ": " + ChatColor.RESET + suffix);
                            int err = 0;
                            for (int i = 1; err != 1; i++) {
                                if (plugin.getConfig().contains("msg" + i)) {
                                    String messageToSend = plugin.getConfig().getString("msg" + i);
                                    String msgToMC = ChatColor.translateAlternateColorCodes('&', messageToSend);
                                    sender.sendMessage(ChatColor.GRAY + "msg" + i + ": '" + ChatColor.RESET + msgToMC + "'");
                                    plugin.onDebug("Found msg" + i);
                                } else {
                                    err++;
                                    plugin.onDebug("Did not find msg" + i);
                                }
                            }
                        } else {
                            sender.sendMessage(Lang.BAD_PERMS + "");
                        }
                    } else if (args[0].equalsIgnoreCase("update")) {
                        if (sender.hasPermission("simpleAutoMessage.simpleautomessage.update")) {
                            plugin.forceUpdate(sender, sender0);
                        } else {
                            sender.sendMessage(Lang.BAD_PERMS + "");
                        }
                    } else if (args[0].equalsIgnoreCase("moo")) {
                        sender.sendMessage(NothingHere.playerMoo);
                        Bukkit.getPlayer(pn).getWorld().playSound(Bukkit.getPlayer(pn).getLocation(), Sound.COW_IDLE, 1, 0);
                    } else {
                        sender.sendMessage("" + Lang.UNKNOWN_CMD);
                    }
                }
            } else if (args.length > 1) {
                sender.sendMessage("" + Lang.UNKNOWN_CMD);
            }
        }
        return true;
    }

}
