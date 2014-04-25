package com.carlgo11.simpleautomessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Announce {

    private Main Main;
    private int messageCounter = 0;
    private int lastMessage;
    private boolean isRandom;
    private int warningCounter = 0;

    public void setup(Main m) {
        this.Main = m;
        this.isRandom = Main.getConfig().getBoolean("random");
        int cm = collectMessages();
        this.lastMessage = cm;
        schedule(cm);

    }

    private void schedule(final int cm) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {

            public void run() {

                if (cm == 0) {
                    Main.getLogger().severe("Could not load any messages from the config. Did you forget to add any or is the config broken?");
                    warningCounter++;
                    if (warningCounter >= 5) {
                        return;
                    }
                } else {
                    if (isRandom) {
                        onRandom();
                    } else {
                        onInOrder();
                    }
                }
            }
        }, Main.time, Main.time);
    }

    private void onRandom() {
        int nm = getNextMessage();
        String message = Main.getConfig().getString("msg" + nm);
        sendMessage(message);
    }

    private void onInOrder() {
        int nm = getNextMessage();
        String message = Main.getConfig().getString("msg" + nm);
        System.out.println(nm);
        sendMessage(message);

    }

    private int collectMessages() {
        Main.debug("Running message collection query...");
        int currentMessage = 0;
        while (true) {
            currentMessage++;
            System.out.println("checkMessage "+currentMessage);
            if (!Main.getConfig().contains("msg" + currentMessage)) {
                return currentMessage;
            }
        }
    }

    public int getNextMessage() {
        if (isRandom) {
            return Main.getRandomInt(lastMessage);
        } else {
            int nm = messageCounter+1;
            System.out.println("nm "+nm+"\tlastMessage "+lastMessage);
            if (nm < lastMessage) {
                messageCounter++;
                return nm;
            } else {
                messageCounter=1;
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
