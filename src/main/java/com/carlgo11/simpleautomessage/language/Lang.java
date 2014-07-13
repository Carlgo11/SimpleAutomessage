package com.carlgo11.simpleautomessage.language;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {

    msg("", "");

    private String path;
    private String def;
    private static YamlConfiguration LANG;
    private static YamlConfiguration EN;

    Lang(String path, String start)
    {
        this.path = path;
        this.def = start;
    }

    public static void setLang(YamlConfiguration config)
    {
        LANG = config;
    }

    public static void setEN(YamlConfiguration config)
    {
        EN = config;
    }

    @Override
    public String toString()
    {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    public static String get(String string)
    {
        if (LANG.contains(string)) {
            return ChatColor.translateAlternateColorCodes('&', LANG.get(string).toString());
        } else if (EN.contains(string)) {
            return ChatColor.translateAlternateColorCodes('&', EN.get(string).toString());
        }
        return "";
    }

    public String getDefault()
    {
        return this.def;
    }

    public String getPath()
    {
        return this.path;
    }
}
