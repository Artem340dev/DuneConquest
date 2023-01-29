package org.millida.duneconquest.objects;

import com.google.common.collect.HashBiMap;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.commands.DuneConquestCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DuneConquestItem {
    private static List<DuneConquestItem> items = new ArrayList<>();

    @Getter
    String id;

    @Getter
    ItemStack bukkitItem;

    @Getter
    @Builder.Default
    List<PotionEffect> effects = new ArrayList<>();

    public void register() {
        items.add(this);
    }

    public static Optional<DuneConquestItem> findItemOrNullById(String id) {
        return items.stream().filter(object -> object.getId().equals(id)).findFirst();
    }

    public static Optional<DuneConquestItem> findItemOrNullByItem(ItemStack item) {
        return items.stream().filter(object -> object.getBukkitItem().isSimilar(item)).findFirst();
    }
}