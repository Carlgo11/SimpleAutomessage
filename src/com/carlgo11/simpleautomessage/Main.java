package com.carlgo11.simpleautomessage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public int tick = 1;
    int time = 0;
    public final static Logger logger = Logger.getLogger("Minercraft");

    public void onEnable() {
        getLogger().info(getDescription().getName() + getDescription().getVersion() + " is enabled!");
        this.reloadConfig();
        Broadcast();
        Time();
        checkConfig();
        checkMetrics();
    }

    public void checkMetrics() {
        if (!getConfig().getBoolean("out-put") == true) {
            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            } catch (IOException e) {
                System.out.println("[" + getDescription().getName() + "] Error Submitting stats!");
            }
        } else {
            System.out.println("[" + getDescription().getName() + "] out-put is set to false! The creator won't get information about this plugin!");
        }
    }

    public void checkConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.saveDefaultConfig();
            System.out.println("[SimpleAutoMessage] No config.yml detected, config.yml created");
        }
    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + getDescription().getVersion() + " is disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String prefix = "[" + getConfig().getString("Prefix") + "]  ";
        String badperm = prefix + ChatColor.RED + "Error: You don't have permission to use that command!";
        if (cmd.getName().equalsIgnoreCase("simpleautomessage")) {
            if (args.length == 0) {
                if (sender.hasPermission("SimpleAutoMessage.cmd.main") || sender.hasPermission("SimpleAutoMessage.cmd.*")) {
                    sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + getConfig().getString("Prefix") + ChatColor.GREEN + " ======== ");
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "SimpleAutoMessage" + ChatColor.YELLOW + " Shows the commands");
                    sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "SimpleAutoMessage Reload" + ChatColor.YELLOW + " Reloads the config.yml");
                } else {
                    sender.sendMessage(badperm);
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("SimpleAutoMessage.cmd.reload")) {
                        // this.reloadConfig();
                        getServer().getPluginManager().disablePlugin(this);
                        getServer().getPluginManager().enablePlugin(this);

                        sender.sendMessage(prefix + ChatColor.GREEN + "Automessage reloaded!");
                    } else {
                        sender.sendMessage(badperm);
                    }
                } else {
                    sender.sendMessage(prefix + ChatColor.RED + "Error: Unknown command!");
                }
            } else if (args.length > 1) {
                sender.sendMessage(prefix + ChatColor.RED + "Error: Unknown command!");
            }
        }
        return true;
    }

    @EventHandler
    public void Time() {
        time = getConfig().getInt("time");
        //add X 20 feature
        if (getConfig().getString("debug").equalsIgnoreCase("true")) {
            System.out.println("time: " + time);
        }
    }

    @EventHandler
    public void Broadcast() {
        final long d = getConfig().getLong("time");
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                if (d == getConfig().getLong("time")) {
                    if (getConfig().getString("debug").equalsIgnoreCase("true")) {
                        System.out.println("tick: " + tick);
                        System.out.println("time: " + time);
                    }
                    if (getConfig().contains("msg" + tick)) {
                        String outmsg = getConfig().getString("msg" + tick);
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            if (p.hasPermission("SimpleAutoMessage.seemsg")) {
                                p.sendMessage("[" + getConfig().getString("Prefix") + "]  " + getConfig().getString("msg" + tick));
                            }
                        }
                        if (getConfig().getBoolean("show-in-console") == true) {
                            System.out.println("[" + getConfig().getString("Prefix") + "]  " + getConfig().getString("msg" + tick));
                        }
                        tick++;
                    } else {
                        if (getConfig().contains("msg1")) {
                            String outmsg = getConfig().getString("msg1");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.hasPermission("SimpleAutoMessage.seemsg")) {
                                    p.sendMessage("[" + getConfig().getString("Prefix") + "]  " + ChatColor.RESET + getConfig().getString("msg1"));
                                }
                            }
                            if (getConfig().getBoolean("show-in-console") == true) {
                                 String consoleprefix = getConfig().getString("Prefix");
                                 consoleprefix.replaceAll("§1"+"§2"+"§d", "");
                                System.out.println("[" + consoleprefix + "]  " + outmsg);
                            }
                            tick = 2;
                        } else {
                            System.out.println("[" + getConfig().getString("Prefix") + "] Error: No msg1 set in the config.yml!");
                        }
                    }

                }
            }
        }, d, d);
    }

    public void onError() {
        Main.logger.warning("[SimpleAutoMessage] Error acurred! Plugin Disabeled!");
        Bukkit.getPluginManager().disablePlugin(this);
    }
}
