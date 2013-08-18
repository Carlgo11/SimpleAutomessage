package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.commands.SimpleautomessageCommand;
import com.carlgo11.simpleautomessage.updater.Updater;
import com.carlgo11.simpleautomessage.metrics.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    public int tick = 1; // msg+<tick> int
    public int time = 0; // the delay
    public final static Logger logger = Logger.getLogger("Minecraft");
    public String debugmsg = null;
    private Broadcast brdcst;
    private Time tme;
    
    public void onEnable() {
        this.reloadConfig();
        getServer().getPluginManager().registerEvents(new Time(this), this);
        checkVersion();
        checkConfig();
        checkMetrics();
        getCommand("simpleautomessage").setExecutor(new SimpleautomessageCommand(this));
        getServer().getPluginManager().registerEvents(new Broadcast(this), this);
        this.getLogger().info(getDescription().getName() + getDescription().getVersion() + " is enabled!");
    }
    
    public void onDisable() {
        this.getLogger().info(getDescription().getName() + getDescription().getVersion() + " is disabled!");
    }
    
    public void checkVersion() {
        if (getDescription().getVersion().startsWith("dev-")) {
            this.getLogger().warning("You are using a development build! Keep in mind development builds might contain bugs!");
            this.getLogger().warning("If you want a fully working version please use a recommended build!");
        }
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
            graphs(metrics);
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
    
    public void onError() { // Sends error msg to console and disables the plugin.
        Main.logger.warning("[SimpleAutoMessage] Error acurred! Plugin disabeled!");
        Bukkit.getPluginManager().disablePlugin(this);
    }
    
    public void onDebug() { // Debug message method
        if (getConfig().getBoolean("debug") == true) {
            Main.logger.info("[SimpleAutoMessage] " + debugmsg);
        }
    }
    
    public void graphs(Metrics metrics) { // Custom Graphs. Sends data to mcstats.org
        try {
            //Graph1
            Metrics.Graph graph1 = metrics.createGraph("Messages"); //Sends data about how many msg strings the user has.
            int o = 0;
            for (int i = 1; getConfig().contains("msg" + i); i++) {
                o = i;
            }
            graph1.addPlotter(new SimplePlotter(o + ""));
            
            //Graph2
            Metrics.Graph graph2 = metrics.createGraph("auto-update"); //Sends auto-update data. if auto-update: is true it returns 'enabled'.
            if (getConfig().getBoolean("auto-update") == true) {
                graph2.addPlotter(new SimplePlotter("enabled"));
            } else {
                graph2.addPlotter(new SimplePlotter("disabled"));
            }
            debugmsg = "Metrics sent!";
            onDebug();
            metrics.start();
        } catch (Exception e) {
            this.getLogger().warning(e.getMessage());
        }
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