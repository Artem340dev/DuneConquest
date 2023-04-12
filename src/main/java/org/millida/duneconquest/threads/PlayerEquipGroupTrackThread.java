package org.millida.duneconquest.threads;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.objects.DuneConquestItem;
import org.millida.duneconquest.objects.DuneConquestItemGroup;

import java.util.Arrays;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerEquipGroupTrackThread extends AbstractThread {
    Player player;

    @Builder
    public PlayerEquipGroupTrackThread(Player player) {
        super(1);
        this.player = player;
    }

    @Override
    public void run() {
        if (!player.isOnline()) this.stop();

        Arrays.asList(player.getInventory().getContents()).stream()
                .filter(item -> item != null)
                .filter(item -> !item.getType().equals(Material.AIR))
                .filter(item -> DuneConquestItemGroup.findItemOrNullByBukkitItem(item).isPresent())
                .filter(item -> (item.getDurability() - DuneConquestItemGroup.findItemOrNullByBukkitItem(item).get().getBukkitItem().getDurability()) < 0)
                .forEach(item -> item.setDurability(DuneConquestItemGroup.findItemOrNullByBukkitItem(item).get().getBukkitItem().getDurability()));

        Optional<DuneConquestItemGroup> groupOptional = DuneConquestItemGroup.findGroupOrNullByPlayer(player);

        if (!groupOptional.isPresent()) return;
        if (DuneEquipGroupThread.findThreadOrNullByPlayer(player) != null) return;

        DuneEquipGroupThread.builder().group(groupOptional.get()).player(player).build().start(DuneConquestPlugin.getInstance());
    }
}