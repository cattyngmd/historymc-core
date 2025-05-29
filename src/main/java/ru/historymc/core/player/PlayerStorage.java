package ru.historymc.core.player;

import org.bukkit.command.CommandSender;
import ru.historymc.core.player.source.DataSource;
import ru.historymc.core.player.source.FileDataSource;

import java.util.HashMap;
import java.util.Map;

public final class PlayerStorage {
    private final Map<String, PlayerExtra> players = new HashMap<>();
    private final DataSource source = new FileDataSource();

    private PlayerStorage() {

    }

    public PlayerExtra get(CommandSender sender) {
        return get(sender.getName());
    }

    public PlayerExtra get(String extra) {
        return players.get(extra);
    }

    public DataSource getSource() {
        return source;
    }

    void add(PlayerExtra extra) {
        players.put(extra.getName(), extra);
    }

    PlayerExtra remove(String name) {
        return players.remove(name);
    }

    public static PlayerStorage getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PlayerStorage INSTANCE = new PlayerStorage();
    }
}
