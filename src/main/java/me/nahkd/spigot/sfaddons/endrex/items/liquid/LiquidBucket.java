package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;

public class LiquidBucket extends EndrexItem {
	
	public LiquidBucket(ItemGroup category, SlimefunItemStack item) {
		super(category, item, EndrexRecipeType.LIQUID_STORAGE, new ItemStack[0]);
	}
	
}
