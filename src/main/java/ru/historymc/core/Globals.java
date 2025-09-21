package ru.historymc.core;

import dev.cattyn.catformat.CatFormat;
import org.yaml.snakeyaml.Yaml;
import ru.historymc.core.utils.catformat.BukkitCatFormat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface Globals {
    ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    CatFormat<String> formatter = new BukkitCatFormat();
    Yaml yaml = new Yaml();
}
