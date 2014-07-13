package com.carlgo11.simpleautomessage.language;

import com.carlgo11.simpleautomessage.Main;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.file.YamlConfiguration;

public class loadLang {


    public static void loadLang(String lan, Main plugin)
    {
        
        String l;
        if(lan.equals("backup")){
            l="EN";
        }else{
            l=lan;
        }
        File dir = new File(plugin.getDataFolder() + "/language");
        dir.mkdir();
        if (!plugin.getConfig().getString("language").isEmpty()) {
            File lang = new File(plugin.getDataFolder() + "/language", l + "_lang.yml");
            if (!lang.exists()) {
                try {
                    plugin.getDataFolder().mkdir();
                    lang.createNewFile();
                    InputStream defConfigStream = plugin.getResource(l + "_lang.yml");
                    if (defConfigStream != null) {
                        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                        defConfig.save(lang);
                        if(!lan.equals("backup")){
                        Lang.setLang(defConfig);
                        }else{
                           Lang.setEN(defConfig);
                        }
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "Couldn't create language file.");
                    plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "This is a fatal error. Now disabling");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                }
            }

            YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
            for (Lang item : Lang.values()) {
                if (conf.getString(item.getPath()) == null) {
                    conf.set(item.getPath(), item.getDefault());
                }
            }
            if(!lan.equals("backup")){
                        Lang.setLang(conf);
                        }else{
                           Lang.setEN(conf);
                        }
            Main.LANG = conf;
            Main.LANG_FILE = lang;
            try {
                conf.save(plugin.getLangFile());
            } catch (IOException e) {
                plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "Failed to save lang.yml.");
                plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "Report this stack trace to an developer.");
                e.printStackTrace();
            }
        } else {
            File lang = new File(plugin.getDataFolder() + "/language", "EN_lang.yml");
            if (!lang.exists()) {
                try {
                    plugin.getDataFolder().mkdir();
                    lang.createNewFile();
                    InputStream defConfigStream = plugin.getResource("EN_lang.yml");
                    if (defConfigStream != null) {
                        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                        defConfig.save(lang);
                        if(!lan.equals("backup")){
                        Lang.setLang(defConfig);
                        }else{
                           Lang.setEN(defConfig);
                        }
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "Couldn't create language file.");
                    plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "This is a fatal error. Now disabling");
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                }
            }

            YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
            for (Lang item : Lang.values()) {
                if (conf.getString(item.getPath()) == null) {
                    conf.set(item.getPath(), item.getDefault());
                }
            }
            if(!lan.equals("backup")){
                        Lang.setLang(conf);
                        }else{
                           Lang.setEN(conf);
                        }
            Main.LANG = conf;
            Main.LANG_FILE = lang;
            try {
                conf.save(plugin.getLangFile());
            } catch (IOException e) {
                plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "Failed to save lang.yml.");
                plugin.getLogger().warning("[" + plugin.getDescription().getName() + "] " + "Report this stack trace to an developer.");
                e.printStackTrace();
            }
        }
    }
}
