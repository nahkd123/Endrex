package me.nahkd.spigot.sfaddons.endrex.schem2.loot;

import org.bukkit.inventory.ItemStack;

public class LootTableEntry {
	
	public double chance;
	public ItemStack loot;
	
	public LootTableEntry(double chance, ItemStack loot) {
		this.chance = chance;
		this.loot = loot;
	}
	public boolean tryLoot() {return Math.random() <= this.chance;}
	
}
