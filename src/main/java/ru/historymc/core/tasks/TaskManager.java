package ru.historymc.core.tasks;

import org.bukkit.Bukkit;
import ru.historymc.core.Main;
import ru.historymc.core.tasks.impl.AnnouncerTask;
import ru.historymc.core.tasks.impl.RestartTask;
import ru.historymc.core.tasks.impl.SpawnRestoreTask;

public final class TaskManager {
    private final Main plugin;

    public TaskManager(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        register(new RestartTask(plugin));
        register(new AnnouncerTask(plugin));
        register(new SpawnRestoreTask(plugin));
    }

    private void register(AbstractTask task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, task.getDelay(), task.getPeriod());
    }
}
