package net.coruptical.hermitcore.utils;

import com.google.gson.Gson;
import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.models.ChestFastItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChestLogger {
    private final HermitCore plugin = HermitCore.getInstance();
    private final String DIR_NAME = "ChestSaves";


    public void start(){
        BukkitRunnable br = new BukkitRunnable() {
            @Override
            public void run() {

                if (plugin.getPlacedChests().isEmpty()) return;

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.m.ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now);

                createDirectory();

                String path = plugin.getDataFolder().getAbsolutePath() +
                        "\\" + DIR_NAME + "\\" + date + ".txt";

                File dataFile = new File(path);
                if(!dataFile.exists()){
                    try {
                        dataFile.createNewFile();

                        FileWriter writer = new FileWriter(path);

                        for (Location location : plugin.getPlacedChests()){
                            Block b = location.getBlock();
                            Chest chest = (Chest) b.getState();


                            NamespacedKey dataKey = new NamespacedKey(plugin, HermitCore.CHEST_DATA_KEY);
                            NamespacedKey ownerKey = new NamespacedKey(plugin, HermitCore.CHEST_OWNER_KEY);
                            PersistentDataContainer pdc = chest.getPersistentDataContainer();
                            String json = pdc.get(dataKey, PersistentDataType.STRING);
                            String oUUID = pdc.get(ownerKey, PersistentDataType.STRING);
                            String coords = location.getBlockX()+","+ location.getBlockY()+","+
                                    location.getBlockZ();

                            Gson gson = new Gson();
                            ChestFastItem[] items = gson.fromJson(json, ChestFastItem[].class);

                            if (items == null)
                                continue;
                            if(items.length == 0)
                                continue;


                            String toWrite = oUUID+";"+coords+";"+json;
                            writer.append(toWrite);
                        }
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        br.runTaskTimer(plugin, 1L, HermitCore.getChestSaveIntervalInTicks());
    }

    private void createDirectory(){
        File dir = new File(plugin.getDataFolder().getAbsolutePath() + "\\" + DIR_NAME);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

}
