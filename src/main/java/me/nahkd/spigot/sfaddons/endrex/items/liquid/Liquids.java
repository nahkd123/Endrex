package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.NamespacedKey;

import me.nahkd.spigot.sfaddons.endrex.Endrex;

public class Liquids {

	public static CustomLiquid WATER;
	public static CustomLiquid LAVA;
	public static CustomLiquid RESONANT_ENDER;
	
	public static void init(Endrex plugin) {
		WATER = new CustomLiquid(new NamespacedKey(plugin, "water"));
		LAVA = new CustomLiquid(new NamespacedKey(plugin, "lava"));
		RESONANT_ENDER = new CustomLiquid(new NamespacedKey(plugin, "resonant_ender"));
	}
	
}
