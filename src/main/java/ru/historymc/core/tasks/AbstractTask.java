package ru.historymc.core.tasks;

import ru.historymc.core.Main;

public abstract class AbstractTask implements Runnable {
    protected final Main main;

    private final int period;
    private final int delay;

    public AbstractTask(Main main, int period, int delay) {
        this.main = main;
        this.period = period;
        this.delay = delay;
    }

    public int getPeriod() {
        return period;
    }

    public int getDelay() {
        return delay;
    }
}
