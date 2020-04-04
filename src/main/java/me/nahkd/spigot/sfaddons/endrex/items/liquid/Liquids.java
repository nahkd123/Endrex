package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkullItems;

public class Liquids {

	public static CustomLiquid WATER;
	public static CustomLiquid LAVA;
	public static CustomLiquid RESONANT_ENDER;
	
	public static void init(Endrex plugin) {
		// TODO add texture for water and lava
		WATER = new CustomLiquid(new NamespacedKey(plugin, "water"), new CustomItem(Material.WATER_BUCKET, "&fWater"));
		LAVA = new CustomLiquid(new NamespacedKey(plugin, "lava"), new CustomItem(Material.LAVA_BUCKET, "&fLava"));
		RESONANT_ENDER = new CustomLiquid(new NamespacedKey(plugin, "resonant_ender"), new CustomItem(EndrexSkullItems.RESONANT_ENDER, "&3Resonant Ender"));
	}
	public static void postInit() {
		WATER.bucket = new ItemStack(Material.WATER_BUCKET);
		LAVA.bucket = new ItemStack(Material.LAVA_BUCKET);
		
		// RESONANT_ENDER.setBucket(EndrexItems.RESONANT_ENDER_BUCKET.getItem());
		RESONANT_ENDER.bucket = EndrexItems.RESONANT_ENDER_BUCKET.getItem();
		RESONANT_ENDER.crucibleSkull = EndrexSkullItems.ENHANCED_CRUCIBLE_RESONANT_ENDER;
	}
	
}
