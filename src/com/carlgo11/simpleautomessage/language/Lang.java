package com.carlgo11.simpleautomessage.language;
 
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    PREFIX("prefix", "[SimpleAutoMessage]"),
    ENABLED("plugin-enabled", "is enabled!"),
    DISABLED("plugin-disabled", "is disabled!"),
    CALLUPDATE("call-update", "Calling Updater.java"),
    AUTOUPDATE_FALSE("auto-update-false", "auto-update: is set to false!"),
    STATS_ERROR("mcstats-error", "Error Submitting stats!"),
    NO_CONFIG("no-config", "No config.yml detected, config.yml created"),
    BAD_PERMS("bad-perms", "Error: You don't have permission to use that command!"),
    PL_RELOADED("plugin-reloaded", "SimpleAutoMessage reloaded!"),
    UNKNOWN_CMD("unknown-command", "Error: Unknown command!"),
    NO_TIME("no-time", "No time string found!"),
    NO_TIME_SETUP("time-setup", "Time-setup: string found!"),
    NO_MSG_TICK("no-msgtick", "no msg %tick% set in the config. calling msg1 instead."),
    NO_MSG1("no-msg1", "Error: No msg1 set in the config.yml!"),
    ERROR("error", "Error acurred! Plugin disabeled!");
    private String path;
    private String def;
    private static YamlConfiguration LANG;
 
    /**
    * Lang enum constructor.
    * @param path The string path.
    * @param start The default string.
    */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
 
    /**
    * Set the {@code YamlConfiguration} to use.
    * @param config The config to set.
    */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }
 
    @Override
    public String toString() {
        if (this == PREFIX)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }
 
    /**
    * Get the default value of the path.
    * @return The default value of the path.
    */
    public String getDefault() {
        return this.def;
    }
 
    /**
    * Get the path to the string.
    * @return The path to the string.
    */
    public String getPath() {
        return this.path;
    }
}