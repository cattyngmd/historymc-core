package ru.historymc.core.command.impl.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.command.exception.InvalidUsageException;
import ru.historymc.core.player.PlayerExtra;
import ru.historymc.core.player.PlayerStorage;

import java.util.Locale;

public final class NameColorCommand extends Command {
    public NameColorCommand(Main plugin) {
        super(plugin, "namecolor");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) {
            throw new InvalidUsageException();
        }

        Player player = player(args);
        PlayerExtra extra = PlayerStorage.getInstance().get(player);

        try {
            ChatColor color = ChatColor.valueOf(args[1].toUpperCase(Locale.ROOT));
            extra.setColor(color);
            player.kickPlayer(ChatColor.YELLOW + ":^)");
        } catch (Exception e) {
            throw new CommandException("Invalid color provided.");
        }
    }
}
