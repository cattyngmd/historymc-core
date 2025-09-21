package ru.historymc.core.tasks.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;
import ru.historymc.core.tasks.AbstractTask;
import ru.historymc.core.utils.Ticks;

public class ResetColorTask extends AbstractTask {
    public ResetColorTask(Main main) {
        super(main, Ticks.ofMinutes(5), 100);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerExtra extra = PlayerStorage.getInstance().get(player);
            if (extra.getColor() == ChatColor.WHITE) continue;
            if (System.currentTimeMillis() < extra.getResetColorTime()) continue;
            extra.setColor(ChatColor.WHITE);
        }
    }
}
