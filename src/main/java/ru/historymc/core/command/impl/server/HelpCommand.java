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
        send(sender, "Server commands: ");
        for (Command cmd : plugin.getCommands().getRegistry()) {
            send(sender, plugin.getCommand(cmd.getName()).getUsage());
        }
    }
}
