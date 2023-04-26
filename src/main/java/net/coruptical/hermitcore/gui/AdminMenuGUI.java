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
    private final String NAME = "&7[&bStaff Menu&7]";
    private Utils ut = new Utils();


    public AdminMenuGUI(){
        inventory = Bukkit.createInventory(null, SIZE, ut.colored(NAME));


        new GUIUtils().fillWithPanes(Material.BLUE_STAINED_GLASS_PANE, inventory, SIZE);

        initItems();
    }

    public void initItems(){
        inventory.addItem(createItem(Material.ICE, ut.colored("&7[&bFreeze All&7]"),
                ut.colored("&fStatus: ") + ""));
        inventory.addItem(createItem(Material.OAK_SIGN, ut.colored("&7[&bChat&7]"),
                ut.colored("&fStatus: ") + ""));
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
        if(!e.getView().getTitle().equalsIgnoreCase(ut.colored(NAME)))
            return;

        e.setCancelled(true);
    }

    // Cancel dragging in inventory.
    @EventHandler
    public void onDrag(InventoryDragEvent e){
        if (!e.getView().getTitle().equalsIgnoreCase(ut.colored(NAME)))
            return;

        e.setCancelled(true);
    }

    // TODO - Stop players from being able to move items into inventory.
}
