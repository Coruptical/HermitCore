package net.coruptical.hermitcore.commands;

import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.gui.AdminMenuGUI;
import net.coruptical.hermitcore.models.ConfigPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffMenu implements CommandExecutor {
    HermitCore plugin = HermitCore.getInstance();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission(ConfigPermission.STAFF_MENU.getLabel())){
                AdminMenuGUI staffMenu = new AdminMenuGUI();
                staffMenu.open(p);
            }else {
                p.sendMessage(plugin.getNoPermissionMessage());
            }
        }
        return true;
    }
}
