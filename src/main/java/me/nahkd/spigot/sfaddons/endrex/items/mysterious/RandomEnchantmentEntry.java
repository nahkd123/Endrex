package me.nahkd.spigot.sfaddons.endrex.items.mysterious;

import org.bukkit.enchantments.Enchantment;

public class RandomEnchantmentEntry {
	
	public final Enchantment type;
	public final double chance;
	public final int min;
	public final int max;
	
	public RandomEnchantmentEntry(Enchantment type, double chance, int min, int max) {
		this.type = type;
		this.chance = chance;
		this.min = min;
		this.max = max;
	}
	
}
