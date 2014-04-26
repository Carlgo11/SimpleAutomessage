package com.carlgo11.simpleautomessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Announce {

    private Main Main;
    private int messageCounter = 0;
    private int lastMessage;
    private boolean isRandom = false;
    private int warningCounter = 0;
    private int lastRandom;

    public void setup(Main m) {
        this.Main = m;

        int cm = collectMessages();
        if (Main.getConfig().getBoolean("random") && cm > 2) {
            this.isRandom = true;
        }
        this.lastMessage = cm;
        schedule(cm);

    }

    private void schedule(final int cm) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {

            public void run() {
                if (warningCounter <= 4) {
                    

                    if (Main.onlinePlayers()) {
                        if (cm == 1) {
                            Main.getLogger().severe("Could not load any messages from the config. Did you forget to add any or is the config broken?");
                            warningCounter++;
                            if(warningCounter == 5){
                                Main.getLogger().severe("Will stop outputing warnings now. Please fix your config and reload the plugin.");
                            }

                        } else {
                            if (isRandom) {
                                Main.debug("isRandom: " + isRandom);
                                onRandom();
                            } else {
                                Main.debug("isRandom: " + isRandom);
                                onInOrder();
                            }
                        }
                    }
                }
            }
        }, Main.time, Main.time);
    }

    private void onRandom() {
        int nm = getNextMessage();
        String message = Main.getConfig().getString("msg" + nm);
        lastRandom = nm;
        sendMessage(message);
    }

    private void onInOrder() {
        int nm = getNextMessage();
        String message = Main.getConfig().getString("msg" + nm);
        sendMessage(message);
    }

    private int collectMessages() {
        Main.debug("Running message collection query...");
        int currentMessage = 0;
        while (true) {
            currentMessage++;
            if (!Main.getConfig().contains("msg" + currentMessage)) {
                Main.debug("currentMessage: " + currentMessage);
                return currentMessage++;
            }
        }
    }

    public int getNextMessage() {
        if (isRandom) {
            int r = Main.getRandomInt(lastMessage - 1);
            while (r == lastRandom) {
                r = Main.getRandomInt(lastMessage - 1);
            }

            return r;

        } else {
            int nm = messageCounter + 1;
            if (nm < lastMessage) {
                messageCounter++;
                return nm;
            } else {
                messageCounter = 1;
                return 1;
            }
        }
    }

    private void sendMessage(String message) {
        String prefix = ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("prefix"));
        String sender = ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("sender"));
        String suffix = ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("suffix"));
        String msg = ChatColor.translateAlternateColorCodes('&', message);

        Bukkit.broadcast(prefix + sender + suffix + " " + ChatColor.RESET + msg, "simpleautomessage.seemsg");
    }

}
