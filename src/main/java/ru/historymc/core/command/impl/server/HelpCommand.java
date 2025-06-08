package ru.historymc.core.command.impl.server;

import org.bukkit.command.CommandSender;
import ru.historymc.core.Main;
import ru.historymc.core.command.Command;
import ru.historymc.core.command.exception.CommandException;

public final class HelpCommand extends Command {
    public HelpCommand(Main plugin) {
        super(plugin, "help");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        sender.sendMessage("Server commands: ");
        for (Command cmd : plugin.getCommands().getRegistry()) {
            sender.sendMessage(plugin.getCommand(cmd.getName()).getUsage());
        }
    }
}
