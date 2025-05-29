package ru.historymc.core.player.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;
import ru.historymc.core.player.PlayerExtra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class FileDataSource implements DataSource {
    private static final Gson gson = new GsonBuilder().setLenient().create();
    private Path root;

    @Override
    public synchronized void updatePlayer(PlayerExtra extra) {
        Path path = toPath(extra.getName());

        try {
            Files.writeString(path, gson.toJson(extra));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized PlayerExtra loadPlayer(String player) {
        Path path = toPath(player);
        if (!path.toFile().exists()) {
            return null;
        }

        try {
            String s = Files.readString(path);

            return gson.fromJson(s, PlayerExtra.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setup(Plugin plugin) {
        this.root = plugin.getDataFolder().toPath().resolve("players");
        this.root.toFile().mkdir();
    }

    @Override
    public void close() {

    }

    private Path toPath(String name) {
        if (root == null) {
            throw new IllegalAccessError("No setup called");
        }
        return root.resolve(name.toLowerCase(Locale.ROOT) + ".data");
    }
}
