package org.millida.duneconquest.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.millida.duneconquest.DuneConquestPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class AbstractConfiguration {
    private FileConfiguration configuration;
    private String name;
    private File file;

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

    protected FileConfiguration getConfiguration() {
        return configuration;
    }

    public abstract void enable();
    public abstract void disable();
}