package net.coruptical.hermitcore.events;

import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChatEvent implements Listener {
    private HermitCore plugin = HermitCore.getInstance();
    private Utils ut = new Utils();

    // Handles chat event for staff chat.
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission(plugin.getStaffChatPermission())){
            NamespacedKey key = new NamespacedKey(plugin, HermitCore.STAFF_CHAT_KEY);
            PersistentDataContainer container = p.getPersistentDataContainer();

            if(container.has(key, PersistentDataType.INTEGER)){
                int staffChatEnabled = container.get(key, PersistentDataType.INTEGER);
                if(staffChatEnabled == 0)
                    return;
                e.setCancelled(true);
                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()){
                    if(onlinePlayer.hasPermission(plugin.getStaffChatPermission())){
                        onlinePlayer.sendMessage(ut.colored("&8[&aStaffChat&8] &b" + p.getName() + " &7⇒ &6") + e.getMessage());
                    }
                }
            }
        }
    }
}
