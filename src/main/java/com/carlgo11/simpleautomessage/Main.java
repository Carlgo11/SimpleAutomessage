package com.carlgo11.simpleautomessage;

import com.carlgo11.simpleautomessage.commands.*;
import com.carlgo11.simpleautomessage.language.*;
import com.carlgo11.simpleautomessage.player.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public int tick = 1; // msg+<tick> int
    public int time = 0; // the delay
    public static YamlConfiguration LANG;
    public static File LANG_FILE;
    public boolean update = false;
    public boolean debugm;
    public ArrayList<String> messages = new ArrayList<String>();

    @Override
    public void onEnable()
    {
        reloadConfig();
        checkConfig();
        checkDebugMode();
        loadLang.loadLang(getConfig().getString("language"), this);
        loadLang.loadLang("backup", this);
        registerListeners(getPluginManager());
        registerCommands();
        getLogger().log(Level.INFO, Lang.get("plugin-enabled"), new Object[]{getDescription().getName(), getDescription().getVersion()});
    }

    @Override
    public void onDisable()
    {
        getLogger().log(Level.INFO, Lang.get("plugin-disabled"), new Object[]{getDescription().getName(), getDescription().getVersion()});
    }

    private void registerListeners(PluginManager pm)
    {
        moveMessages.moveOldMessages(this);
        loadMessages();
        pm.registerEvents(new Time(this), this);
        pm.registerEvents(new PlayerJoin(this), this);

        Announce announce = new Announce();
        announce.setup(this);
    }

    private void registerCommands()
    {
        getCommand("simpleautomessage").setExecutor(new SimpleautomessageCommand(this));
    }

    public void checkConfig()
    {
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
            System.out.println("[" + getDescription().getName() + "] " + "No config.yml found, config.yml created.");
        }
    }

    private void loadMessages()
    {
        messages.clear();
        messages.add("This message should not be displayed. Contact the developers");
        try {
            File file = getMessageFile();
            if (!file.exists()) {
                file.createNewFile();
                this.getLogger().log(Level.INFO, Lang.get("no-msg-file"), file.getName());
            }
            BufferedReader read;
            read = new BufferedReader(new FileReader(file));

            String line;
            while ((line = read.readLine()) != null) {
                if (!messages.contains(line)) {
                    if (!line.startsWith("#")) {
                        messages.add(line);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public YamlConfiguration getLang()
    {
        return LANG;
    }

    public File getLangFile()
    {
        return LANG_FILE;
    }

    public void debug(String s)
    {
        if (debugm) {
            getLogger().log(Level.INFO, "[" + "Debug" + "]" + " {0}", s);
        }
    }

    public void checkDebugMode()
    {
        if (getConfig().getBoolean("debug")) {
            debugm = true;
        }
    }

    public boolean onlinePlayers()
    {
        int conf = getConfig().getInt("min-players");
        int online = Bukkit.getOnlinePlayers().size();
        return online >= conf;
    }

    public int getRandomInt(int maxammount)
    {
        int qwe = 0;
        int a;
        if (maxammount < 3 || qwe == 0) {
            Random n = new Random();
            int num = 0;
            for (int count = 1; count <= 2; count++) {
                num = 1 + n.nextInt(maxammount);
            }
            a = num;
        } else {
            Random n = new Random();
            int num = 0;
            for (int count = 1; count <= 2; count++) {
                num = 1 + n.nextInt(maxammount);
            }
            a = num;
            if (qwe == a) {
                this.getRandomInt(maxammount);
            }
        }
        return a;
    }

    public File getMessageFile()
    {
        return new File("" + this.getDataFolder() + File.separatorChar + this.getConfig().getString("message-file"));
    }
}
