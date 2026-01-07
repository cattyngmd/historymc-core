package ru.historymc.core.utils.events;

import org.bukkit.event.Event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    Event.Type type(); // holy autism

    Event.Priority priority() default Event.Priority.Normal;
}
