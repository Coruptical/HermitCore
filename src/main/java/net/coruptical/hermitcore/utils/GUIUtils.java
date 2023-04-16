package net.coruptical.hermitcore.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIUtils {
    public void fillWithPanes(Material material, Inventory inventory, int size){


        if ( (size % 9) != 0)
            throw new IllegalArgumentException("Invalid inventory size given!");

        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);


        switch (size) {
            case 27:
                for (int i = 0; i <= 9; i++){
                    inventory.setItem(i, stack);
                }

                inventory.setItem(17, stack);

                for(int i = 18; i <= 26; i++){
                    inventory.setItem(i, stack);
                }
                break;
            case 36:
                for (int i = 0; i <= 9; i++){
                    inventory.setItem(i, stack);
                }
                inventory.setItem(17, stack);
                inventory.setItem(18, stack);
                inventory.setItem(26, stack);

                for(int i = 27; i <= 35; i++){
                    inventory.setItem(i, stack);
                }
                break;
            case 45:
                for (int i = 0; i <= 9; i++){
                    inventory.setItem(i, stack);
                }

                inventory.setItem(17, stack);
                inventory.setItem(18, stack);
                inventory.setItem(26, stack);
                inventory.setItem(27, stack);
                inventory.setItem(35, stack);

                for(int i = 36; i <= 44; i++){
                    inventory.setItem(i, stack);
                }

                break;
            case 54:

                break;
            default:
                throw new IllegalArgumentException("Invalid/too small inventory size!");
        }
    }
}
