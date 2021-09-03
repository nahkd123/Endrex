package me.nahkd.spigot.sfaddons.endrex.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Wrap around the item stack to do stuffs
 * @author nahkd123
 *
 */
public class ItemStackWrapper {
	
	private ItemStack itemStack;
	private ItemMeta meta;
	
	public ItemStackWrapper(ItemStack item) {
		itemStack = item;
		meta = item.getItemMeta();
	}
	
	public ItemStackWrapper addEnchant(Enchantment ench, int lv) {
		meta.addEnchant(ench, lv, true);
		return this;
	}
	
	public ItemStackWrapper addFlag(ItemFlag... flags) {
		meta.addItemFlags(flags);
		return this;
	}
	
	public ItemStack getItem() {
	    itemStack.setItemMeta(meta);
	    return itemStack;
	}
	
}
