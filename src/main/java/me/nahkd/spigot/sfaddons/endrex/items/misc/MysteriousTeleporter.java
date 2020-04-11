package me.nahkd.spigot.sfaddons.endrex.items.misc;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;

public class MysteriousTeleporter extends EndrexItem {

	public MysteriousTeleporter(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
	}
	
	public MysteriousTeleporter registerChain(SlimefunAddon addon) {
		super.registerChain(addon);
		return this;
	}

}
