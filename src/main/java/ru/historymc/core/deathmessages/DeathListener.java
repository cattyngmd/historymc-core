package ru.historymc.core.deathmessages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.historymc.core.utils.events.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class DeathListener implements Listener {
    private static final Map<Player, DamageData> data = new HashMap<>();
    private final DeathMessages messages;

    public DeathListener(DeathMessages messages) {
        this.messages = messages;
    }

    @EventHandler(type = Event.Type.ENTITY_DEATH)
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player entity) {
            DamageData damage = data.getOrDefault(entity, DamageData.NONE);
            String message = messages.format(entity, damage);
            event.setDeathMessage(message);
            data.remove(entity);
        }
    }

    @EventHandler(type = Event.Type.ENTITY_DAMAGE)
    private void onAttack(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;

        if (event instanceof EntityDamageByEntityEvent) {
            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
            data.put(victim, DamageData.of(attacker, event.getCause()));
        } else {
            data.put(victim, DamageData.of(event.getCause()));
        }
    }

}
