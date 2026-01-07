package ru.historymc.core.utils.events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class EventHelper {
    private EventHelper() {
    }

    public static void register(Plugin plugin, Listener listener) {
        PluginManager manager = plugin.getServer().getPluginManager();
        for (Method method : listener.getClass().getDeclaredMethods()) {
            EventHandler handler = method.getAnnotation(EventHandler.class);
            Class<?>[] params = method.getParameterTypes();

            if (handler == null) continue;
            if (params.length != 1) continue;
            if (Modifier.isStatic(method.getModifiers())) continue;

            if (!method.isAccessible()) method.setAccessible(true);
            EventExecutor executor = createExecutor(method, params);
            manager.registerEvent(handler.type(), listener, executor, handler.priority(), plugin);
        }
    }

    private static EventExecutor createExecutor(Method method, Class<?>[] params) {
        return (localListener, event) -> {
            if (!params[0].isInstance(event)) return;
            safeCall(method, localListener, event);
        };
    }

    private static void safeCall(Method method, Object o, Object... args) {
        try {
            method.invoke(o, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
