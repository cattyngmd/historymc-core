package ru.historymc.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Utils {
    private static final int SUBSTRING = "Craft".length();

    public static int clamp(int a, int min, int max) {
        return Math.min(Math.max(a, min), max);
    }

    public static boolean passed(long since, long millis) {
        return System.currentTimeMillis() - since >= millis;
    }

    public static String getName(Entity entity) {
        if (entity instanceof Player) {
            return ((Player) entity).getName();
        }
        return entity.toString().substring(SUBSTRING);
    }

    public static void broadcast(String message) {
        Bukkit.getServer().broadcastMessage(String.format("[%sSERVER%s] %s", ChatColor.RED, ChatColor.WHITE, message));
    }

    public static void broadcast(String message, Object... objects) {
        broadcast(String.format(message, objects));
    }
}
