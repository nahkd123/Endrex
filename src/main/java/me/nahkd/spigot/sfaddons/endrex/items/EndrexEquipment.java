package me.nahkd.spigot.sfaddons.endrex.items;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

public class EndrexEquipment extends EndrexItem {

	public EndrexEquipment(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
		disenchantable = false;
		enchantable = false;
	}

}
