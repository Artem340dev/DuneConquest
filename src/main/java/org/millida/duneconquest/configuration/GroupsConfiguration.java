package org.millida.duneconquest.configuration;

import lombok.Builder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.millida.duneconquest.objects.DuneConquestItem;
import org.millida.duneconquest.objects.DuneConquestItemGroup;
import org.millida.duneconquest.utils.ItemUtil;

import java.util.*;

public class GroupsConfiguration extends AbstractConfiguration {
    @Builder
    public GroupsConfiguration() {
        super("groups.yml");
    }

    @Override
    public void enable() {
        this.createConfig();

        for (String groupId : this.getConfiguration().getConfigurationSection("groups").getKeys(false)) {
            HashMap<String, DuneConquestItem> items = new HashMap<>();
            List<PotionEffect> effects = new ArrayList<>();

            for (String effectName : this.getConfiguration().getConfigurationSection("groups." + groupId + ".effects").getKeys(false)) {
                int amplifier = this.getConfiguration().getInt("groups." + groupId + ".effects." + effectName);
                effects.add(new PotionEffect(PotionEffectType.getByName(effectName), Integer.MAX_VALUE, amplifier));
            }

            for (String itemId : this.getConfiguration().getConfigurationSection("groups." + groupId + ".content").getKeys(false)) {
                String name = this.getConfiguration().getString("groups." + groupId + ".content." + itemId + ".name");
                List<String> lore = this.getConfiguration().getStringList("groups." + groupId + ".content." + itemId + ".lore");
                Material material = Material.valueOf(this.getConfiguration().getString("groups." + groupId + ".content." + itemId + ".material"));
                DuneConquestItem.DuneConquestItemType itemType = DuneConquestItem.DuneConquestItemType.valueOf(this.getConfiguration().getString("groups." + groupId + ".content." + itemId + ".itemType"));

                int durability = this.getConfiguration().getInt("groups." + groupId + ".content." + itemId + ".durability");
                int guardLevel = this.getConfiguration().getInt("groups." + groupId + ".content." + itemId + ".guardLevel");
                int customModelData = this.getConfiguration().getInt("groups." + groupId + ".content." + itemId + ".customModelData");

                ItemStack bukkitItem = ItemUtil.getItem(name, lore, Map.of(Attribute.GENERIC_ARMOR, new AttributeModifier("generic.armor", guardLevel, AttributeModifier.Operation.ADD_NUMBER)), Map.of("duneGroupName", groupId, "duneType", itemType.name()), material, customModelData, durability);
                DuneConquestItem item = DuneConquestItem.builder().itemId(itemId).bukkitItem(bukkitItem).type(itemType).build();
                items.put(itemId, item);
            }

            DuneConquestItemGroup.builder().groupId(groupId).items(items).effects(effects).build().register();
        }
    }

    @Override
    public void disable() {
    }
}