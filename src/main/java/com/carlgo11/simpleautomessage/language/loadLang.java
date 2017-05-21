package com.carlgo11.simpleautomessage.language;

import com.carlgo11.simpleautomessage.Main;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;

public class loadLang {

    public static void loadLang(String lan, Main plugin)
    {
        String l;
        if (lan.equals("backup") || lan.isEmpty()) {
            l = "EN";
        } else {
            l = lan;
        }
        File dir = new File(plugin.getDataFolder() + "/language");
        dir.mkdir();
        loadLang.getLangFile(l, lan, plugin);
    }

    public static void getLangFile(String language, String lan, Main plugin)
    {
        File lang = new File(plugin.getDataFolder() + "/language", language + "_lang.yml");
        if (!lang.exists()) {
            try {
                plugin.getDataFolder().mkdir();
                lang.createNewFile();
                Reader defConfigStream = new InputStreamReader(plugin.getResource(language + "_lang.yml"), "UTF8");
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                defConfig.save(lang);
                if (!lan.equals("backup")) {
                    Lang.setLang(defConfig);
                } else {
                    Lang.setEN(defConfig);
                }
                return;
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "[{0}] " + "Couldn''t create language file.", plugin.getDescription().getName());
                plugin.getLogger().log(Level.WARNING, "[{0}] " + "This is a fatal error. Now disabling", plugin.getDescription().getName());
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for (Lang item : Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        if (!lan.equals("backup")) {
            Lang.setLang(conf);
        } else {
            Lang.setEN(conf);
        }
        Main.LANG = conf;
        Main.LANG_FILE = lang;
        try {
            conf.save(plugin.getLangFile());
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "[{0}] " + "Failed to save lang.yml.", plugin.getDescription().getName());
            plugin.getLogger().log(Level.WARNING, "[{0}] " + "Report this stack trace to an developer.", plugin.getDescription().getName());
        }
    }
}
