package me.nahkd.spigot.sfaddons.endrex.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;

public class EndrexRecipeType extends RecipeType {

	public static EndrexRecipeType LIQUID_STORAGE;
	
	public static void init(Endrex plugin) {
		// TODO change the icon to something better
		LIQUID_STORAGE = new EndrexRecipeType(new NamespacedKey(plugin, "liquid_storage"), new CustomItem(EndrexSkulls.RESONANT_ENDER_BUCKET, "&eLiquid Storage", "&7Get this item from liquid", "&7storage."));
	}
	
	public EndrexRecipeType(NamespacedKey key, ItemStack item) {
		super(key, item);
	}

}
