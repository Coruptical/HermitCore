package net.coruptical.hermitcore.events;

import com.google.gson.Gson;
import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.models.ItemModel;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ChestStoreListener implements Listener {
    private final HermitCore plugin = HermitCore.getInstance();


    // Store the data in chest block when we close inventory of that chest.
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Inventory inv = e.getInventory();
        if(inv.getType().equals(InventoryType.CHEST)){
            Location loc = e.getInventory().getLocation();
            if (loc == null)
                return;


            Block b = loc.getBlock();

            NamespacedKey key = new NamespacedKey(plugin, HermitCore.CHEST_DATA_KEY);

            Chest chest = (Chest) b.getState();

            PersistentDataContainer pdc = chest.getPersistentDataContainer();

            Gson gson = new Gson();
            ArrayList<ItemModel> chestItems = new ArrayList<>();

            for (short i = 0; i < inv.getSize(); i++){
                ItemStack stack = inv.getItem(i);

                if (stack == null || stack.getType().isAir())
                    continue;

                String b64 = new Utils().itemTo64(stack);

                ItemModel model = new ItemModel(i, b64);
                chestItems.add(model);
            }

            String json = gson.toJson(chestItems);
            pdc.set(key, PersistentDataType.STRING, json);

            chest.update();
        }
    }

    // Store owner's UUID of chest when chest gets placed down.
    // Also add the location of this chest block to plugin for ability to persistently store.
    @EventHandler
    public void onChestPlace(BlockPlaceEvent e){
        if(e.getBlock().getType().equals(Material.CHEST)){
            Player p = e.getPlayer();
            String SUUID = p.getUniqueId().toString();

            Chest chest = (Chest) e.getBlock().getState();
            PersistentDataContainer pdc = chest.getPersistentDataContainer();

            NamespacedKey key = new NamespacedKey(plugin, HermitCore.CHEST_OWNER_KEY);
            pdc.set(key, PersistentDataType.STRING, SUUID);

            chest.update();

            plugin.getPlacedChests().add(chest.getLocation());
        }
    }


    // Removes location of chest from plugin when chest broken
    @EventHandler
    public void onChestBreak(BlockBreakEvent e){
        if(e.getBlock().getType().equals(Material.CHEST)){
            if(plugin.getPlacedChests().contains(e.getBlock().getLocation())) {
                plugin.getPlacedChests().remove(e.getBlock().getLocation());
            }
        }
    }


}
