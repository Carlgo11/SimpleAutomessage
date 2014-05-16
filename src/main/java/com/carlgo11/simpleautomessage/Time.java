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
            plugin.debug("No time string found!");
        }
        plugin.time = plugin.getConfig().getInt("time");
        if (plugin.getConfig().contains("time-setup")) {
            plugin.debug("time-setup: string found!");
            if (plugin.getConfig().getString("time-setup").equalsIgnoreCase("sec")) {
                plugin.time *= 20;
            }
        } else {
            plugin.time *= 20;
        }
        plugin.debug("time: " + plugin.time);
    }
    /* What is this?
     * 
     * Time() checks the time int and sets transfers the delay into seconds. Then returns the value to the Main.time int.
     */
}
