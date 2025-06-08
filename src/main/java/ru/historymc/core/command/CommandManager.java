package ru.historymc.core.command;

import org.bukkit.command.PluginCommand;
import ru.historymc.core.Main;
import ru.historymc.core.command.impl.misc.JoinCommand;
import ru.historymc.core.command.impl.misc.KillCommand;
import ru.historymc.core.command.impl.misc.NameColorCommand;
import ru.historymc.core.command.impl.server.HelpCommand;
import ru.historymc.core.command.impl.server.ListCommand;
import ru.historymc.core.command.impl.social.IgnoreCommand;
import ru.historymc.core.command.impl.social.ReplyCommand;
import ru.historymc.core.command.impl.social.TownCommand;
import ru.historymc.core.command.impl.social.WhisperCommand;

import java.util.ArrayList;
import java.util.List;

public final class CommandManager {
    private final List<Command> regisry = new ArrayList<>();
    private final Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        register(new ListCommand(plugin));
        register(new KillCommand(plugin));
        register(new JoinCommand(plugin));
        register(new NameColorCommand(plugin));
        register(new HelpCommand(plugin));
        register(new TownCommand(plugin));
        register(new WhisperCommand(plugin));
        register(new ReplyCommand(plugin));
        register(new IgnoreCommand(plugin));
    }

    private void register(Command command) {
        PluginCommand cmd = plugin.getCommand(command.getName());
        cmd.setExecutor(command);
        command.usage(cmd.getUsage());
        regisry.add(command);
    }

    public List<Command> getRegistry() {
        return regisry;
    }
}
