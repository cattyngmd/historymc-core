package ru.historymc.core.deathmessages;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public record DamageData(Entity damager, long damageTime, EntityDamageEvent.DamageCause cause) {
    public static final DamageData NONE = new DamageData(null, -1, null);

    public static DamageData of(Entity damager, EntityDamageEvent.DamageCause cause) {
        return new DamageData(damager, System.currentTimeMillis(), cause);
    }

    public static DamageData of(EntityDamageEvent.DamageCause cause) {
        return of(null, cause);
    }
}
