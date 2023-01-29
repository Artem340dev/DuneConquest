package org.millida.duneconquest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.millida.duneconquest.commands.DuneConquestCommand;
import org.millida.duneconquest.configuration.ItemsConfiguration;
import org.millida.duneconquest.handlers.PlayerHandler;

import java.util.HashMap;

public class DuneConquestPlugin extends JavaPlugin {
    public static final String PREFIX = "&9&lDuneConquest &8>> &f";

    @Getter
    private static DuneConquestPlugin instance;

    @Getter
    private ItemsConfiguration itemsConfiguration;

    @Override
    public void onEnable() {
        instance = this;

        this.itemsConfiguration = new ItemsConfiguration();
        itemsConfiguration.enable();

        new DuneConquestCommand().register();
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
    }

    @Override
    public void onDisable() {
        itemsConfiguration.disable();
    }
}
