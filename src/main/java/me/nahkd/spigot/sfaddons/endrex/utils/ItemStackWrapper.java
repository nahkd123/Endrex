package me.nahkd.spigot.sfaddons.endrex.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Wrap around the item stack to do stuffs
 * @author nahkd123
 *
 */
public class ItemStackWrapper {
	
	private CustomItem itemStack;
	
	public ItemStackWrapper(CustomItem item) {
		itemStack = item;
	}
	
	public ItemStackWrapper addEnchant(Enchantment ench, int lv) {
		itemStack.addUnsafeEnchantment(ench, lv);
		return this;
	}
	
	public ItemStackWrapper addFlag(ItemFlag... flags) {
		itemStack.addFlags(flags);
		return this;
	}
	
	public CustomItem getItem() {return itemStack;}
	
}
