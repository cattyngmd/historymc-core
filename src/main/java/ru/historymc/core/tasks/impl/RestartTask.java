package ru.historymc.core.tasks.impl;

import org.bukkit.Bukkit;
import ru.historymc.core.Main;
import ru.historymc.core.tasks.AbstractTask;
import ru.historymc.core.utils.Utils;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static java.time.Duration.*;

public class RestartTask extends AbstractTask {
    private final long start = System.currentTimeMillis();
    private final long restart = ofHours(4).toMillis();

    private final List<WarnPair> pairs = new LinkedList<>(List.of(
            new WarnPair(ofMinutes(10), "10 minutes"),
            new WarnPair(ofMinutes(5), "5 minutes"),
            new WarnPair(ofMinutes(1), "1 minute"),
            new WarnPair(ofSeconds(15), "15 seconds")
    ));

    public RestartTask(Main main) {
        super(main, 20, 20);
    }

    @Override
    public void run() {
        if (Utils.passed(start, restart)) {
            Bukkit.getServer().shutdown();
            return;
        }

        if (!pairs.isEmpty() && passed(pairs.getFirst())) {
            WarnPair pair = pairs.removeFirst();
            Utils.broadcast("The server will be restarted in %s.", pair.message());
        }
    }

    private boolean passed(WarnPair pair) {
        return Utils.passed(start, restart - pair.duration.toMillis());
    }

    private record WarnPair(Duration duration, String message) {
    }
}
