package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;

public class LiquidBucket extends EndrexItem {
	
	public LiquidBucket(Category category, SlimefunItemStack item) {
		super(category, item, EndrexRecipeType.LIQUID_STORAGE, new ItemStack[0]);
	}
	
}
