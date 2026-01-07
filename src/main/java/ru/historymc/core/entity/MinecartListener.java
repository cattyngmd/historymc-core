package ru.historymc.core.entity;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import ru.historymc.core.utils.events.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class MinecartListener implements Listener {
    private static final double DIST_SQR = 6 * 6;
    private final Map<Player, StorageMinecart> players = new HashMap<>();

    @EventHandler(type = Event.Type.PLAYER_JOIN)
    public void onJoin(PlayerJoinEvent event) {
        players.remove(event.getPlayer());
    }

    @EventHandler(type = Event.Type.PLAYER_QUIT)
    public void onQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer());
    }

    @EventHandler(type = Event.Type.ENTITY_DEATH)
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player)
            players.remove(player);
    }

    @EventHandler(type = Event.Type.PLAYER_INTERACT_ENTITY)
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof StorageMinecart cart) {
            players.remove(player);
            players.put(player, cart);
        }
    }

    @EventHandler(type = Event.Type.PLAYER_MOVE)
    public void onPlayerMove(PlayerMoveEvent event) {
        StorageMinecart cart = players.get(event.getPlayer());
        if (cart == null) return;
        double dist = cart.getLocation().distanceSquared(event.getTo());
        if (dist < DIST_SQR) return;
        close(event.getPlayer());
    }

    @EventHandler(type = Event.Type.VEHICLE_MOVE)
    public void onMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof StorageMinecart cart) {
            for (Map.Entry<Player, StorageMinecart> e : players.entrySet()) {
                if (cart != e.getValue()) continue;
                Player player = e.getKey();
                double dist = player.getLocation().distanceSquared(event.getTo());
                if (dist < DIST_SQR) continue;
                close(player);
            }
        }
    }

    private void close(Player player) {
        ((CraftPlayer) player).getHandle().closeInventory();
        players.remove(player);
    }
}
