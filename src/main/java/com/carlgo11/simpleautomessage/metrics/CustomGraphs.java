package com.carlgo11.simpleautomessage.metrics;

import com.carlgo11.simpleautomessage.Main;

public class CustomGraphs {

    public static void graphs(Metrics metrics, Main Main) { // Custom Graphs. Sends data to mcstats.org
        try {

            //Graph1
            Metrics.Graph graph1 = metrics.createGraph("Messages"); //Sends data about how many msg strings the server has.
            int o = 0;
            for (int i = 1; Main.getConfig().contains("msg" + i); i++) {
                o = i;
            }
            graph1.addPlotter(new SimplePlotter("" + o));

            //graph2
            Metrics.Graph graph2 = metrics.createGraph("auto-update"); //Sends auto-update data. if auto-update: is true it returns 'enabled'.
            if (Main.getConfig().getBoolean("auto-update") == true) {
                graph2.addPlotter(new SimplePlotter("enabled"));
            } else {
                graph2.addPlotter(new SimplePlotter("disabled"));
            }

            //Graph3
            Metrics.Graph graph3 = metrics.createGraph("language");
            String lang = Main.getConfig().getString("language");
            if (lang.equalsIgnoreCase("EN") || lang.isEmpty()) {
                graph3.addPlotter(new SimplePlotter("English"));
            } else if (lang.equalsIgnoreCase("FR")) {
                graph3.addPlotter(new SimplePlotter("French"));
            } else if (lang.equalsIgnoreCase("NL")) {
                graph3.addPlotter(new SimplePlotter("Dutch"));
            } else if (lang.equalsIgnoreCase("SE")) {
                graph3.addPlotter(new SimplePlotter("Swedish"));
            } else if (lang.equalsIgnoreCase("RU")) {
                graph3.addPlotter(new SimplePlotter("Russian"));
            } else {
                graph3.addPlotter(new SimplePlotter("Other"));
            }

            //Graph4
            Metrics.Graph graph4 = metrics.createGraph("min-players");
            graph4.addPlotter(new SimplePlotter("" + Main.getConfig().getInt("min-players")));

            //Graph5
            Metrics.Graph graph5 = metrics.createGraph("random");
            if (Main.getConfig().getBoolean("random")) {
                graph5.addPlotter(new SimplePlotter("enabled"));
            } else {
                graph5.addPlotter(new SimplePlotter("disabled"));
            }

            //Graph6
            Metrics.Graph graph6 = metrics.createGraph("warn-update0");
            if (!Main.getConfig().getBoolean("auto-update")) {
                if (Main.getConfig().getString("warn-update").equalsIgnoreCase("op")) {
                    graph6.addPlotter(new SimplePlotter("op"));
                } else if (Main.getConfig().getString("warn-update").equalsIgnoreCase("perm")) {
                    graph6.addPlotter(new SimplePlotter("perm"));
                } else {
                    graph6.addPlotter(new SimplePlotter("none"));
                }
            }

            //Graph7
            Metrics.Graph graph7 = metrics.createGraph("update-config");
            if (Main.getConfig().getBoolean("update-config")) {
                graph7.addPlotter(new SimplePlotter("true"));
            } else {
                graph7.addPlotter(new SimplePlotter("false"));
            }

            Main.debug("Sending metrics data...");
            metrics.start();
        } catch (Exception e) {
            Main.getLogger().warning(e.getMessage());
        }
    }
}
