package org.millida.duneconquest.objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DuneConquestItemGroup implements RegisteredObject {
    static HashMap<String, DuneConquestItemGroup> groups = new HashMap<>();

    @Getter
    String groupId;

    @Getter
    HashMap<String, DuneConquestItem> items;

    @Getter
    @Builder.Default
    List<PotionEffect> effects = new ArrayList<>();

    @Override
    public void register() {
        groups.put(groupId, this);
    }

    @Override
    public void unregister() {
        groups.remove(groupId);
    }

    public boolean hasGroup(PlayerInventory inventory) {
        ItemStack helmet = this.findItemOrNullByItemType(DuneConquestItem.DuneConquestItemType.HELMET).get().getBukkitItem();
        ItemStack chestplate = this.findItemOrNullByItemType(DuneConquestItem.DuneConquestItemType.CHESTPLATE).get().getBukkitItem();
        ItemStack leggings = this.findItemOrNullByItemType(DuneConquestItem.DuneConquestItemType.LEGGINGS).get().getBukkitItem();
        ItemStack boots = this.findItemOrNullByItemType(DuneConquestItem.DuneConquestItemType.BOOTS).get().getBukkitItem();

        if (helmet.isSimilar(inventory.getHelmet()) && chestplate.isSimilar(inventory.getChestplate()) && leggings.isSimilar(inventory.getLeggings()) && boots.isSimilar(inventory.getBoots())) {
            return true;
        }

        return false;
    }

    public Optional<DuneConquestItem> findItemOrNullByItemId(String itemId) {
        return items.values().stream().filter(item -> item.getItemId().equals(itemId)).findFirst();
    }

    public Optional<DuneConquestItem> findItemOrNullByItemType(DuneConquestItem.DuneConquestItemType type) {
        return items.values().stream().filter(item -> item.getType() == type).findFirst();
    }

    public static Optional<DuneConquestItemGroup> findGroupOrNullByItemId(String itemId) {
        return groups.values().stream().filter(group -> group.findItemOrNullByItemId(itemId).isPresent()).findFirst();
    }

    public static Optional<DuneConquestItemGroup> findGroupOrNullByPlayer(Player player) {
        return groups.values().stream().filter(group -> group.hasGroup(player.getInventory())).findFirst();
    }

    public static Collection<DuneConquestItemGroup> findAllGroups() {
        return groups.values();
    }
}