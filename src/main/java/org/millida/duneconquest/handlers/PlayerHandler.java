package org.millida.duneconquest.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.objects.DuneConquestItem;
import org.millida.duneconquest.objects.DuneConquestItemGroup;
import org.millida.duneconquest.threads.DuneEquipGroupThread;
import org.millida.duneconquest.threads.PlayerEquipGroupTrackThread;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerHandler implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PlayerEquipGroupTrackThread.builder().player(event.getPlayer()).build().start(DuneConquestPlugin.getInstance());
    }

    @EventHandler
    public void onRepair(InventoryClickEvent event) {
        if (!(event instanceof AnvilInventory)) return;

        AnvilInventory inventory = (AnvilInventory) event.getInventory();
        if (inventory.getResult() == null) return;

        Optional<DuneConquestItem> itemOptional = DuneConquestItemGroup.findItemOrNullByBukkitItem(inventory.getResult());
        if (!itemOptional.isPresent()) return;

        inventory.setRepairCost(itemOptional.get().getBukkitItem().getType().getMaxDurability() - itemOptional.get().getBukkitItem().getDurability());
        inventory.setMaximumRepairCost(itemOptional.get().getBukkitItem().getType().getMaxDurability() - itemOptional.get().getBukkitItem().getDurability());
    }
}