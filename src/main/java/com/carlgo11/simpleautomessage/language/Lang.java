package com.carlgo11.simpleautomessage.language;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {

    PREFIX("prefix", "§f§r[SimpleAutoMessage]"),
    ENABLED("plugin-enabled", "is enabled!"),
    DISABLED("plugin-disabled", "is disabled!"),
    CALLUPDATE("call-update", "Calling Updater.java"),
    AUTOUPDATE_FALSE("auto-update-false", "auto-update: is set to false!"),
    STATS_ERROR("mcstats-error", "Error Submitting stats!"),
    BAD_PERMS("bad-perms", "Error: You don't have permission to use that command!"),
    PL_RELOADED("plugin-reloaded", "SimpleAutoMessage reloaded!"),
    ERROR("error", "Error acurred! SimpleAutoMessage disabeled!"),
    UPDATING("updating", "Updating %prefix%..."),
    UPDATED("updated", "Done. Restart the server to load the new patch."),
    Simplemsg_Main("simplemsg-main", " Shows the commands"),
    Simplemsg_Reload("simplemsg-reload", " Reloads the config.yml"),
    Simplemsg_Update("simplemsg-update", " Will force an update"),
    Simplemsg_List("simplemsg-list", " Lists all the enabled messages"),
    Simplemsg_Report("simplemsg-report", " Upload a report to pastebin"),
    Simplemsg_Support("simplemsg-support", " Get help from a developer");
    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     *
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start)
    {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     *
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config)
    {
        LANG = config;
    }

    @Override
    public String toString()
    {
        if (this == PREFIX) {
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        }
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     *
     * @return The default value of the path.
     */
    public String getDefault()
    {
        return this.def;
    }

    /**
     * Get the path to the string.
     *
     * @return The path to the string.
     */
    public String getPath()
    {
        return this.path;
    }
}
