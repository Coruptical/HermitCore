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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChestLogger {
    private final HermitCore plugin = HermitCore.getInstance();

    public void start(){
        BukkitRunnable br = new BukkitRunnable() {
            @Override
            public void run() {
                for (Location location : plugin.getPlacedChests()){
                    Block b = location.getBlock();
                    Chest chest = (Chest) b.getState();


                    NamespacedKey key = new NamespacedKey(plugin, HermitCore.CHEST_DATA_KEY);
                    PersistentDataContainer pdc = chest.getPersistentDataContainer();
                    String json = pdc.get(key, PersistentDataType.STRING);


                    Gson gson = new Gson();
                    ChestFastItem[] items = gson.fromJson(json, ChestFastItem[].class);

                    if (items == null)
                        continue;
                    if(items.length == 0)
                        continue;

                    for(ChestFastItem item : items){
                        plugin.getServer().broadcastMessage(item.getIndex() + ";" + item.getType() +";" + item.getAmount());
                    }
                }
            }
        };

        br.runTaskTimer(plugin, 1L, HermitCore.getChestSaveIntervalInTicks());
    }

}
