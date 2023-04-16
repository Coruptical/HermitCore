package net.coruptical.hermitcore.gui;

import net.coruptical.hermitcore.utils.GUIUtils;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AdminMenuGUI implements Listener {
    private final Inventory inventory;
    private final int SIZE = 45;


    public AdminMenuGUI(){
        inventory = Bukkit.createInventory(null, SIZE,
                new Utils().colored("&7[&bStaff Menu&7]"));


        new GUIUtils().fillWithPanes(Material.BLUE_STAINED_GLASS_PANE, inventory, SIZE);

        initItems();
    }

    public void initItems(){
        inventory.addItem(createItem(Material.WOODEN_AXE, "Some sword."));
    }

    protected ItemStack createItem(Material material, String name){
        final ItemStack item = new ItemStack(material,1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);



        return item;
    }

    protected ItemStack createItem(Material material, String name, String... lore){
        final ItemStack item = new ItemStack(material,1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);



        return item;
    }

    public void open(HumanEntity humanEntity){
        humanEntity.openInventory(inventory);
    }


    // Cancel wrong clicks and manage them.
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!e.getInventory().equals(inventory))
            return;

        e.setCancelled(true);
    }

    // Cancel dragging in inventory.
    @EventHandler
    public void onDrag(InventoryDragEvent e){
        if (!e.getInventory().equals(inventory))
            return;

        e.setCancelled(true);
    }

    // TODO - Stop players from being able to move items into inventory.
}
