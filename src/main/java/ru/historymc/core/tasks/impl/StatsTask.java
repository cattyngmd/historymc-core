package ru.historymc.core.tasks.impl;

import org.bukkit.Bukkit;
import ru.historymc.core.Globals;
import ru.historymc.core.Main;
import ru.historymc.core.tasks.AbstractTask;
import ru.historymc.core.utils.Ticks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StatsTask extends AbstractTask {
    private int lastOnline;

    public StatsTask(Main main) {
        super(main, Ticks.ofSeconds(5), 20);
    }

    @Override
    public void run() {
        Globals.EXECUTOR.submit(this::dumpStatistics);
    }

    private void dumpStatistics() {
        Path path = main.getDataFolder().toPath().resolve("stats.txt");
        int online = Bukkit.getOnlinePlayers().length;

        if (lastOnline == online)
            return;

        lastOnline = online;

        try {
            Files.writeString(path, String.valueOf(online) + '\n');
        } catch (IOException e) {

        }
    }
}
