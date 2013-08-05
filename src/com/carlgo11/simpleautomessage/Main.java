package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.updater.Updater;
import com.carlgo11.simpleautomessage.metrics.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public int tick = 1;
    int time = 0;
    public final static Logger logger = Logger.getLogger("Minecraft");
    public String debugmsg = null;

    public void onEnable() {
        this.getLogger().info(getDescription().getName() + getDescription().getVersion() + " is enabled!");
        this.reloadConfig();
        checkVersion();
        Time();
        Broadcast();
        checkConfig();
        checkMetrics();
    }

    public void onDisable() {
        this.getLogger().info(getDescription().getName() + getDescription().getVersion() + " is disabled!");
    }

    public void checkVersion() {
        if (getConfig().getBoolean("auto-update") == true) {
            debugmsg = "Calling Updater.java";
            this.onDebug();
            Updater updater = new Updater(this, "simpleautomessage/", this.getFile(), Updater.UpdateType.DEFAULT, true);
        } else {
            debugmsg = "auto-update: is set to false!";
        }
    }

    public void checkMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            graph1(metrics);
            metrics.start();
        } catch (IOException e) {
            System.out.println("[" + getDescription().getName() + "] Error Submitting stats!");
        }


    }

    public void checkConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.saveDefaultConfig();
            System.out.println("[" + getDescription().getName() + "] No config.yml detected, config.yml created");
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String prefixToSend = getConfig().getString("prefix");
        String prefix = ChatColor.translateAlternateColorCodes('&', prefixToSend);
        String badperm = prefix + ChatColor.RED + "Error: You don't have permission to use that command!";
        if (cmd.getName().equalsIgnoreCase("simpleautomessage")) {
            if (args.length == 0) {
                if (sender.hasPermission("SimpleAutoMessage.cmd.main") || sender.hasPermission("SimpleAutoMessage.cmd.*")) {
                    sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + prefix + ChatColor.GREEN + " ======== ");
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
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + ChatColor.stripColor(prefix) + ChatColor.GREEN + "SimpleAutoMessage reloaded!");
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
    public void Time() { // Time string method. checks the delay between each message.
        if (!getConfig().contains("time")) {
            debugmsg = "No time string found!";
            onDebug();
        }
        time = getConfig().getInt("time");
        if (getConfig().contains("Time-setup")) {
            debugmsg = "Time-setup: string found!";
            onDebug();
            if (getConfig().getString("Time-setup").equalsIgnoreCase("sec")) {
                time *= 20;
            }
        } else {
            time *= 20;
        }
        debugmsg = "time: " + time;
        onDebug();
    }

    @EventHandler
    public void Broadcast() { // Broadcast method
        final long d = (long) (time);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                if (getConfig().getString("debug").equalsIgnoreCase("true")) {
                    debugmsg = "tick: " + tick;
                    onDebug();
                    debugmsg = "time: " + d;
                    onDebug();
                }
                String prefixToSend = getConfig().getString("prefix");
                String prefixToMC = ChatColor.translateAlternateColorCodes('&', prefixToSend);

                if (getConfig().contains("msg" + tick)) {
                    String messageToSend = getConfig().getString("msg" + tick);
                    String msgToMC = ChatColor.translateAlternateColorCodes('&', messageToSend);
                    getServer().broadcast("" + prefixToMC + "  " + ChatColor.RESET + msgToMC, "SimpleAutoMessage.seemsg");
                    tick++;
                } else {
                    debugmsg = "no msg" + tick + " set in the config. calling msg1 instead.";
                    onDebug();

                    if (getConfig().contains("msg1")) {

                        String messageToSend = getConfig().getString("msg1");
                        String msgToMC = ChatColor.translateAlternateColorCodes('&', messageToSend);
                        getServer().broadcast("" + prefixToMC + "  " + ChatColor.RESET + msgToMC, "SimpleAutoMessage.seemsg");
                        tick = 2;
                    } else {
                        System.out.println(ChatColor.stripColor(prefixToMC) + " Error: No msg1 set in the config.yml! ");
                    }
                }
            }
        }, d, d);
    }

    public void onError() { // Sends error msg to console and disables the plugin.
        Main.logger.warning("[SimpleAutoMessage] Error acurred! Plugin disabeled!");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    public void onDebug() { // Debug message method
        if (getConfig().getBoolean("debug") == true) {
            Main.logger.info("[SimpleAutoMessage] " + debugmsg);
        }
    }

    public void graph1(Metrics metrics) { // Custom Graph. Sends msg string data to mcstats.org

        Metrics.Graph graph = metrics.createGraph("Messages");
        graph.addPlotter(new SimplePlotter("Messages") {
            @Override
            public int getValue() {
                int o = 0;
                for (int i = 1; getConfig().contains("msg" + i); i++) {
                    o = i;
                }
                debugmsg = "Metrics data sent";
                onDebug();
                return o;
            }
        });
        metrics.start();
    }

    public class SimplePlotter extends Metrics.Plotter {

        public SimplePlotter(final String name) {
            super(name);
        }

        @Override
        public int getValue() {
            return 1;
        }
    }
}