package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Announce {

    static private Main Main;
    static private int messageCounter;
    static private int lastMessage = 0;
    static public boolean isRandom;

    public void setup(Main m) {
        this.Main = m;
        this.isRandom = Main.getConfig().getBoolean("random");
        int cm = collectMessages();
        if (cm == 0) {
            m.getLogger().severe("Could not load any messages from the config. Did you forget to add any or is the config broken?");
        }
        this.lastMessage = collectMessages();
        schedule();

    }

    private void schedule() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {

            public void run() {
                if (isRandom) {
                    onRandom();
                } else {
                    onInOrder();
                }
            }
        }, Main.getConfig().getLong("time"), Main.getConfig().getLong("time"));
    }

    private void onRandom() {
        int nm = getNextMessage();
        String message = Main.getConfig().getString("msg" + nm);
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
            if (!Main.getConfig().contains("msg" + currentMessage++)) {
                return currentMessage;
            }
        }
    }

    public int getNextMessage() {
        if (isRandom) {
            return Main.getRandomInt(lastMessage);
        } else {
            int nm = messageCounter++;
            if (nm <= lastMessage) {
                return nm;
            } else {
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
