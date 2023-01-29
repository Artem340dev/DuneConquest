package org.millida.duneconquest.configuration;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.millida.duneconquest.DuneConquestPlugin;
import org.millida.duneconquest.objects.DuneConquestItem;
import org.millida.duneconquest.utils.ItemUtil;

import java.util.*;

public class ItemsConfiguration extends AbstractConfiguration {
    public ItemsConfiguration() {
        super("items.yml");
    }

    @Override
    public void enable() {
        this.createConfig();

        for (String id : this.getConfiguration().getConfigurationSection("items").getKeys(false)) {
            String name = this.getConfiguration().getString("items." + id + ".name");
            List<String> lore = this.getConfiguration().getStringList("items." + id + ".lore");
            Material material = Material.valueOf(this.getConfiguration().getString("items." + id + ".material"));
            int customModelData = this.getConfiguration().getInt("items." + id + ".customModelData");
            int durability = this.getConfiguration().getInt("items." + id + ".durability");

            HashMap<Enchantment, Integer> enchantments = new HashMap<>();
            List<PotionEffect> effects = new ArrayList();

            for (String enchantment : this.getConfiguration().getConfigurationSection("items." + id + ".enchantments").getKeys(false)) {
                int level = this.getConfiguration().getInt("items." + id + ".enchantments." + enchantment);
                enchantments.put(Enchantment.getByName(enchantment), level);
            }

            for (String effectName : this.getConfiguration().getConfigurationSection("items." + id + ".effects").getKeys(false)) {
                int amplifier = this.getConfiguration().getInt("items." + id + ".effects." + effectName);
                effects.add(new PotionEffect(PotionEffectType.getByName(effectName), Integer.MAX_VALUE, amplifier));
            }

            ItemStack item = ItemUtil.getItem(name, lore, Arrays.asList(ItemFlag.HIDE_ENCHANTS), material, enchantments, customModelData, (short) durability);
            DuneConquestItem.builder().id(id).bukkitItem(item).effects(effects).build().register();
        }
    }

    @Override
    public void disable() {
        this.saveConfig();
    }
}