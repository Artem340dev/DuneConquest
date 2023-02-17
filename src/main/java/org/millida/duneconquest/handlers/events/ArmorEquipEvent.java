package org.millida.duneconquest.handlers.events;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.millida.duneconquest.objects.DuneConquestItemGroup;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArmorEquipEvent extends PlayerEvent {
    @Getter
    static HandlerList handlerList = new HandlerList();

    @Getter
    DuneConquestItemGroup group;

    @Getter
    EquipmentAction action;

    @Builder
    public ArmorEquipEvent(Player player, DuneConquestItemGroup group, EquipmentAction action) {
        super(player);
        this.group = group;
        this.action = action;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public enum EquipmentAction {
        PUT_ON, TAKE_OFF;
    }
}