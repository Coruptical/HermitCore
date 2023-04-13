package net.coruptical.hermitcore;

import net.coruptical.hermitcore.commands.StaffChat;
import net.coruptical.hermitcore.events.ChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class HermitCore extends JavaPlugin {
    private static HermitCore instance;

    public static HermitCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;


        // Save config.yml to plugin folder.
        saveDefaultConfig();

        // Register everything - Listeners, Tab completers, Command executors, etc.
        register();

    }

    @Override
    public void onDisable() {


    }

    public void register(){
        // Register event listeners
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);


        // Register commands
        getCommand("staffchat").setExecutor(new StaffChat());

        // Register command tab completers


    }

    public String getStaffChatPermission(){
        return getConfig().getString("StaffChatPermission");
    }

    public String getNoPermissionMessage(){
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("NoPermissionMessage"));
    }

}

