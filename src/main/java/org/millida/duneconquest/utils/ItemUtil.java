package org.millida.duneconquest.utils;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class ItemUtil {
    public static ItemStack getItem(String name, List<String> lore, Map<Attribute, AttributeModifier> attributes, Map<String, String> tags, Material material, int customModelData, int durability) {
        ItemStack item = setNBTs(new ItemStack(material), tags);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatUtil.parseColor(name));
        meta.setLore(ChatUtil.parseColor(lore));
        meta.setCustomModelData(customModelData);
        attributes.forEach(meta::addAttributeModifier);
        ((Damageable) meta).setDamage((int)item.getType().getMaxDurability() - durability);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack setNBTs(ItemStack item, Map<String, String> tags) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        NBTTagCompound tagCompound = new NBTTagCompound();
        tags.forEach(tagCompound::setString);

        nmsItem.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
}