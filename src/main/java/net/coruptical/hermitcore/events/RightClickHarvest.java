package net.coruptical.hermitcore.events;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RightClickHarvest implements Listener {

    // Allow player to harvest wheat with right click and re-plant
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (!e.hasBlock()) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!e.getClickedBlock().getType().equals(Material.WHEAT)) return;
        Block b = e.getClickedBlock();
        Ageable wheat = (Ageable) b.getBlockData();
        World world = b.getWorld();
        if (wheat.getAge() != wheat.getMaximumAge()) return;

        wheat.setAge(0);

        b.setBlockData(wheat);
        world.dropItemNaturally(b.getLocation(), new ItemStack(Material.WHEAT));
        int numSeeds = new Random().nextInt(3) + 1;

        world.dropItemNaturally(b.getLocation(), new ItemStack(Material.WHEAT_SEEDS, numSeeds));


    }

}
