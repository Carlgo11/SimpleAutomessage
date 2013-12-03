package com.carlgo11.simpleautomessage;

import org.bukkit.event.Listener;

public class Time implements Listener {

    Main plugin;

    public Time(Main plug)
    {
        super();
        this.plugin = plug;
        Time();
    }

    public final void Time()
    {
        if (!plugin.getConfig().contains("time")) {
            plugin.onDebug("No time string found!");
        }
        plugin.time = plugin.getConfig().getInt("time");
        if (plugin.getConfig().contains("Time-setup")) {
            plugin.onDebug("Time-setup: string found!");
            if (plugin.getConfig().getString("Time-setup").equalsIgnoreCase("sec")) {
                plugin.time *= 20;
            }
        } else {
            plugin.time *= 20;
        }
        plugin.onDebug("time: " + plugin.time);
    }
    /* What is this?
     * 
     * Time() checks the time int and sets transfers the delay into seconds. Then returns the value to the Main.time int.
     */
}
