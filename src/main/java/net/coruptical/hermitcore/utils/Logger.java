package net.coruptical.hermitcore.utils;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Logger {
    private Plugin plugin;

    public Plugin getPlugin() {
        return plugin;
    }

    public Logger(Plugin plugin){
        this.plugin = plugin;
    }

    public void info(String msg){
        getPlugin().getLogger().log(Level.INFO, msg);
    }

    public void warn(String msg){
        getPlugin().getLogger().log(Level.WARNING, msg);
    }
    public void severe(String msg){
        getPlugin().getLogger().log(Level.SEVERE, msg);
    }
}
