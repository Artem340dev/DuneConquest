package org.millida.duneconquest.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.millida.duneconquest.DuneConquestPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractConfiguration {
    @Getter
    FileConfiguration configuration;

    String name;
    File file;

    public AbstractConfiguration(String name) {
        this.name = name;
        this.file = new File(DuneConquestPlugin.getInstance().getDataFolder(), name);
    }

    protected void createConfig() {
        if (!file.exists()) {
            DuneConquestPlugin.getInstance().saveResource(name, false);
        }

        Reader configStream = new InputStreamReader(DuneConquestPlugin.getInstance().getResource(name));
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(configStream);

        this.configuration = YamlConfiguration.loadConfiguration(file);
        configuration.setDefaults(defaultConfig);
    }

    protected void saveConfig() {
        try {
            configuration.save(file);
        } catch (Exception e) {
        }
    }

    public abstract void enable();
    public abstract void disable();
}