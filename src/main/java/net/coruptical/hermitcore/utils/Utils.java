package net.coruptical.hermitcore.utils;

import net.coruptical.hermitcore.HermitCore;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class Utils {
    private HermitCore plugin = HermitCore.getInstance();

    public String colored(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String removeColorFromString(String string){
        String normalLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < string.length(); i++){
            if(normalLetters.contains(string.charAt(i) + "")){
                out.append(string.charAt(i));
            }
        }
        return out.toString();
    }

    public String itemTo64(ItemStack stack) throws IllegalStateException {

        try {
            long start = System.nanoTime();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(stack);

            // Serialize that array
            dataOutput.close();
            long elapsed = System.nanoTime() - start;
            plugin.getLogger().log(Level.INFO, "Time taken to b64 item: " +
                    (double) elapsed/1000000 + "ms");
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    public ItemStack itemFrom64(String data) throws IOException {
        try {
            long start = System.nanoTime();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            try {
                long elapsed = System.nanoTime() - start;
                plugin.getLogger().log(Level.INFO, "Time taken to get item from b64: " +
                        (double) elapsed/1000000 + "ms");
                return (ItemStack) dataInput.readObject();
            } finally {
                dataInput.close();
            }
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

}
