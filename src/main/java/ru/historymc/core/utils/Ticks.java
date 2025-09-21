package ru.historymc.core.utils;

public final class Ticks {
    private Ticks() { }

    public static int ofDays(double days) {
        return ofHours(24 * days);
    }

    public static int ofHours(double hours) {
        return ofMinutes(60 * hours);
    }

    public static int ofMinutes(double minutes) {
        return ofSeconds(60 * minutes);
    }

    public static int ofSeconds(double seconds) {
        return (int) (seconds * 20);
    }
}
