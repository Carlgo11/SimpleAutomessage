//package com.carlgo11.simpleautomessage;
//
//import com.carlgo11.simpleautomessage.language.Lang;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.event.Listener;
//
//public class Broadcast implements Listener {
//
//    Main plugin;
//
//    public Broadcast(Main plug)
//    {
//        super();
//        this.plugin = plug;
//        broadcast();
//    }
//    int errormaxmsgs = 5;
//    int errormsgs = 0;
//    int ra = 0;
//    int rb = 0;
//
//    public void broadcast()
//    {
//        int a = 0;
//        int b = 1;
//        for (int i = 1; a != b; i++) {
//            if (plugin.getConfig().contains("msg" + i)) {
//            } else {
//                a++;
//                ra = i;
//            }
//        }
//        ra--;
//
//        final long d = (long) (plugin.time);
//        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
//            public void run()
//            {
//                int minP = plugin.getConfig().getInt("min-players");
//                int onlineP = Bukkit.getServer().getOnlinePlayers().length;
//                int realonlineP = onlineP;
//                onlineP++;
//
//                if (onlineP > minP) {
//                    plugin.debug("time: " + d);
//                    if (plugin.getConfig().getBoolean("random")) {
//                        plugin.debug("ra: " + ra);
//                        plugin.debug("a: " + RandomishInt.a);
//                    } else {
//                        plugin.debug("tick: " + plugin.tick);
//                    }
//                    String senderToSend = plugin.getConfig().getString("sender");
//                    String senderToMC = ChatColor.translateAlternateColorCodes('&', senderToSend);
//                    String prefixToSend = plugin.getConfig().getString("prefix");
//                    String prefixToMC = ChatColor.translateAlternateColorCodes('&', prefixToSend);
//                    String suffixToSend = plugin.getConfig().getString("suffix");
//                    String suffixToMC = ChatColor.translateAlternateColorCodes('&', suffixToSend);
//                    if (plugin.getConfig().getBoolean("random")) {
//                        RandomishInt.onInt(ra);
//                        while (RandomishInt.use == false) {
//
//                        }
//                        if (plugin.getConfig().contains("msg" + RandomishInt.a)) {
//                            String messageToSend = plugin.getConfig().getString("msg" + RandomishInt.a);
//                            String msgToMC = ChatColor.translateAlternateColorCodes('&', messageToSend);
//                            plugin.getServer().broadcast(prefixToMC + senderToMC + suffixToMC + " " + ChatColor.RESET + msgToMC, "SimpleAutoMessage.seemsg");
//                        } else {
//                            if (errormsgs != errormaxmsgs) {
//                                System.out.println(ChatColor.stripColor(prefixToMC) + ChatColor.stripColor(senderToMC) + ChatColor.stripColor(suffixToMC) + " " + Lang.NO_MSG1);
//                                errormsgs++;
//                            }
//                        }
//                        rb = RandomishInt.a;
//                    } else {
//                        if (plugin.getConfig().contains("msg" + plugin.tick)) {
//                            String messageToSend = plugin.getConfig().getString("msg" + plugin.tick);
//                            String msgToMC = ChatColor.translateAlternateColorCodes('&', messageToSend);
//                            plugin.getServer().broadcast(prefixToMC + senderToMC + suffixToMC + " " + ChatColor.RESET + msgToMC, "SimpleAutoMessage.seemsg");
//                            plugin.tick++;
//                        } else {
//                            plugin.debug("no msg" + plugin.tick + " set in the config. Calling msg1 instead.");
//                            if (plugin.getConfig().contains("msg1")) {
//                                String messageToSend = plugin.getConfig().getString("msg1");
//                                String msgToMC = ChatColor.translateAlternateColorCodes('&', messageToSend);
//                                plugin.getServer().broadcast(prefixToMC + senderToMC + suffixToMC + " " + ChatColor.RESET + msgToMC, "SimpleAutoMessage.seemsg");
//                                plugin.tick = 2;
//                            } else {
//                                if (errormsgs != errormaxmsgs) {
//                                    System.out.println(ChatColor.stripColor(prefixToMC) + ChatColor.stripColor(senderToMC) + ChatColor.stripColor(suffixToMC) + " " + Lang.NO_MSG1);
//                                    errormsgs++;
//                                }
//                            }
//                        }
//                    }
//
//                } else {
//                    plugin.debug("Error: minP:" + minP + " realOnlineP: " + realonlineP);
//                }
//            }
//        }, d, d);
//    }
//}