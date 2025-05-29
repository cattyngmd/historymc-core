package ru.historymc.core.utils;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class ConfigFile {
    private final File file;

    public ConfigFile(Plugin plugin, String name) {
        this(new File(plugin.getDataFolder(), name));
    }

    public ConfigFile(File file) {
        this.file = file;
    }

    public void createIfAbsent() throws IOException {
        if (!file.exists()) {
            byte[] bytes = getLocal().readAllBytes();
            Files.write(file.toPath(), bytes);
        }
    }

    public InputStream getLocal() {
        return getClass()
                .getClassLoader()
                .getResourceAsStream("defaults/" + file.getName());
    }

    public File getFile() {
        return file;
    }
}
