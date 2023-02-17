package org.millida.duneconquest.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.millida.duneconquest.handlers.events.ArmorEquipEvent;
import org.millida.duneconquest.objects.PlayerActiveEquipmentData;
import org.millida.duneconquest.objects.DuneConquestItemGroup;

import java.util.Optional;

public class PlayerHandler implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Optional<DuneConquestItemGroup> groupOptional = DuneConquestItemGroup.findGroupOrNullByPlayer(event.getPlayer());
        Optional<PlayerActiveEquipmentData> equipmentDataOptional = PlayerActiveEquipmentData.findEquipmentDataOrNullByPlayer(event.getPlayer());

        this.callArmorEquipEvent(event.getPlayer(), groupOptional, equipmentDataOptional);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Optional<DuneConquestItemGroup> groupOptional = DuneConquestItemGroup.findGroupOrNullByPlayer(player);
        Optional<PlayerActiveEquipmentData> equipmentDataOptional = PlayerActiveEquipmentData.findEquipmentDataOrNullByPlayer(player);

        this.callArmorEquipEvent(player, groupOptional, equipmentDataOptional);
    }

    @EventHandler
    public void onEquipArmor(ArmorEquipEvent event) {
        if (event.getAction().equals(ArmorEquipEvent.EquipmentAction.TAKE_OFF)) {
            event.getGroup().getEffects().forEach(potionEffect -> {
                if (event.getPlayer().hasPotionEffect(potionEffect.getType())) {
                    event.getPlayer().removePotionEffect(potionEffect.getType());
                }
            });
        }

        if (event.getAction().equals(ArmorEquipEvent.EquipmentAction.PUT_ON)) {
            event.getPlayer().addPotionEffects(event.getGroup().getEffects());
        }
    }

    private void callArmorEquipEvent(Player player, Optional<DuneConquestItemGroup> groupOptional, Optional<PlayerActiveEquipmentData> activeEquipmentDataOptional) {
        if (!groupOptional.isPresent() && activeEquipmentDataOptional.isPresent()) {
            activeEquipmentDataOptional.get().unregister();

            Bukkit.getPluginManager().callEvent(ArmorEquipEvent.builder()
                    .player(player)
                    .group(activeEquipmentDataOptional.get().getGroup())
                    .action(ArmorEquipEvent.EquipmentAction.TAKE_OFF)
                    .build());
        }

        if (!activeEquipmentDataOptional.isPresent() && groupOptional.isPresent()) {
            PlayerActiveEquipmentData.builder()
                    .player(player)
                    .group(groupOptional.get())
                    .build()
                    .register();

            Bukkit.getPluginManager().callEvent(ArmorEquipEvent.builder()
                    .player(player)
                    .group(groupOptional.get())
                    .action(ArmorEquipEvent.EquipmentAction.PUT_ON)
                    .build());
        }
    }
}