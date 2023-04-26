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

    private static Settings i = new Settings();

    private Settings(){

    }

    public static Settings getInstance() {
        return i;
    }

    public void init(){
        plugin = HermitCore.getInstance();

        // Create new custom config.
        settingsYml = new File(plugin.getDataFolder().getAbsolutePath()+"/"
        +FILE_NAME+".yml");
        settingsConfig = YamlConfiguration.loadConfiguration(settingsYml);
    }

    public void save(){
        try {
            if (!settingsYml.exists()){
                plugin.saveResource("settings.yml", false);
                return;
            }

            settingsConfig.save(settingsYml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}