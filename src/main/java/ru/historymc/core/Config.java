package ru.historymc.core;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;
import ru.historymc.core.utils.BlockPos;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class Config extends Configuration {
    private final Map<String, ChatColor> colorMap = new HashMap<>();

    private BlockPos spawn;
    private int spawnRadius;
    private int worldBorder;

    public Config(File file) {
        super(file);
        super.load();
        write();
        save();
        load();
    }

    public void write() {
        generate("world.spawn", List.of(0, 127, 0));
        generate("world.spawn-radius", 400);
        generate("world.world-border", 12_551_000);

        generate("chat.color-symbols.green", ">");
        generate("chat.color-symbols.gold", "<");
    }

    public void load() {
        List<Integer> spawn = (List<Integer>) getProperty("world.spawn");
        this.spawn = new BlockPos(spawn.get(0), spawn.get(1), spawn.get(2));
        this.spawnRadius = (int) getProperty("world.spawn-radius");
        this.worldBorder = (int) getProperty("world.world-border");

        getNode("chat.color-symbols").getAll().forEach((k, v) -> {
            colorMap.put((String) v, ChatColor.valueOf(k.toUpperCase(Locale.ROOT)));
        });
    }

    public void generate(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public int getWorldBorder() {
        return worldBorder;
    }

    public BlockPos getSpawn() {
        return spawn;
    }

    public ChatColor getColor(String key) {
        if (key.isEmpty()) return null;
        return colorMap.get(key.substring(0, 1));
    }
}
