package org.millida.duneconquest;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.millida.duneconquest.commands.DuneConquestCommand;
import org.millida.duneconquest.configuration.GroupsConfiguration;
import org.millida.duneconquest.handlers.PlayerHandler;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DuneConquestPlugin extends JavaPlugin {
    public static final String PREFIX = "&9&lDuneConquest &8>> &f";

    @Getter
    static DuneConquestPlugin instance;

    @Getter
    Plugin plugin;

    @Getter
    GroupsConfiguration groupsConfiguration;

    public void onEnable() {
        instance = this;

        this.groupsConfiguration = new GroupsConfiguration();
        groupsConfiguration.enable();

        new DuneConquestCommand().register();
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
    }

    public void onDisable() {
        groupsConfiguration.disable();
    }
}
