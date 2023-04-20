package org.millida.duneconquest;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.millida.duneconquest.commands.DuneConquestCommand;
import org.millida.duneconquest.configuration.GroupsConfiguration;
import org.millida.duneconquest.configuration.MainConfiguration;
import org.millida.duneconquest.handlers.PlayerHandler;
import org.millida.duneconquest.utils.ChatUtil;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DuneConquestPlugin extends JavaPlugin {
    public static final String PREFIX = "&9&lDuneConquest &8>> &f";

    @Getter
    static DuneConquestPlugin instance;

    @Getter
    GroupsConfiguration groupsConfiguration;

    @Getter
    MainConfiguration mainConfiguration;

    public void onEnable() {
        instance = this;

        this.groupsConfiguration = GroupsConfiguration.builder().build();
        groupsConfiguration.enable();

        this.mainConfiguration = MainConfiguration.builder().build();
        mainConfiguration.enable();

        DuneConquestCommand.builder().build().register();
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);

        this.getLogger().info(ChatUtil.parseColor("&aIs an actual version for 17.04.2023"));
    }

    public void onDisable() {
        groupsConfiguration.disable();
    }
}
