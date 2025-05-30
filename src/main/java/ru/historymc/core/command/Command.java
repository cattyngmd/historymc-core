package ru.historymc.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.historymc.core.Main;
import ru.historymc.core.command.exception.CommandException;
import ru.historymc.core.command.exception.InvalidUsageException;
import ru.historymc.core.command.exception.NoPlayerFoundException;

public abstract class Command implements CommandExecutor {
    protected final Main plugin;
    private final String name;
    private String usage;

    public Command(Main plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.usage = ChatColor.RED + "/" + name;
    }

    public abstract void onCommand(CommandSender sender, String[] args) throws CommandException;

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        try {
            onCommand(commandSender, strings);
        } catch (InvalidUsageException e) {
            sendUsage(commandSender);
        } catch (CommandException e) {
            commandSender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public void usage(String usage) {
        this.usage = ChatColor.RED + usage;
    }

    protected String highlight(String message) {
        return ChatColor.RED + message + ChatColor.WHITE;
    }

    protected void sendUsage(CommandSender sender) {
        sender.sendMessage(this.usage);
    }

    protected Player player(String[] args) throws CommandException {
        if (args.length == 0) {
            throw new InvalidUsageException();
        }

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            throw new NoPlayerFoundException(args[0]);
        }

        return player;
    }

    public String getName() {
        return name;
    }
}
