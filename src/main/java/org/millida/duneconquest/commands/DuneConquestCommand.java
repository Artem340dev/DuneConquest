package org.millida.duneconquest.commands;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.objects.DuneConquestItem;
import org.millida.duneconquest.objects.DuneConquestItemGroup;
import org.millida.duneconquest.utils.ChatUtil;

import java.util.Optional;

public class DuneConquestCommand extends AbstractCommand {
    @Builder
    public DuneConquestCommand() {
        super("duneconquest", "duneconquest.duneconquest", "У вас нет прав duneconquest.duneconquest!");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 2) {
            if (args[0].equals("get")) {
                String itemId = args[1];
                Optional<DuneConquestItemGroup> group = DuneConquestItemGroup.findGroupOrNullByItemId(itemId);

                if (!group.isPresent()) {
                    player.sendMessage(ChatUtil.parseColor(DuneConquestPlugin.PREFIX + "&cПредмет не найден!"));
                    return;
                }

                Optional<DuneConquestItem> item = group.get().findItemOrNullByItemId(itemId);

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