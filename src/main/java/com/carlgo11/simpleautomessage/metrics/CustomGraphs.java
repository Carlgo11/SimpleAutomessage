package com.carlgo11.simpleautomessage.Metrics;

import com.carlgo11.simpleautomessage.Main;

public class CustomGraphs {

    public static void graphs(Metrics Metrics, Main Main)
    {
        try {
            graph1(Metrics, Main);
            graph2(Metrics, Main);
            graph3(Metrics, Main);
            graph4(Metrics, Main);
            graph5(Metrics, Main);
            graph6(Metrics, Main);
            graph7(Metrics, Main);
            Main.debug("Sending Metrics data...");
            Metrics.start();
        } catch (Exception e) {
            Main.logger.warning(e.getMessage());
        }

    }

    void graph1(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph1 = Metrics.createGraph("Messages"); //Sends data about how many msg strings the server has.
        int o = 0;
        for (int i = 1; Main.getConfig().contains("msg" + i); i++) {
            o = i;
        }
        graph1.addPlotter(new SimplePlotter("" + o));
    }

    void graph2(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph2 = Metrics.createGraph("auto-update"); //Sends auto-update data. if auto-update: is true it returns 'enabled'.
        if (Main.getConfig().getBoolean("auto-update") == true) {
            graph2.addPlotter(new SimplePlotter("enabled"));
        } else {
            graph2.addPlotter(new SimplePlotter("disabled"));
        }

    }

    void graph3(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph3 = Metrics.createGraph("language");
        if (Main.getConfig().getString("language").equalsIgnoreCase("EN") || Main.getConfig().getString("language").isEmpty()) {
            graph3.addPlotter(new SimplePlotter("English"));
        } else if (Main.getConfig().getString("language").equalsIgnoreCase("FR")) {
            graph3.addPlotter(new SimplePlotter("French"));
        } else if (Main.getConfig().getString("language").equalsIgnoreCase("NL")) {
            graph3.addPlotter(new SimplePlotter("Dutch"));
        } else if (Main.getConfig().getString("language").equalsIgnoreCase("SE")) {
            graph3.addPlotter(new SimplePlotter("Swedish"));
        } else if (Main.getConfig().getString("language").equalsIgnoreCase("RU")) {
            graph3.addPlotter(new SimplePlotter("Russian"));
        } else {
            graph3.addPlotter(new SimplePlotter("Other"));
        }
    }

    void graph4(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph4 = Metrics.createGraph("min-players");
        graph4.addPlotter(new SimplePlotter("" + Main.getConfig().getInt("min-players")));
    }

    void graph5(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph5 = Metrics.createGraph("random");
        if (Main.getConfig().getBoolean("random")) {
            graph5.addPlotter(new SimplePlotter("enabled"));
        } else {
            graph5.addPlotter(new SimplePlotter("disabled"));
        }
    }

    void graph6(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph6 = Metrics.createGraph("warn-update0");
        if (!Main.getConfig().getBoolean("auto-update")) {
            if (Main.getConfig().getString("warn-update").equalsIgnoreCase("op")) {
                graph6.addPlotter(new SimplePlotter("op"));
            } else if (Main.getConfig().getString("warn-update").equalsIgnoreCase("perm")) {
                graph6.addPlotter(new SimplePlotter("perm"));
            } else {
                graph6.addPlotter(new SimplePlotter("none"));
            }
        }
    }

    void graph7(Metrics Metrics, Main Main) throws Exception
    {
        Metrics.Graph graph7 = Metrics.createGraph("update-config");
        if (Main.getConfig().getBoolean("update-config")) {
            graph7.addPlotter(new SimplePlotter("enabled"));
        } else {
            graph7.addPlotter(new SimplePlotter("disabled"));
        }
    }
}
