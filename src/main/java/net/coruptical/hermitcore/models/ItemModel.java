package net.coruptical.hermitcore.models;

import java.io.Serializable;

public class ItemModel implements Serializable {
    private int index;
    private String stackBase64;

    public ItemModel(int index, String stackBase64) {
        this.index = index;
        this.stackBase64 = stackBase64;
    }

    public int getIndex() {
        return index;
    }

    public String getStackBase64() {
        return stackBase64;
    }
}
