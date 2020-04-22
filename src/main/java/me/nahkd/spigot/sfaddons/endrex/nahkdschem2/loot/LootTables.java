package me.nahkd.spigot.sfaddons.endrex.nahkdschem2.loot;

import java.util.List;

import org.bukkit.inventory.ItemStack;

/**
 * Utility class
 * @author nahkd123
 *
 */
public class LootTables {
	
	/**
	 * Generate loots
	 * @param invContent
	 * @param table
	 */
	public static void loot(ItemStack[] invContent, List<LootTableEntry> table) {
		for (int i = 0 ; i < invContent.length; i++) {
			for (LootTableEntry e : table) if (e.tryLoot()) {
				invContent[i] = e.loot;
				break;
			}
		}
	}
	
}
