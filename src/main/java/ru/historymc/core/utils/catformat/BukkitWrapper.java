package ru.historymc.core.utils.catformat;

import dev.cattyn.catformat.text.TextWrapper;
import org.bukkit.ChatColor;

public class BukkitWrapper implements TextWrapper<String> {
    @Override
    public String colored(String text, int color) {
        ChatColor formatting = ChatColor.getByCode(color);
        if (formatting == null) return text;
        return formatting + text;
    }

    @Override
    public String concat(String text, String text2) {
        return text + text2;
    }

    @Override
    public String modify(String text, int modifiers) {
        return text;
    }

    @Override
    public String newText(String content) {
        return content;
    }
}
