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
    public String debugmsg = null;

    public void onEnable() {
        this.getLogger().info(getDescription().getName() + getDescription().getVersion() + " is enabled!");
        this.reloadConfig();
        Broadcast();
        Time();
        checkConfig();
        checkMetrics();
    }

    public void onDisable() {
        this.getLogger().info(getDescription().getName() + getDescription().getVersion() + " is disabled!");
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

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String plainprefix = getConfig().getString("Prefix").toString().replaceAll("Â", "");
        String prefix = "[" + plainprefix + "]  ";
        String badperm = prefix + ChatColor.RED + "Error: You don't have permission to use that command!";
        if (cmd.getName().equalsIgnoreCase("simpleautomessage")) {
            if (args.length == 0) {
                if (sender.hasPermission("SimpleAutoMessage.cmd.main") || sender.hasPermission("SimpleAutoMessage.cmd.*")) {
                    sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + plainprefix + ChatColor.GREEN + " ======== ");
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

                        sender.sendMessage(prefix + ChatColor.GREEN + "SimpleAutoMessage reloaded!");
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
        debugmsg = "time: " + time;
        onDebug();
    }

    @EventHandler
    public void Broadcast() {
        final long d = getConfig().getLong("time");
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                if (d == getConfig().getLong("time")) {
                    if (getConfig().getString("debug").equalsIgnoreCase("true")) {
                        debugmsg = "tick: " + tick;
                        onDebug();
                        debugmsg = "time: " + time;
                        onDebug();
                    }
                    String prefixtomc = getConfig().getString("Prefix").toString().replaceAll("Â", "");
                    String prefixtoconsole = getConfig().getString("Prefix").toString().replaceAll("Â", "");

                    if (getConfig().contains("msg" + tick)) {
                        String msgtomc = getConfig().getString("msg" + tick).toString().replaceAll("Â", "");
                        String msgtoconsole = getConfig().getString("msg" + tick).toString().replaceAll("Â", "");
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            if (p.hasPermission("SimpleAutoMessage.seemsg")) {
                                p.sendMessage("[" + prefixtomc + "]  " + ChatColor.RESET + msgtomc);
                            }
                        }

                        if (getConfig().getBoolean("show-in-console") == true) {
                            System.out.println("[" + prefixtoconsole + "]  " + msgtoconsole);
                        }
                        tick++;

                    } else {
                        debugmsg = "no msg" + tick + " set in the config. calling msg1 instead.";
                        onDebug();
                        String msgtomc = getConfig().getString("msg1").toString().replaceAll("Â", "");
                        String msgtoconsole = getConfig().getString("msg1").toString().replaceAll("Â", "");
                        if (getConfig().contains("msg1")) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.hasPermission("SimpleAutoMessage.seemsg")) {
                                    p.sendMessage("[" + prefixtomc + "]  " + ChatColor.RESET + msgtomc);
                                }
                            }
                            if (getConfig().getBoolean("show-in-console") == true) {
                                System.out.println("[" + prefixtoconsole + "]  " + msgtoconsole);
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

    public void onDebug() {
        if (getConfig().getBoolean("debug") == true) {
            Main.logger.info("[SimpleAutoMessage] " + debugmsg);
        }
    }
}
