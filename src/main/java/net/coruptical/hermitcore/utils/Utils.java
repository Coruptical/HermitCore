package net.coruptical.hermitcore.utils;

import net.coruptical.hermitcore.HermitCore;
import org.bukkit.ChatColor;

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

}
