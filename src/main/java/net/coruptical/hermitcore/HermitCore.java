package net.coruptical.hermitcore;

import net.coruptical.hermitcore.commands.RestoreChest;
import net.coruptical.hermitcore.commands.StaffChat;
import net.coruptical.hermitcore.commands.StaffMenu;
import net.coruptical.hermitcore.events.ChatEvent;
import net.coruptical.hermitcore.events.ChestStoreListener;
import net.coruptical.hermitcore.events.RightClickHarvest;
import net.coruptical.hermitcore.gui.AdminMenuGUI;
import net.coruptical.hermitcore.utils.ChestLogger;
import net.coruptical.hermitcore.utils.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public final class HermitCore extends JavaPlugin {


    // TODO - Think of new custom admin features
    // TODO - Make a /freeze command that freezes a player PERSISTENTLY.

    // TODO - Work on making all player placed chests have owner UUIDS for later stages?
    // TODO - Make all chests placed save data in them every 5 minutes. Store this in new files.
    // TODO - From those files - Be able to roll back data.

    public static final String STAFF_CHAT_KEY = "staff-chat-toggle";
    public static final String INFORMATION_KEY = "information";
    public static final String CHEST_OWNER_KEY = "chest-owner-uuid";
    public static final String CHEST_DATA_KEY = "chest-data";

    private static HermitCore instance;

    public static HermitCore getInstance() {
        return instance;
    }


    private Set<Location> placedChests = new HashSet<>();

    public Set<Location> getPlacedChests() {
        return placedChests;
    }

    @Override
    public void onEnable() {
        instance = this;
        Settings settings = Settings.getInstance();

        // Save config.yml to plugin folder.
        saveDefaultConfig();

        // Register everything - Listeners, Tab completers, Command executors, etc.
        register();


        ChestLogger chestLogger = new ChestLogger();
        chestLogger.start();

        settings.init();
        settings.save();

    }

    @Override
    public void onDisable() {


    }

    public void register(){
        // Register event listeners
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new AdminMenuGUI(), this);
        getServer().getPluginManager().registerEvents(new ChestStoreListener(), this);

        // Register commands
        getCommand("staffchat").setExecutor(new StaffChat());
        getCommand("staffmenu").setExecutor(new StaffMenu());
        getCommand("restorechest").setExecutor(new RestoreChest());

        // Register command tab completers
        getCommand("restorechest").setTabCompleter(new RestoreChest());

    }

    public String getStaffChatPermission(){
        return getConfig().getString("StaffChatPermission");
    }

    public String getNoPermissionMessage(){
        return ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("NoPermissionMessage"));
    }

    public String getRestoreChestPermission(){
        return getConfig().getString("ChestRestorePermission");
    }

    public String getStaffMenuPermission(){
        return getConfig().getString("StaffMenuPermission");
    }

    public static int getChestSaveIntervalInTicks(){
        String interval = instance.getConfig().getString("ChestSaveInterval");

        String type = ""+interval.charAt(interval.length()-1);


        interval = interval.replace("s", "");
        interval = interval.replace("m", "");
        interval = interval.replace("h", "");

        int time;

        switch (type){
            case "s":


                time = Integer.parseInt(interval);
                return time*20;
            case "m":


                time = Integer.parseInt(interval);
                return time*60*20;
            case "h":


                time = Integer.parseInt(interval);
                return time*60*60*20;
            default:
                throw new IllegalArgumentException("Invalid ");
        }
    }

}

