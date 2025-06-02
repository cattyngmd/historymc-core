package ru.historymc.core.illegals;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.inventory.ItemStack;
import ru.historymc.core.utils.Utils;

public class IllegalsListener implements Listener {
    @EventHandler
    public void onSlot(PlayerInventoryEvent event) {
        for (int i = 0; i < event.getInventory().getSize(); i++) {
            ItemStack item = event.getInventory().getItem(i);
            if (item.getAmount() < 0 || item.getAmount() > 64) {
                item.setAmount(Utils.clamp(item.getAmount(), 0, 64));
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Material material = event.getBlock().getType();
        Player player = event.getPlayer();

        if (Illegals.isIllegal(material) && player.getItemInHand().getType() == material) {
            event.setCancelled(true);
            player.setItemInHand(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onDrop(ItemSpawnEvent event) {
        if (event.getEntity() instanceof Item) {
            Item entity = (Item) event.getEntity();
            if (Illegals.isIllegal(entity.getItemStack().getType())) {
                event.setCancelled(true);
            }
        }
    }
}
