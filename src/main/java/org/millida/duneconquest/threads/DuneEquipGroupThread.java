package org.millida.duneconquest.threads;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.millida.duneconquest.objects.DuneConquestItemGroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DuneEquipGroupThread extends AbstractThread {
    static HashMap<UUID, DuneEquipGroupThread> threads = new HashMap<>();

    Player player;
    DuneConquestItemGroup group;

    @Builder
    public DuneEquipGroupThread(Player player, DuneConquestItemGroup group) {
        super(1);
        this.player = player;
        this.group = group;
    }

    @Override
    public void start(JavaPlugin plugin) {
        player.addPotionEffects(group.getEffects());

        threads.put(player.getUniqueId(), this);
        super.start(plugin);
    }

    @Override
    public void stop() {
        group.getEffects()
                .stream()
                .map(effect -> effect.getType())
                .filter(effectType -> player.hasPotionEffect(effectType))
                .forEach(effectType -> player.removePotionEffect(effectType));

        threads.remove(player.getUniqueId(), this);
        super.stop();
    }

    @Override
    public void run() {
        if (!player.isOnline() || !group.hasGroup(player.getInventory())) {
            this.stop();
        }
    }

    public static DuneEquipGroupThread findThreadOrNullByPlayer(Player player) {
        return threads.get(player.getUniqueId());
    }
}