package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.commands.SimpleautomessageCommand;
import com.carlgo11.simpleautomessage.updater.Updater;
import com.carlgo11.simpleautomessage.language.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public int tick = 1; // msg+<tick> int
    public int time = 0; // the delay
    public final static Logger logger = Logger.getLogger("Minecraft");
    public String debugmsg = null;
    private Broadcast brdcst;
    private Time tme;
    public static YamlConfiguration LANG;
    public static File LANG_FILE;

    public void onEnable() {
        this.reloadConfig();
        getServer().getPluginManager().registerEvents(new Time(this), this);
        checkVersion();
        checkConfig();
        getServer().getPluginManager().registerEvents(new loadLang(this), this);
        getCommand("simpleautomessage").setExecutor(new SimpleautomessageCommand(this));
        getServer().getPluginManager().registerEvents(new Broadcast(this), this);
        this.getLogger().info(getDescription().getName() + " " + getDescription().getVersion() + " " + Lang.ENABLED);
    }

    public void onDisable() {
        this.getLogger().info(getDescription().getName() + " " + getDescription().getVersion() + " " + Lang.DISABLED);
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

    public void checkConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.saveDefaultConfig();
            File locale = new File(this.getDataFolder() + "/language");
            if (locale.exists()) {
                System.out.println("[" + getDescription().getName() + "] " + Lang.NO_CONFIG);
            } else {
                System.out.println("[" + getDescription().getName() + "] " + "No config.yml detected, config.yml created.");
            }
        }
    }

    public YamlConfiguration getLang() {
        return LANG;
    }

    public File getLangFile() {
        return LANG_FILE;
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
}