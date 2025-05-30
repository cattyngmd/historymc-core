package ru.historymc.core;

import dev.cattyn.catformat.CatFormat;
import org.yaml.snakeyaml.Yaml;
import ru.historymc.core.utils.catformat.BukkitCatFormat;

public interface Globals {
    CatFormat<String> formatter = new BukkitCatFormat();
    Yaml yaml = new Yaml();
}
