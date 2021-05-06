package me.nahkd.spigot.sfaddons.endrex.items;

import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class EndrexEquipment extends EndrexItem {

	public EndrexEquipment(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
		disenchantable = false;
		enchantable = false;
	}

}
