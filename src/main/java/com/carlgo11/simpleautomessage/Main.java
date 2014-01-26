package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.commands.*;
import com.carlgo11.simpleautomessage.updater.*;
import com.carlgo11.simpleautomessage.language.*;
import com.carlgo11.simpleautomessage.player.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.carlgo11.simpleautomessage.metrics.*;

public class Main extends JavaPlugin {

    public int tick = 1; // msg+<tick> int
    public int time = 0; // the delay
    public final static Logger logger = Logger.getLogger("Minecraft");
    public static YamlConfiguration LANG;
    public static File LANG_FILE;
    public boolean update = false;
    public boolean debugm;
    public String configv = "1.0.6";

    public void onEnable()
    {
        reloadConfig();
        getServer().getPluginManager().registerEvents(new Time(this), this);
        checkConfig();
        checkDebugMode();
        checkVersion();
        checkMetrics();
        getServer().getPluginManager().registerEvents(new loadLang(this), this);
        getServer().getPluginManager().registerEvents(new Broadcast(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        commands();
        getLogger().info(getDescription().getName() + " " + getDescription().getVersion() + " " + Lang.ENABLED);
    }

    public void onDisable()
    {
        getLogger().info(getDescription().getName() + " " + getDescription().getVersion() + " " + Lang.DISABLED);
    }

    public void commands()
    {
        getCommand("simpleautomessage").setExecutor(new SimpleautomessageCommand(this));
    }

    public void checkVersion()
    {
        if (getDescription().getVersion().startsWith("dev")) { // prints out a warning when using dev build
            getLogger().warning("You are using a development build! Keep in mind development builds may contain bugs!");
            getLogger().warning("If you want a fully working version please use a recommended build!");
            getLogger().warning("Type /simpleautomessage update to download the latest recommended build.");
        }

        if (getConfig().getBoolean("auto-update")) {
            debug("Calling Updater.java");
            Updater updater = new Updater(this, 49417, getFile(), Updater.UpdateType.DEFAULT, true);
            update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;

        } else if (!getConfig().getString("warn-update").equalsIgnoreCase("none")) {
            Updater updater = new Updater(this, 49417, getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
            update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
        } else {
            debug("auto-update & warn-update is set to false!");
        }
    }

    public void checkMetrics()
    {
        try {
            Metrics metrics = new Metrics(this);
            CustomGraphs.graphs(metrics, this);
            metrics.start();
        } catch (IOException ex) {
            System.out.println("[" + getDescription().getName() + "] " + Lang.STATS_ERROR + "Output: " + ex.toString());
        }
    }

    public void checkConfig()
    {
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
            System.out.println("[" + getDescription().getName() + "] " + "No config.yml detected, config.yml created.");
        }
        if (getConfig().getBoolean("update-config")) {
            if (!getConfig().getString("version").equals(this.configv)) {
                if (!getDescription().getVersion().startsWith("dev")) {
                    config.renameTo(new File(getDataFolder(), "config.version-" + getConfig().getString("version") + ".yml"));
                    saveDefaultConfig();
                } else {
                    debug("The plugin-version is a dev-build. Will not try to reload the config.");
                }
            }
        } else {
            debug("update-config is set to false.");
        }
    }

    public YamlConfiguration getLang()
    {
        return LANG;
    }

    public File getLangFile()
    {
        return LANG_FILE;
    }

    public void debug(String s)
    {
        if (debugm) {
            getLogger().log(Level.INFO, "[" + "Debug" + "]" + " {0}", s);
        }
    }

    public void checkDebugMode()
    {
        if (getConfig().getBoolean("debug")) {
            debugm = true;
        }
    }

    public void forceUpdate(CommandSender p, String sender0)
    {
        String up = Lang.UPDATING.toString().replaceAll("%prefix%", getDescription().getName());
        String updone = Lang.UPDATED.toString().replaceAll("%prefix%", getDescription().getName());
        p.sendMessage(sender0 + " " + ChatColor.GREEN + up);
        Updater updater = new Updater(this, 49417, getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
        p.sendMessage(sender0 + " " + ChatColor.GREEN + updone);
    }
}
