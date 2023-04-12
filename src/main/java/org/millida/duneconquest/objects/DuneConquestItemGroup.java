package org.millida.duneconquest.objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
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
        if (inventory.getHelmet() == null || inventory.getChestplate() == null || inventory.getLeggings() == null || inventory.getBoots() == null) return false;

        net.minecraft.world.item.ItemStack inventoryHelmet = CraftItemStack.asNMSCopy(inventory.getHelmet().clone());
        if (!inventoryHelmet.hasTag() || !inventoryHelmet.getTag().hasKey("duneGroupName") || !inventoryHelmet.getTag().getString("duneGroupName").equals(groupId)) return false;

        net.minecraft.world.item.ItemStack inventoryChestplate = CraftItemStack.asNMSCopy(inventory.getChestplate().clone());
        if (!inventoryChestplate.hasTag() || !inventoryChestplate.getTag().hasKey("duneGroupName") || !inventoryChestplate.getTag().getString("duneGroupName").equals(groupId)) return false;

        net.minecraft.world.item.ItemStack inventoryLeggings = CraftItemStack.asNMSCopy(inventory.getLeggings().clone());
        if (!inventoryLeggings.hasTag() || !inventoryLeggings.getTag().hasKey("duneGroupName") || !inventoryLeggings.getTag().getString("duneGroupName").equals(groupId)) return false;

        net.minecraft.world.item.ItemStack inventoryBoots = CraftItemStack.asNMSCopy(inventory.getBoots().clone());
        if (!inventoryBoots.hasTag() || !inventoryBoots.getTag().hasKey("duneGroupName") || !inventoryBoots.getTag().getString("duneGroupName").equals(groupId)) return false;

        return true;
    }

    public Optional<DuneConquestItem> findItemOrNullByItemId(String itemId) {
        return items.values()
                .stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst();
    }

    public Optional<DuneConquestItem> findItemOrNullByItemType(DuneConquestItem.DuneConquestItemType type) {
        return items.values()
                .stream()
                .filter(item -> item.getType() == type)
                .findFirst();
    }

    public static Optional<DuneConquestItemGroup> findGroupOrNullByItemId(String itemId) {
        return groups.values()
                .stream()
                .filter(group -> group.findItemOrNullByItemId(itemId).isPresent())
                .findFirst();
    }

    public static Optional<DuneConquestItemGroup> findGroupOrNullByPlayer(Player player) {
        return groups.values()
                .stream()
                .filter(group -> group.hasGroup(player.getInventory()))
                .findFirst();
    }

    public static Optional<DuneConquestItem> findItemOrNullByBukkitItem(ItemStack item) {
        return groups.values()
                .stream()
                .filter(group -> CraftItemStack.asNMSCopy(item).hasTag())
                .filter(group -> CraftItemStack.asNMSCopy(item).getTag().hasKey("duneGroupName"))
                .filter(group -> CraftItemStack.asNMSCopy(item).getTag().hasKey("duneType"))
                .filter(group -> CraftItemStack.asNMSCopy(item).getTag().getString("duneGroupName").equals(group.getGroupId()))
                .map(group -> group.getItems().values())
                .flatMap(Collection::stream)
                .filter(duneItem -> duneItem.getType().equals(DuneConquestItem.DuneConquestItemType.valueOf(CraftItemStack.asNMSCopy(item).getTag().getString("duneType"))))
                .findFirst();
    }

    public static Collection<DuneConquestItemGroup> findAllGroups() {
        return groups.values();
    }
}