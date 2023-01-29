package org.millida.duneconquest.handlers;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.millida.duneconquest.objects.DuneConquestItem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlayerHandler implements Listener {
    @EventHandler
    public void onPutOnArmor(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        List<ItemStack> armorContents = Arrays.asList(player.getInventory().getArmorContents());

        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        armorContents.stream().filter(item -> item != null).forEach(bukkitItem -> {
            Optional<DuneConquestItem> item = DuneConquestItem.findItemOrNullByItem(bukkitItem);
            if (!item.isPresent()) return;

            player.addPotionEffects(item.get().getEffects());
        });
    }
}