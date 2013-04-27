package com.carlgo11.automessage.main;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
	public class Main extends JavaPlugin{
		static int random = 0;
	public int tick = 1;
	     String color = null;
		 ChatColor realcolor = null;
		 ChatColor prefixcolor = null;
		 int limit = 0;
		 int x = 1;
		 int z = 1;
		 int n = 1;
		 int intime = 1;
		 int time = 0;
		 long ltime = 0;
		 long testtime = 40;
		public final static Logger  logger = Logger.getLogger("Minercraft");
		public void onEnable(){
			File config = new File(this.getDataFolder(), "config.yml");
			if(!config.exists()){
				this.saveDefaultConfig();
				System.out.println("[AutoMessage] No config.yml detected, config.yml created");
			}
			getLogger().info(getDescription().getName() + getDescription().getVersion() + " Is Enabled!");
	    Prefixcolors();
		colors();
		Broadcast();
		Time();
		
		}
	
		public void onDisable(){
			getLogger().info(getDescription().getName() + getDescription().getVersion() + " Is Disabled!");
		}
		
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
			String prefix = prefixcolor + "[" + getConfig().getString("Prefix") + "]  ";
			String badperm = prefix + ChatColor.RED + "Error: You don't have permission to use that command!";
			if(cmd.getName().equalsIgnoreCase("simpleautomessage")){
				
					
				if(args.length == 0){
					if(sender.hasPermission("SimpleAutoMessage.cmd.main") || sender.hasPermission("SimpleAutoMessage.cmd.*")){	
					sender.sendMessage(ChatColor.GREEN + "======== " + ChatColor.YELLOW + getConfig().getString("Prefix") + ChatColor.GREEN + " ======== ");
					sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "SimpleAutoMessage" + ChatColor.YELLOW + " Shows the commands");
					sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + "SimpleAutoMessage Reload" + ChatColor.YELLOW + " Reloads the config.yml");
					} else {
						sender.sendMessage(badperm);
					}
				} else 
				if(args.length == 1){
				if(args[0].equalsIgnoreCase("reload")){	
				if(sender.hasPermission("SimpleAutoMessage.cmd.reload")){
				this.reloadConfig();
				Prefixcolors();
				colors();
				saveConfig();
				Time();
				Broadcast();
				
				sender.sendMessage(prefix + ChatColor.GREEN + "Automessage reloaded!");
				} else {
					sender.sendMessage(badperm);
					}
				} else {
					sender.sendMessage(prefix + ChatColor.RED + "Error: Unknown command!");
				}
				} else 
					if(args.length > 1){
						sender.sendMessage(prefix + ChatColor.RED + "Error: Unknown command!");
					}
				
			}
				return true;
		}
		
		@EventHandler
		public void Time(){
			time = getConfig().getInt("time");
			//add X 20 feature
			if(getConfig().getString("debug").equalsIgnoreCase("true")){
				System.out.println("time: " + time);
			}
		}
		
		@EventHandler
		public void Prefixcolors(){
			
			color = getConfig().getString("Prefix-Color");
			if(color.equalsIgnoreCase("0") || color.equalsIgnoreCase("black")){
				prefixcolor = ChatColor.BLACK;
				if(getConfig().getString("debug").equalsIgnoreCase("true")){
				Main.logger.info("converted color > prefixcolor");
				}
			} 
				if(color.equalsIgnoreCase("1") || color.equalsIgnoreCase("blue")){
					prefixcolor = ChatColor.DARK_BLUE;
					if(getConfig().getString("debug").equalsIgnoreCase("true")){
					Main.logger.info("converted color > prefixcolor");
					}
				}
					if(color.equalsIgnoreCase("2") || color.equalsIgnoreCase("green")){
						prefixcolor = ChatColor.DARK_GREEN;
						if(getConfig().getString("debug").equalsIgnoreCase("true")){
						Main.logger.info("converted color > prefixcolor");
						}
					}
						if(color.equalsIgnoreCase("3") || color.equalsIgnoreCase("Cyan")){
							prefixcolor = ChatColor.DARK_AQUA;
							if(getConfig().getString("debug").equalsIgnoreCase("true")){
							Main.logger.info("converted color > prefixcolor");
						}
						} 
			if(color.equalsIgnoreCase("4") || color.equalsIgnoreCase("Red")){
				prefixcolor = ChatColor.DARK_RED;
				if(getConfig().getString("debug").equalsIgnoreCase("true")){
				Main.logger.info("converted color > prefixcolor");
			} 
			}
				if(color.equalsIgnoreCase("5") || color.equalsIgnoreCase("Purple")){
					prefixcolor = ChatColor.DARK_PURPLE;
					if(getConfig().getString("debug").equalsIgnoreCase("true")){
					Main.logger.info("converted color > prefixcolor");
				}
				}
					if(color.equalsIgnoreCase("6") || color.equalsIgnoreCase("Yellow")){
						prefixcolor = ChatColor.YELLOW;
						if(getConfig().getString("debug").equalsIgnoreCase("true")){
						Main.logger.info("converted color > prefixcolor");
						}
					} 
						if(color.equalsIgnoreCase("7") || color.equalsIgnoreCase("Gray")){
							prefixcolor = ChatColor.DARK_GRAY;
							if(getConfig().getString("debug").equalsIgnoreCase("true")){
							Main.logger.info("converted color > prefixcolor");
							}
						} 
							if(color.equalsIgnoreCase("8") || color.equalsIgnoreCase("Light_Gray")){
								prefixcolor = ChatColor.GRAY;
								if(getConfig().getString("debug").equalsIgnoreCase("true")){
								Main.logger.info("converted color > prefixcolor");
								}
							} 
								if(color.equalsIgnoreCase("9") || color.equalsIgnoreCase("Light_Blue")){
									prefixcolor = ChatColor.BLUE;
									if(getConfig().getString("debug").equalsIgnoreCase("true")){
									Main.logger.info("converted color > prefixcolor");
									}
								} 
									if(color.equalsIgnoreCase("A") || color.equalsIgnoreCase("Light_GREEN")){
										prefixcolor = ChatColor.GREEN;
										if(getConfig().getString("debug").equalsIgnoreCase("true")){
										Main.logger.info("converted color > prefixcolor");
										}
									}
										if(color.equalsIgnoreCase("B") || color.equalsIgnoreCase("Light_Cyan")){
											prefixcolor = ChatColor.AQUA;
											if(getConfig().getString("debug").equalsIgnoreCase("true")){
											Main.logger.info("converted color > prefixcolor");
											}
										} 
											if(color.equalsIgnoreCase("c") || color.equalsIgnoreCase("Light_Red")){
												prefixcolor = ChatColor.RED;
												if(getConfig().getString("debug").equalsIgnoreCase("true")){
												Main.logger.info("converted color > prefixcolor");
												}
											} 
												if(color.equalsIgnoreCase("d") || color.equalsIgnoreCase("Light_Purple")){
													prefixcolor = ChatColor.LIGHT_PURPLE;
													if(getConfig().getString("debug").equalsIgnoreCase("true")){
													Main.logger.info("converted color > prefixcolor");
													}
												} 
													if(color.equalsIgnoreCase("e") || color.equalsIgnoreCase("Light_Yellow")){
														prefixcolor = ChatColor.GOLD;
														if(getConfig().getString("debug").equalsIgnoreCase("true")){
														Main.logger.info("converted color > prefixcolor");
														}
													}
													if(color.equalsIgnoreCase("f") || color.equalsIgnoreCase("White")){
														prefixcolor = ChatColor.WHITE;
														if(getConfig().getString("debug").equalsIgnoreCase("true")){
														Main.logger.info("converted color > prefixcolor");
														}
													}
		}
		@EventHandler
		public void colors(){
			color = getConfig().getString("Color");
			if(color.equalsIgnoreCase("0") || color.equalsIgnoreCase("black")){
				realcolor = ChatColor.BLACK;
				if(getConfig().getString("debug").equalsIgnoreCase("true")){
				Main.logger.info("converted color > realcolor");
				}
			} 
				if(color.equalsIgnoreCase("1") || color.equalsIgnoreCase("blue")){
					realcolor = ChatColor.DARK_BLUE;
					if(getConfig().getString("debug").equalsIgnoreCase("true")){
					Main.logger.info("converted color > realcolor");
					}
				}
					if(color.equalsIgnoreCase("2") || color.equalsIgnoreCase("green")){
						realcolor = ChatColor.DARK_GREEN;
						if(getConfig().getString("debug").equalsIgnoreCase("true")){
						Main.logger.info("converted color > realcolor");
					}
					}
						if(color.equalsIgnoreCase("3") || color.equalsIgnoreCase("Cyan")){
							realcolor = ChatColor.DARK_AQUA;
							if(getConfig().getString("debug").equalsIgnoreCase("true")){
							Main.logger.info("converted color > realcolor");
						} 
						}
			if(color.equalsIgnoreCase("4") || color.equalsIgnoreCase("Red")){
				realcolor = ChatColor.DARK_RED;
				if(getConfig().getString("debug").equalsIgnoreCase("true")){
				Main.logger.info("converted color > realcolor");
			} 
			}
				if(color.equalsIgnoreCase("5") || color.equalsIgnoreCase("Purple")){
					realcolor = ChatColor.DARK_PURPLE;
					if(getConfig().getString("debug").equalsIgnoreCase("true")){
					Main.logger.info("converted color > realcolor");
				} 
				}
					if(color.equalsIgnoreCase("6") || color.equalsIgnoreCase("Yellow")){
						realcolor = ChatColor.YELLOW;
						if(getConfig().getString("debug").equalsIgnoreCase("true")){
						Main.logger.info("converted color > realcolor");
					} 
					}
						if(color.equalsIgnoreCase("7") || color.equalsIgnoreCase("Gray")){
							realcolor = ChatColor.DARK_GRAY;
							if(getConfig().getString("debug").equalsIgnoreCase("true")){
							Main.logger.info("converted color > realcolor");
						} 
						}
							if(color.equalsIgnoreCase("8") || color.equalsIgnoreCase("Light_Gray")){
								realcolor = ChatColor.GRAY;
								if(getConfig().getString("debug").equalsIgnoreCase("true")){
								Main.logger.info("converted color > realcolor");
							} 
							}
								if(color.equalsIgnoreCase("9") || color.equalsIgnoreCase("Light_Blue")){
									realcolor = ChatColor.BLUE;
									if(getConfig().getString("debug").equalsIgnoreCase("true")){
									Main.logger.info("converted color > realcolor");
								} 
								}
									if(color.equalsIgnoreCase("A") || color.equalsIgnoreCase("Light_GREEN")){
										realcolor = ChatColor.GREEN;
										if(getConfig().getString("debug").equalsIgnoreCase("true")){
										Main.logger.info("converted color > realcolor");
									}
									}
										if(color.equalsIgnoreCase("B") || color.equalsIgnoreCase("Light_Cyan")){
											realcolor = ChatColor.AQUA;
											if(getConfig().getString("debug").equalsIgnoreCase("true")){
											Main.logger.info("converted color > realcolor");
										} 
										}
											if(color.equalsIgnoreCase("c") || color.equalsIgnoreCase("Light_Red")){
												realcolor = ChatColor.RED;
												if(getConfig().getString("debug").equalsIgnoreCase("true")){
												Main.logger.info("converted color > realcolor");
											} 
											}
												if(color.equalsIgnoreCase("d") || color.equalsIgnoreCase("Light_Purple")){
													realcolor = ChatColor.LIGHT_PURPLE;
													if(getConfig().getString("debug").equalsIgnoreCase("true")){
													Main.logger.info("converted color > realcolor");
												} 
												}
													if(color.equalsIgnoreCase("e") || color.equalsIgnoreCase("Light_Yellow")){
														realcolor = ChatColor.GOLD;
														if(getConfig().getString("debug").equalsIgnoreCase("true")){
														Main.logger.info("converted color > realcolor");
													}
													}
													if(color.equalsIgnoreCase("f") || color.equalsIgnoreCase("White")){
														realcolor = ChatColor.WHITE;
														if(getConfig().getString("debug").equalsIgnoreCase("true")){
														Main.logger.info("converted color > realcolor");
														}
													}
		}
		
		@EventHandler
		public void Broadcast(){
			if(realcolor == null){
				System.out.print("[AutoMessage] Error: Couldn't load the color fron config.yml!");
				onDisable();
			}
			if(prefixcolor == null){
				System.out.print("[AutoMessage] Error: Couldn't load the prefix-color fron config.yml!");
				onDisable();
			}
			final long d = getConfig().getLong("time");	
			Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable(){
				public void run(){
						if(d == getConfig().getLong("time")){
					if(getConfig().getString("debug").equalsIgnoreCase("true")){
					System.out.println("tick: " + tick);
					System.out.println("realcolor: " + realcolor);
					System.out.println("time: " + time);
					}
						if(getConfig().contains("msg" + tick)){
					Bukkit.broadcastMessage(prefixcolor + "[" + getConfig().getString("Prefix") + "]  " + ChatColor.RESET + realcolor + getConfig().getString("msg" + tick));
					tick++;
						} else {
							Bukkit.broadcastMessage(prefixcolor + "[" + getConfig().getString("Prefix") + "]  " + ChatColor.RESET + realcolor + getConfig().getString("msg1"));
							tick=2;
						}
		
					}
				}
			}, 60L, d);
			
			
			
		}
		
		
	
		
		
		public void onError(){
			Main.logger.warning("[AutoMessage] Error acurred! Plugin Disabeled!");
			Bukkit.getPluginManager().disablePlugin(this);
			
		}
		
	
	}
		
	


