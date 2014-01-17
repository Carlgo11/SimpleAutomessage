package com.carlgo11.simpleautomessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Report {

    /*
     * File created by Carlgo11. And uploaded to https://github.com/carlgo11/report
     * Please see LICENSE on https://github.com/carlgo11/report for the terms and conditions for this file.
     */
    
    public static String Main(Main plugin)
    {
        String topic = "Report for " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " created. The following info is gathered from the config.yml & latest.log.\n\n";
        return topic + "CONFIG: \n{\n" + config(plugin).toString() + "}\n\nLatest Log:\n{\n" + latestlog(plugin).toString() + "}";
    }

    static StringBuilder config(Main plugin)
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

    static StringBuilder latestlog(Main plugin)
    {

        BufferedReader br = null;
        StringBuilder txt = new StringBuilder();
        if (plugin.getConfig().getBoolean("send-log")) {
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
        return txt;
    }
}
