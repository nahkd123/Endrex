package me.nahkd.spigot.sfaddons.endrex.items.mysterious;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexEquipment;

public class MysteriousEquipment extends EndrexEquipment {

	private static HashMap<String, MysteriousEquipment> mappedItems = new HashMap<String, MysteriousEquipment>();
	public static HashMap<String, MysteriousEquipment> getMappedItems() {return mappedItems;}
	
	public HashMap<Enchantment, List<RandomEnchantmentEntry>> enchs;
	
	public MysteriousEquipment(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
		
		mappedItems.put(getId(), this);
		enchs = new HashMap<Enchantment, List<RandomEnchantmentEntry>>();
	}
	
	public MysteriousEquipment addEnchantment(RandomEnchantmentEntry... entries) {
		for (RandomEnchantmentEntry entry : entries) {
			List<RandomEnchantmentEntry> list = enchs.getOrDefault(entry.type, new ArrayList<RandomEnchantmentEntry>());
			list.add(entry);
			enchs.put(entry.type, list);
		}
		return this;
	}
	
	/**
	 * Apply enchantments to the item meta
	 * @param meta The item meta to apply
	 */
	public void applyEnchantment(ItemMeta meta) {
		boolean enchanted = false;
		while (!enchanted) for (Entry<Enchantment, List<RandomEnchantmentEntry>> entry : enchs.entrySet()) {
			Enchantment e = entry.getKey();
			List<RandomEnchantmentEntry> craps = entry.getValue();
			
			for (RandomEnchantmentEntry childOfThoseCraps : craps) if (Math.random() < childOfThoseCraps.chance) {
				meta.addEnchant(e, childOfThoseCraps.min + (childOfThoseCraps.min == childOfThoseCraps.max? 0 : Endrex.getRandomizer().nextInt(childOfThoseCraps.max - childOfThoseCraps.min)), true);
				enchanted = true;
				break;
			}
		}
	}

}
