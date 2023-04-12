package org.millida.duneconquest.commands;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.utils.ChatUtil;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class AbstractCommand implements CommandExecutor {
    @Getter
    String command, permission;
    
    String permissionMessage;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command doesn't work in console!");
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(permission)) {
            player.sendMessage(ChatUtil.parseColor(permissionMessage));
            return true;
        }

        this.execute(player, args);
        return true;
    }

    public void register() {
        DuneConquestPlugin.getInstance().getCommand(command).setExecutor(this);
    }

    protected abstract void execute(Player player, String[] args);
    protected abstract void help(Player player);
}