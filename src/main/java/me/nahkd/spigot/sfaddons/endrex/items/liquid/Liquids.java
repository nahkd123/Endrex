package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;

public class Liquids {

	public static CustomLiquid WATER;
	public static CustomLiquid LAVA;
	public static CustomLiquid RESONANT_ENDER;
	public static CustomLiquid MYSTHERIUM;
	
	public static void init(Endrex plugin) {
		// TODO add texture for water and lava
		WATER = new CustomLiquid(new NamespacedKey(plugin, "water"), new CustomItem(Material.WATER_BUCKET, "&fWater"));
		LAVA = new CustomLiquid(new NamespacedKey(plugin, "lava"), new CustomItem(Material.LAVA_BUCKET, "&fLava"));
		RESONANT_ENDER = new CustomLiquid(new NamespacedKey(plugin, "resonant_ender"), new CustomItem(EndrexSkulls.RESONANT_ENDER, "&3Resonant Ender"));
		MYSTHERIUM = new CustomLiquid(new NamespacedKey(plugin, "mystherium"), new CustomItem(EndrexSkulls.MYSTHERIUM_LIQUID, "&5Mystherium"));
	}
	public static void postInit() {
		WATER.bucket = new ItemStack(Material.WATER_BUCKET);
		WATER.crucibleSkullHash = EndrexSkulls.ENHANCED_CRUCIBLE_RESONANT_WATER;
		LAVA.bucket = new ItemStack(Material.LAVA_BUCKET);
		LAVA.crucibleSkullHash = EndrexSkulls.ENHANCED_CRUCIBLE_RESONANT_LAVA;
		RESONANT_ENDER.bucket = EndrexItems.RESONANT_ENDER_BUCKET.getItem();
		RESONANT_ENDER.crucibleSkullHash = EndrexSkulls.ENHANCED_CRUCIBLE_RESONANT_ENDER;
		MYSTHERIUM.bucket = EndrexItems.MYSTHERIUM_BUCKET.getItem();
		MYSTHERIUM.crucibleSkullHash = EndrexSkulls.ENHANCED_CRUCIBLE_MYSTHERIUM;
	}
	
}
