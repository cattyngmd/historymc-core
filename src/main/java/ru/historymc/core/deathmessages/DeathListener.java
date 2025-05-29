package ru.historymc.core.deathmessages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class DeathListener implements Listener {
    private static final Map<Player, DamageData> data = new HashMap<>();
    private final DeathMessages messages;

    public DeathListener(DeathMessages messages) {
        this.messages = messages;
    }

    @EventHandler public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player entity) {
            DamageData damage = data.getOrDefault(entity, DamageData.NONE);
            String message = messages.format(entity, damage);
            Bukkit.getServer().broadcastMessage(message);
            data.remove(entity);
        }
    }

    @EventHandler private void onAttack(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;

        if (event instanceof EntityDamageByEntityEvent) {
            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
            data.put(victim, DamageData.of(attacker, event.getCause()));
        } else {
            data.put(victim, DamageData.of(event.getCause()));
        }
    }

}
