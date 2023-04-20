package net.coruptical.hermitcore.commands;

import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.models.ConfigPermission;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestoreChest implements CommandExecutor, TabCompleter {
    private final HermitCore plugin = HermitCore.getInstance();
    private final String DIR_NAME = "ChestSaves";
    Utils ut = new Utils();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player || sender instanceof ConsoleCommandSender){
            Player p;

            if (sender instanceof Player ) {
                p = (Player) sender;
                if (!p.hasPermission(ConfigPermission.RESTORE_CHEST.getLabel())){
                    p.sendMessage(plugin.getNoPermissionMessage());
                    return true;
                }
            }

            if (args.length >= 4){
                int x = 0;
                int y = 0;
                int z = 0;

                try {
                    x = Integer.parseInt(args[0]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ut.colored("&cInvalid x-coordinate!"));
                    return true;
                }
                try {
                    y = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ut.colored("&cInvalid y-coordinate!"));
                    return true;
                }
                try {
                    z = Integer.parseInt(args[2]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ut.colored("&cInvalid z-coordinate!"));
                    return true;
                }

                processRestore(x,y,z,sender,args[3]);
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        String path = plugin.getDataFolder().getAbsolutePath() + "\\" + DIR_NAME;
        File dir = new File(path);
        File[] dirListing = dir.listFiles();

        if(args.length == 0){
            list.add("(x) (y) (z) (filename)");
            return list;
        }
        if(args.length == 1 && args[0].length() == 0){
            list.add("x");
        }
        if(args.length == 2 && args[1].length() == 0){
            list.add("y");
        }
        if(args.length == 3 && args[2].length() == 0){
            list.add("z");
        }
        if  (args.length == 4 && args[3].length() == 0){
            if (dirListing != null){
                for (File file : dirListing){
                    list.add(file.getName().replace(" ", "_"));
                }
            }
        }
        if (args.length == 4 && args[3].length() > 0){
            if (dirListing != null){
                for (File file : dirListing){
                    if (file.getName().replace(" ", "_").startsWith(args[3]))
                        list.add(file.getName().replace(" ", "_"));
                }
            }
        }

        return list;
    }


    private void processRestore(int x, int y, int z, CommandSender sender, String path){
        List<String> data = new ArrayList<>();

        plugin.getServer().broadcastMessage(""+x+" "+y+" "+z+" "+sender.getClass().toString()
                + " " + path);

        File file = new File(path.replace("_", ""));
        if (!file.exists()){
            send(sender, ut.colored("&cThis is an invalid file name!"));
        }



        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }

    private void send(CommandSender sender, String msg){
        if (sender instanceof Player){
            sender.sendMessage(msg);
        }else if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(new Utils().removeColorFromString(msg));
        }
    }


}
