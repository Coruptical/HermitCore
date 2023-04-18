package net.coruptical.hermitcore.utils;

import net.coruptical.hermitcore.HermitCore;
import org.bukkit.ChatColor;

public class Utils {
    private HermitCore plugin = HermitCore.getInstance();

    public String colored(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }


}
