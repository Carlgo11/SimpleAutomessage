package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.language.Lang;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Announce {

    private Main Main;
    private int messageCounter = 0;
    private int lastMessage;
    private boolean isRandom = false;
    private int warningCounter = 0;
    private int lastRandom;

    public void setup(Main m)
    {
        this.Main = m;

        int cm = Main.messages.size();
        if (Main.getConfig().getBoolean("random") && cm > 2) {
            this.isRandom = true;
        }
        this.lastMessage = cm;
        schedule(cm);

    }

    private void schedule(final int cm)
    {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main, new Runnable() {

            public void run()
            {
                if (warningCounter <= 4) {
                    if (Main.onlinePlayers()) {
                        if (cm == 1) {
                            Main.getLogger().log(Level.SEVERE, Lang.get("no-messages"), new Object[]{Main.getConfig().getString("message-file")});
                            warningCounter++;
                            if (warningCounter == 5) {
                                Main.getLogger().log(Level.SEVERE, Lang.get("stop-errors"), new Object[]{Main.getConfig().getString("message-file")});
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

    private void onRandom()
    {
        int nm = getNextMessage();
        String message = Main.messages.get(nm);
        lastRandom = nm;
        sendMessage(message);
    }

    private void onInOrder()
    {
        int nm = getNextMessage();
        String message = Main.messages.get(nm);
        sendMessage(message);
    }

    public int getNextMessage()
    {
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

    private void sendMessage(String message)
    {
        String prefix = ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("prefix"));
        String sender = ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("sender"));
        String suffix = ChatColor.translateAlternateColorCodes('&', Main.getConfig().getString("suffix"));
        String msg = ChatColor.translateAlternateColorCodes('&', message);
        String smsg = msg;
        System.out.println("msg: " + msg);
        if (msg.contains(":n")) {
            smsg = msg.replaceAll(":n", System.getProperty("line.separator"));
            System.out.println("new line found. " + smsg);
        }
        Bukkit.broadcast(prefix + ChatColor.RESET + sender + " " + ChatColor.RESET + suffix + " " + ChatColor.RESET + smsg, "simpleautomessage.seemsg");
    }
}
