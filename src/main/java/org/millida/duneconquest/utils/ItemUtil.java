package org.millida.duneconquest.utils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {
    public static ItemStack getItem(String name, List<String> lore, Map<Attribute, AttributeModifier> attributes, Material material, int customModelData, short durability) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setCustomModelData(customModelData);
        meta.setDisplayName(ChatUtil.parseColor(name));
        meta.setLore(ChatUtil.parseColor(lore));

        attributes.forEach(meta::addAttributeModifier);
        item.setDurability(durability);

        item.setItemMeta(meta);
        return item;
    }
}