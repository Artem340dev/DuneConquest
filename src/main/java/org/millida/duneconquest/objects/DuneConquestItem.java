package org.millida.duneconquest.objects;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.commands.DuneConquestCommand;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DuneConquestItem {
    @Getter
    String itemId;

    @Getter
    ItemStack bukkitItem;

    @Getter
    DuneConquestItemType type;

    public enum DuneConquestItemType {
        HELMET, CHESTPLATE, BOOTS, LEGGINGS;
    }
}