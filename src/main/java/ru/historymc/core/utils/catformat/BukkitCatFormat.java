package ru.historymc.core.utils.catformat;

import dev.cattyn.catformat.CatFormatImpl;
import org.bukkit.ChatColor;

import java.util.Locale;

public class BukkitCatFormat extends CatFormatImpl<String> {
    public BukkitCatFormat() {
        super(new BukkitWrapper());
        for (ChatColor color : ChatColor.values()) {
            add(color.name().toLowerCase(Locale.ROOT), color.getCode());
        }
    }
}
