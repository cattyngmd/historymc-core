package ru.historymc.core.player.source;

import org.bukkit.plugin.Plugin;
import ru.historymc.core.player.PlayerExtra;

public interface DataSource {
    void updatePlayer(PlayerExtra extra);

    PlayerExtra loadPlayer(String player);

    void setup(Plugin plugin);

    void close();
}
