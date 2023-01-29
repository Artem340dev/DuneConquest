package org.millida.duneconquest.commands;

import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.configuration.ItemsConfiguration;
import org.millida.duneconquest.objects.DuneConquestItem;
import org.millida.duneconquest.utils.ChatUtil;
import org.millida.duneconquest.utils.ItemUtil;
import space.arim.dazzleconf.annote.SubSection;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class DuneConquestCommand extends AbstractCommand {
    public DuneConquestCommand() {
        super("duneconquest", "duneconquest.duneconquest", "У вас нет прав duneconquest.duneconquest!");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 2) {
            if (args[0].equals("get")) {
                String id = args[1];
                Optional<DuneConquestItem> item = DuneConquestItem.findItemOrNullById(id);

                if (!item.isPresent()) {
                    player.sendMessage(ChatUtil.parseColor(DuneConquestPlugin.PREFIX + "&cПредмет не найден!"));
                    return;
                }

                player.getInventory().addItem(item.get().getBukkitItem());
                player.sendMessage(ChatUtil.parseColor(DuneConquestPlugin.PREFIX + "Предмет успешно был выдан вам в руки!"));
                return;
            }
        }

        this.help(player);
    }

    @Override
    protected void help(Player player) {
        player.sendMessage(ChatUtil.parseColor(DuneConquestPlugin.PREFIX + "/duneconquest get [название предмета] - получить предмет."));
    }
}