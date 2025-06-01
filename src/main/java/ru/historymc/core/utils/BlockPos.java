package ru.historymc.core.utils;

import org.bukkit.Location;
import org.bukkit.World;

public record BlockPos(int x, int y, int z) {
    public static BlockPos ofFloored(double x, double y, double z) {
        return new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    public BlockPos down() {
        return new BlockPos(x, y - 1, z);
    }

    public Location toLoc(World world) {
        return new Location(world, x, y, z);
    }
}
