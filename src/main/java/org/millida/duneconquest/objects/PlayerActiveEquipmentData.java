package org.millida.duneconquest.objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerActiveEquipmentData implements RegisteredObject {
    static HashMap<UUID, PlayerActiveEquipmentData> data = new HashMap<>();

    @Getter
    Player player;

    @Getter
    DuneConquestItemGroup group;

    @Override
    public void register() {
        data.put(player.getUniqueId(), this);
    }

    @Override
    public void unregister() {
        data.remove(player.getUniqueId());
    }

    public static Optional<PlayerActiveEquipmentData> findEquipmentDataOrNullByPlayer(Player player) {
        return data.values().stream().filter(data -> data.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst();
    }
}