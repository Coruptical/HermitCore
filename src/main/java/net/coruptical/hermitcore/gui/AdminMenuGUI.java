package net.coruptical.hermitcore.gui;

import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.utils.GUIUtils;
import net.coruptical.hermitcore.utils.Settings;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AdminMenuGUI implements Listener {
    private Inventory inventory;
    private final int SIZE = 45;
    private final String NAME = "&7[&bStaff Menu&7]";
    private Utils ut = new Utils();


    public AdminMenuGUI(){
        create();
    }

    public void create(){
        inventory = Bukkit.createInventory(null, SIZE, ut.colored(NAME));


        new GUIUtils().fillWithPanes(Material.BLUE_STAINED_GLASS_PANE, inventory, SIZE);

        initItems();
    }

    public void initItems(){
        Settings settings = Settings.getInstance();
        inventory.addItem(createItem(Material.ICE, ut.colored("&7[&bFreeze All&7]"),
                settings.getStatusMessage(settings.isFreeze())));
        inventory.addItem(createItem(Material.OAK_SIGN, ut.colored("&7[&bChat&7]"),
                settings.getStatusMessage(settings.isChat())));
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
        Settings settings = Settings.getInstance();


        if (!settings.isMenuOpen()){
            humanEntity.openInventory(inventory);
            settings.setMenuOpen(true);
        }else{
            humanEntity.sendMessage(ut.colored(
                    "&cSomeone is already accessing the admin menu."));
        }

    }

    public void close(HumanEntity humanEntity){
        humanEntity.closeInventory();
    }

    public void update(HumanEntity humanEntity){
        Settings.getInstance().setMenuOpen(false);
        create();
        open(humanEntity);
    }

    // Update menu boolean in Settings.
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equalsIgnoreCase(ut.colored(NAME)))
            return;

        Settings settings = Settings.getInstance();
        settings.setMenuOpen(false);
    }


    // Cancel wrong clicks and manage them.
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Settings settings = Settings.getInstance();
        if(!e.getView().getTitle().equalsIgnoreCase(ut.colored(NAME)))
            return;

        e.setCancelled(true);


        if (!e.getClick().equals(ClickType.LEFT))
            return;

        ItemStack itemClicked = e.getCurrentItem();
        if (itemClicked == null || itemClicked.getType().isAir())
            return;

        if (itemClicked.getType().equals(Material.ICE)){
            if (settings.isFreeze()){
                settings.setFreeze(false);
            }else {
                settings.setFreeze(true);
            }
        }else if (itemClicked.getType().equals(Material.OAK_SIGN)){
            if (settings.isChat()){
                settings.setChat(false);
            }else {
                settings.setChat(true);
            }

        }
        update(e.getWhoClicked());


    }

    // Cancel dragging in inventory.
    @EventHandler
    public void onDrag(InventoryDragEvent e){
        if (!e.getView().getTitle().equalsIgnoreCase(ut.colored(NAME)))
            return;

        e.setCancelled(true);
    }

}
