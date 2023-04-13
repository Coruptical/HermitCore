package net.coruptical.hermitcore.commands;

import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class StaffChat implements CommandExecutor {
    HermitCore plugin = HermitCore.getInstance();
    Utils ut = new Utils();
    private final String keyName = "staff-chat-toggle";
    private final String ENABLE = "&aEnabled &fstaff chat.";
    private final String DISABLE = "&cDisabled &fstaff chat.";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
       if(sender instanceof Player){
           Player p = (Player) sender;
           if(p.hasPermission(plugin.getStaffChatPermission())){
               NamespacedKey key = new NamespacedKey(plugin, keyName);


               if(!p.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)){
                   p.getPersistentDataContainer().set(key,
                           PersistentDataType.INTEGER,
                           1);
                   p.sendMessage(ut.colored(ENABLE));

               }else{
                   int staffChatEnabled = p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
                   if(staffChatEnabled == 0){
                       p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
                       p.sendMessage(ut.colored(ENABLE));
                   }else if (staffChatEnabled == 1){
                       p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 0);
                       p.sendMessage(ut.colored(DISABLE));
                   }
               }
           }else{
               p.sendMessage(plugin.getNoPermissionMessage());
           }
       }
        return true;
    }

}
