package com.carlgo11.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Report {

    /*
     * File created by Carlgo11. And uploaded to https://github.com/carlgo11/report
     * Please see LICENSE on https://github.com/carlgo11/report for the terms and conditions for distribution of this code.
     */
    private Plugin plugin;

    public static String Main(Plugin plugin)
    {
        String topic = "Report for " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " created. The following info is gathered from the config.yml & latest.log.\n\n";
        return topic + summary(plugin).toString() + "CONFIG: \n{\n" + config(plugin).toString() + "}\n\n\nMESSAGE-FILE: \n{\n" + automessage(plugin).toString() + "}\n\n\nLatest Log:\n{\n" + latestlog(plugin).toString() + "}";
    }

    static StringBuilder summary(Plugin plugin)
    {
        StringBuilder txt = new StringBuilder();
        if (plugin.getConfig().contains("report-summary")) {
            if (plugin.getConfig().getBoolean("report-summary")) {
                txt.append("====   Summary   ====\n");
                txt.append("OS: " + System.getProperty("os.name") + "\n");
                txt.append("Java version: " + System.getProperty("java.version") + "\n");
                txt.append("Bukkit version: " + Bukkit.getServer().getBukkitVersion() + "\n");
                txt.append("Plugin version: " + plugin.getDescription().getVersion() + "\n");
                txt.append("Config version: " + plugin.getConfig().getString("version") + "\n");
                txt.append("Auto update: " + plugin.getConfig().getBoolean("auto-update") + "\n");
                txt.append("Warn update: " + plugin.getConfig().getString("warn-update") + "\n");
                txt.append("Online mode: " + Bukkit.getServer().getOnlineMode() + "\n");
                txt.append("Message file: " + plugin.getConfig().getString("message-file") + "\n");
                txt.append("\n\n");
            } else {
                txt.append("Cannot create a summary (Access denied). Contact the Server Owner.\n\n");
            }
        } else {
            txt.append("The developer(s) of this plugin have forgotten to set a report-log boolean in the config. Please report this error.\n");
        }

        return txt;
    }

    static StringBuilder config(Plugin plugin)
    {
        BufferedReader br = null;
        StringBuilder txt = new StringBuilder();
        try {
            String line;
            br = new BufferedReader(new FileReader("" + plugin.getDataFolder() + File.separatorChar + "config.yml"));
            while ((line = br.readLine()) != null) {
                txt.append(line);
                txt.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return txt;
    }

    static StringBuilder automessage(Plugin plugin)
    {
        BufferedReader br = null;
        StringBuilder txt = new StringBuilder();
        try {
            String line;
            br = new BufferedReader(new FileReader("" + plugin.getDataFolder() + File.separatorChar + plugin.getConfig().getString("message-file")));
            while ((line = br.readLine()) != null) {
                txt.append(line);
                txt.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return txt;
    }

    static StringBuilder latestlog(Plugin plugin)
    {

        BufferedReader br = null;
        StringBuilder txt = new StringBuilder();
        if (plugin.getConfig().contains("report-log")) {
            if (plugin.getConfig().getBoolean("report-log")) {
                try {
                    String line;
                    br = new BufferedReader(new FileReader("logs" + File.separatorChar + "latest.log"));
                    while ((line = br.readLine()) != null) {
                        txt.append(line);
                        txt.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                txt.append("Access denied for latest.log. Contact the Server Owner.\n");
            }
        } else {
            txt.append("The developer(s) of this plugin have forgotten to set a report-log boolean in the config. Please report this error.\n");
        }
        return txt;
    }
}
