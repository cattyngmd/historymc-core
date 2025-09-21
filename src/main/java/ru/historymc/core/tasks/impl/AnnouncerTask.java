package ru.historymc.core.tasks.impl;

import ru.historymc.core.Main;
import ru.historymc.core.tasks.AbstractTask;
import ru.historymc.core.utils.Ticks;
import ru.historymc.core.utils.Utils;

public class AnnouncerTask extends AbstractTask {
    public AnnouncerTask(Main main) {
        super(main, Ticks.ofMinutes(30), 20);
    }

    @Override
    public void run() {
        Utils.broadcast("telegram: https://t.me/historymcru");
        Utils.broadcast("vk: https://vk.com/historymc");
    }
}
