package ru.historymc.core.deathmessages;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import ru.historymc.core.Globals;
import ru.historymc.core.utils.Utils;
import ru.historymc.core.utils.ConfigFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class DeathMessages implements Globals {
    private final ConfigFile file;

    private final Map<EntityDamageEvent.DamageCause, List<String>> templates = new HashMap<>();
    private List<String> defaults = Collections.emptyList();

    public DeathMessages(ConfigFile file) throws IOException {
        this.file = file;
        load();
    }

    public static String format(String format, Player victim, DamageData data) {
        String result = format.replace("<victim>", victim.getName());
        if (data.damager() != null) {
            result = result.replace("<attacker>", Utils.getName(data.damager()));
        }
        return formatter.format(result);
    }

    public String format(Player victim, DamageData data) {
        EntityDamageEvent.DamageCause cause = Utils.passed(data.damageTime(), 5000)
                ? null
                : data.cause();
        return format(getTemplate(cause), victim, data);
    }

    public String getTemplate(EntityDamageEvent.DamageCause cause) {
        if (cause == null) return defaults.getFirst();
        List<String> possibles = templates.getOrDefault(cause, defaults);
        return possibles.get(ThreadLocalRandom.current().nextInt(possibles.size()));
    }

    public void load() throws IOException {
        file.createIfAbsent();
        String s = Files.readString(file.getFile().toPath());
        Map<String, List<String>> map = (Map<String, List<String>>) yaml.load(s);
        for (String key : map.keySet()) {
            if (key.equals("default")) {
                defaults = map.get(key);
                continue;
            }
            String str = key.toUpperCase().replace("-", "_");
            EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.valueOf(str);
            templates.put(cause, map.get(key));
        }
    }
}
