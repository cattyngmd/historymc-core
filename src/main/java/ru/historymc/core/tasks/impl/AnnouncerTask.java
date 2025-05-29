package ru.historymc.core.tasks.impl;

import ru.historymc.core.Main;
import ru.historymc.core.tasks.AbstractTask;
import ru.historymc.core.utils.Utils;

// TODO make Ticks class and use Ticks.ofSeconds or Ticks.ofHours instead
public class AnnouncerTask extends AbstractTask {
    public AnnouncerTask(Main main) {
        super(main, 20 * 60 * 30, 20);
    }

    @Override
    public void run() {
        Utils.broadcast("telegram: https://t.me/historymcru");
        Utils.broadcast("vk: https://vk.com/historymc");
    }
}
