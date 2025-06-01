package ru.historymc.core.command.impl.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
        if (args.length != 1) {
            throw new InvalidUsageException();
        }

        if (sender instanceof ConsoleCommandSender) {
            onCommandServer(sender, args);
            return;
        }

        if (!(sender instanceof Player)) {
            throw new InvalidUsageException(); // Lol
        }

        PlayerExtra extra = PlayerStorage.getInstance().get(sender);
        if (extra.getColor() == ChatColor.WHITE) {
            throw new CommandException("Nope");
        }

        ChatColor color = getColor(args[0]);

        extra.setColor(color);
        sync(sender);
    }

    private void onCommandServer(CommandSender sender, String[] args) throws CommandException {
        Player player = player(args);
        PlayerExtra extra = PlayerStorage.getInstance().get(player);
        extra.setColor(ChatColor.GREEN);
        sync(player);
    }

    private ChatColor getColor(String arg) throws CommandException {
        try {
            ChatColor color = ChatColor.valueOf(arg.toUpperCase(Locale.ROOT));
            if (color == ChatColor.WHITE) throw new IllegalArgumentException();
            return color;
        } catch (IllegalArgumentException e) {
            throw new CommandException("Invalid color provided");
        }
    }

    private void sync(CommandSender entity) {
        if (entity instanceof Player player) {
            player.kickPlayer(ChatColor.YELLOW + ":^)");
        }
    }
}
