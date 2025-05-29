package ru.historymc.core.player;

import com.google.gson.annotations.SerializedName;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// k-k-krov' na moih glockahhhh
public final class PlayerExtra {
    @SerializedName("name") private final String name;
    @SerializedName("town") private String town;
    @SerializedName("chatColor") private ChatColor color = ChatColor.WHITE;

    @SerializedName("ignore")
    private final List<String> ignoreList = new ArrayList<>();

    private String lastSender;

    public PlayerExtra(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getLastSender() {
        return lastSender;
    }

    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
    }

    public List<String> getIgnoreList() {
        return ignoreList;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public boolean isIgnoring(CommandSender player) {
        return ignoreList.contains(player.getName().toLowerCase(Locale.ROOT));
    }
}
