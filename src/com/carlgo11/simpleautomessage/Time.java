package com.carlgo11.simpleautomessage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Time implements Listener {

    Main plugin;

    public Time(Main plug) {
        super();
        this.plugin = plug;
        Time();
    }

    public final void Time() {
        if (!plugin.getConfig().contains("time")) {
            plugin.debugmsg = "No time string found!";
            plugin.onDebug();
        }
        plugin.time = plugin.getConfig().getInt("time");
        if (plugin.getConfig().contains("Time-setup")) {
            plugin.debugmsg = "Time-setup: string found!";
            plugin.onDebug();
            if (plugin.getConfig().getString("Time-setup").equalsIgnoreCase("sec")) {
                plugin.time *= 20;
            }
        } else {
            plugin.time *= 20;
        }
        plugin.debugmsg = "time: " + plugin.time;
        plugin.onDebug();
    }
    /* What is this?
     * 
     * Time() checks the time int and sets transfers the delay into seconds. Then returns the value to the Main.time int.
     */
}
