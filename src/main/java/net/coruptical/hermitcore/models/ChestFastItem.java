package net.coruptical.hermitcore.models;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class ChestFastItem implements Serializable {
    private String type;
    private short amount;
    private short index;

    public ChestFastItem(ItemStack item, short index){
        this.type = item.getType().toString();
        this.amount = (short) item.getAmount();
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public short getAmount() {
        return amount;
    }

    public short getIndex() {
        return index;
    }
}
