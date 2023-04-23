package net.coruptical.hermitcore.commands;

import com.google.gson.Gson;
import net.coruptical.hermitcore.HermitCore;
import net.coruptical.hermitcore.models.ConfigPermission;
import net.coruptical.hermitcore.models.ItemModel;
import net.coruptical.hermitcore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            World world = null;

            if (sender instanceof Player ) {
                p = (Player) sender;
                if (!p.hasPermission(ConfigPermission.RESTORE_CHEST.getLabel())){
                    p.sendMessage(plugin.getNoPermissionMessage());
                    return true;
                }
            }

            if (args.length >= 5){
                int x = 0;
                int y = 0;
                int z = 0;
                if (plugin.getServer().getWorld(args[0]) == null){
                    send(sender, ut.colored("&cInvalid world!"));
                }
                world = plugin.getServer().getWorld(args[0]);

                try {
                    x = Integer.parseInt(args[1]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ut.colored("&cInvalid x-coordinate!"));
                    return true;
                }
                try {
                    y = Integer.parseInt(args[2]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ut.colored("&cInvalid y-coordinate!"));
                    return true;
                }
                try {
                    z = Integer.parseInt(args[3]);
                }catch (NumberFormatException e){
                    sender.sendMessage(ut.colored("&cInvalid z-coordinate!"));
                    return true;
                }

                processRestore(world,x,y,z,sender,args[4]);
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
            list.add("(world) (x) (y) (z) (filename)");
            return list;
        }
        if (args.length == 1 && args[0].length() == 0){
            for (World world : plugin.getServer().getWorlds()){
                list.add(world.getName());
            }
        }
        if(args.length == 2 && args[1].length() == 0){
            list.add("x");
        }
        if(args.length == 3 && args[2].length() == 0){
            list.add("y");
        }
        if(args.length == 4 && args[3].length() == 0){
            list.add("z");
        }
        if  (args.length == 5 && args[4].length() == 0){
            if (dirListing != null){
                ArrayList<File> files = new ArrayList<>();
                for (File file : dirListing){
                    files.add(file);
                }

                for (int i = 0; i < files.size(); i++){
                    list.add(files.get(i).getName().replace(" ", "_"));
                }
            }
        }
        if (args.length == 5 && args[4].length() > 0){
            if (dirListing != null){
                for (File file : dirListing){
                    if (file.getName().replace(" ", "_").startsWith(args[3]))
                        list.add(file.getName().replace(" ", "_"));
                }
            }
        }
        if (args.length == 1 && args[0].length() > 0){
            for (World world : plugin.getServer().getWorlds()){
                String wn = world.getName();
                if (wn.toLowerCase().startsWith(args[0].toLowerCase()))
                    list.add(wn);
            }
        }

        return list;
    }


    private void processRestore(World world, int x, int y, int z, CommandSender sender, String path){
        List<String> data = new ArrayList<>();

        plugin.getServer().broadcastMessage(""+x+" "+y+" "+z+" "+sender.getClass().toString()
                + " " + path + "\n\n");

        File file = new File(plugin.getDataFolder().getAbsolutePath() + "\\"
                + DIR_NAME + "\\" + path.replace("_", " "));
        if (!file.exists()){
            send(sender, ut.colored("&cThis is an invalid file name!"));
            return;
        }



        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String json = "";
        String ownerUUID = "";

        for (String line : data){
            String[] fileData = line.split(";");
            for (String piece : fileData){
                send(sender, piece);
            }

            String[] coords = fileData[1].split(",");
            int fileX = Integer.parseInt(coords[0]);
            int fileY = Integer.parseInt(coords[1]);
            int fileZ = Integer.parseInt(coords[2]);

            if (fileX == x){
                if (fileY == y){
                    if (fileZ == z){
                        json = fileData[2];
                        ownerUUID = fileData[1];

                        break;
                    }
                }
            }
        }

        Gson gson = new Gson();
        ItemModel[] items = gson.fromJson(json, ItemModel[].class);

        if (items == null)
            return;
        if(items.length == 0)
            return;

        Block b = world.getBlockAt(x,y,z);
        b.setType(Material.CHEST);
        Chest chest = (Chest) b.getState();
        PersistentDataContainer pdc = chest.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, HermitCore.CHEST_OWNER_KEY);
        pdc.set(key, PersistentDataType.STRING, ownerUUID);

        plugin.getPlacedChests().add(chest.getLocation());


        chest.update();


        Inventory inv = chest.getBlockInventory();
        for (ItemModel model : items){
            ItemStack stack = null;
            try {
                stack = new Utils().itemFrom64(model.getStackBase64());
            } catch (IOException e) {
                e.printStackTrace();
            }

            inv.setItem(model.getIndex(), stack);
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
