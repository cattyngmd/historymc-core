package ru.historymc.core.player;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import ru.historymc.core.Config;
import ru.historymc.core.utils.Utils;
import ru.historymc.core.player.source.DataSource;
import uk.org.whoami.authme.cache.limbo.LimboCache;

import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public final class PlayerListener implements Listener {
    private final Plugin plugin;
    private final Config config;

    public PlayerListener(Plugin plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String player = event.getPlayer().getName().toLowerCase(Locale.ROOT);
        Path path = plugin.getDataFolder().toPath().resolve("players");
        if (path.resolve( player + ".data").toFile().exists())
            return;

        AtomicReference<Location> location = new AtomicReference<>(null);
        while (location.get() == null) {
            location.set(random(event.getPlayer().getWorld()));
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Location loc = LimboCache.getInstance().getLimboPlayer(player).getLoc();
            loc.setX(location.get().getX());
            loc.setY(location.get().getY());
            loc.setZ(location.get().getZ());
        });
    }

    @EventHandler
    public void onGameJoin(PlayerJoinEvent event) {
        String player = event.getPlayer().getName();
        PlayerExtra extra = getSource().loadPlayer(player);
        if (extra == null) {
            extra = new PlayerExtra(player);
        }

        PlayerStorage.getInstance().add(extra);
        event.getPlayer().setDisplayName(extra.getColor() + player + ChatColor.WHITE);
    }

    @EventHandler
    public void onGameLeft(PlayerQuitEvent event) {
        String player = event.getPlayer().getName();
        PlayerExtra extra = PlayerStorage.getInstance().remove(player);

        if (extra != null) {
            getSource().updatePlayer(extra);
        }

        if (event.getPlayer().isOp()) {
            event.getPlayer().setOp(false);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location location = event.getRespawnLocation();
        if (Math.abs(location.getX()) > 2 || Math.abs(location.getZ()) > 2) return;

        location = null;
        while (location == null) {
            location = random(event.getRespawnLocation().getWorld());
        }
        event.setRespawnLocation(location);
    }

    @EventHandler
    public void onWorldBorder(PlayerMoveEvent event) {
        double x = event.getTo().getX();
        double z = event.getTo().getZ();
        int wb = config.getWorldBorder();
        if (x >= wb || x <= -wb || z >= wb || z <= -wb) {
            event.getTo().setX(Utils.clamp((int) x, -wb + 3, wb - 3));
            event.getTo().setZ(Utils.clamp((int) z, -wb + 3, wb - 3));
        }
    }

    private DataSource getSource() {
        return PlayerStorage.getInstance().getSource();
    }

    private Location random(World world) {
        float factorX = ThreadLocalRandom.current().nextFloat();
        float factorZ = ThreadLocalRandom.current().nextFloat();
        int radius = config.getSpawnRadius();
        int x = (int) (factorX * radius * 2 - radius);
        int z = (int) (factorZ * radius * 2 - radius);

        int maxY = world.getHighestBlockYAt(x, z);

        for (int y = maxY; y > 0; y--) {
            if (isSafe(world, x, y, z))
                return new Location(world, x + 0.5f, y, z + 0.5f);
        }
        return null;
    }

    private boolean isSafe(World world, int x, int y, int z) {
        Block legs = world.getBlockAt(x, y, z);
        Block head = world.getBlockAt(x, y + 1, z);
        if (!empty(legs) || !empty(head)) return false;
        Block ground = world.getBlockAt(x, y - 1, z);
        return solid(ground);
    }

    private boolean empty(Block block) {
        return block.isEmpty() || block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER;
    }

    private boolean solid(Block block) {
        return !block.isEmpty() && !block.isLiquid();
    }
}
