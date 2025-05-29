package ru.historymc.core;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.historymc.core.chat.Chat;
import ru.historymc.core.command.CommandManager;
import ru.historymc.core.deathmessages.DeathListener;
import ru.historymc.core.deathmessages.DeathMessages;
import ru.historymc.core.illegals.IllegalsListener;
import ru.historymc.core.player.PlayerListener;
import ru.historymc.core.player.PlayerStorage;
import ru.historymc.core.tasks.TaskManager;
import ru.historymc.core.utils.BlockPos;
import ru.historymc.core.utils.ConfigFile;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    private final CommandManager commands = new CommandManager(this);
    private final TaskManager tasks = new TaskManager(this);
    private final Chat chat = new Chat(this);

    private Config config;
    private DeathMessages messages;

    @Override
    public void onEnable() {
        config = new Config(new File(getDataFolder(), "config.yml"));

        try {
            messages = new DeathMessages(new ConfigFile(this, "deathmessages.yml"));
        } catch (IOException e) {
            Bukkit.getServer().shutdown();
            throw new RuntimeException(e);
        }

        PlayerStorage.getInstance().getSource().setup(this);
        tasks.init();
        chat.init();
        commands.init();

        register(new DeathListener(messages));
        register(new IllegalsListener());
        register(new PlayerListener(config));

        for (World world : getServer().getWorlds()) {
            BlockPos pos = config.getSpawn();
            world.setSpawnLocation(pos.x(), pos.y(), pos.z());
        }
    }

    @Override
    public void onDisable() {

    }

    private void register(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public Chat getChat() {
        return chat;
    }

    public DeathMessages getMessages() {
        return messages;
    }

    public Config getConfig() {
        return config;
    }
}
