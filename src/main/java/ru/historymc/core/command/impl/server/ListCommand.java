package ru.historymc.core.command.impl.server;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;

// мне похуй что она уже есть в ванилле
public final class ListCommand extends Command {
    public ListCommand(Main plugin) {
        super(plugin, "list");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        int online = players().length;

        StringBuilder sb = new StringBuilder();
        sb.append("Online [");
        sb.append(highlight(String.valueOf(online)));
        sb.append("]: ");

        for (int i = 0; i < online; i++) {
            Player player = players()[i];
            sb.append(player.getName());
            if (i != online - 1) {
                sb.append(", ");
            } else {
                sb.append(".");
            }
        }

        sender.sendMessage(sb.toString());
    }

    private Player[] players() {
        return Bukkit.getServer().getOnlinePlayers();
    }
}
