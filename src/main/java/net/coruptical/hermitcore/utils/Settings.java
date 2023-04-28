package net.coruptical.hermitcore.utils;

import net.coruptical.hermitcore.HermitCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Settings {
    private HermitCore plugin;
    private final String FILE_NAME = "settings";
    private File settingsYml;
    private FileConfiguration settingsConfig;

    private boolean chat;
    private boolean freeze;

    private boolean menuOpen = false;

    // Create a private instance of this class, so we can't have multiple of these classes.
    private static final Settings i = new Settings();

    private Settings(){

    }

    public static Settings getInstance() {
        return i;
    }

    public FileConfiguration getConfig(){
        return settingsConfig;
    }

    public void init(){
        plugin = HermitCore.getInstance();

        // Create new custom config.
        settingsYml = new File(plugin.getDataFolder().getAbsolutePath()+"/"
        +FILE_NAME+".yml");
        settingsConfig = YamlConfiguration.loadConfiguration(settingsYml);
        save();

        chat = getConfig().getBoolean("chat");
        freeze = getConfig().getBoolean("freeze");

    }

    public void save(){
        try {
            // If this file doesn't exist, create one from resources.
            if (!settingsYml.exists()){
                plugin.saveResource("settings.yml", false);
                return;
            }

            // If it exists, just save it with values.
            settingsConfig.save(settingsYml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveValues(){
        try {
            // Save all values to config.
            getConfig().set("chat", chat);
            getConfig().set("freeze", freeze);


            // Save the config.
            getConfig().save(settingsYml);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getStatusMessage(boolean bool){
        Utils utils = new Utils();
        if (bool){
            return utils.colored("&fStatus: &aOn");
        }else {
            return utils.colored("&fStatus: &cOff");
        }
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public boolean isChat() {
        return chat;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
    }
}