package me.nahkd.spigot.sfaddons.endrex.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;

public class EndrexRecipeType extends RecipeType {

	public static EndrexRecipeType LIQUID_STORAGE;
	public static EndrexRecipeType DUSTS_FABRICATOR;
	public static EndrexRecipeType KILL_ENDER_DRAGON;
	
	public static void init(Endrex plugin) {
		// TODO change the icon to something better
		LIQUID_STORAGE = new EndrexRecipeType(new NamespacedKey(plugin, "liquid_storage"), new CustomItem(EndrexSkulls.RESONANT_ENDER_BUCKET, "&eLiquid Storage", "&7Get this item from liquid", "&7storage."));
		DUSTS_FABRICATOR = new EndrexRecipeType(new NamespacedKey(plugin, "dusts_fabricator"), new CustomItem(EndrexSkulls.DUSTS_FABRICATOR, "&bDusts Fabricator", "&7Fabricate this item from", "&7Dusts Fabricator."));
		KILL_ENDER_DRAGON = new EndrexRecipeType(new NamespacedKey(plugin, "kill_ender_dragon"), new CustomItem(Material.DRAGON_EGG, "&eKill Ender Dragon", "&7Do it."));
	}
	
	public EndrexRecipeType(NamespacedKey key, ItemStack item) {
		super(key, item);
	}

}
