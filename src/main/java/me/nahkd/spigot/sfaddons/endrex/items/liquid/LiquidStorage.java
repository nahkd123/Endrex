package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.block.Block;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

@SuppressWarnings("deprecation")
public class LiquidStorage {
	
	// Welcome to deprecation hell!
	public static CustomLiquid getLiquidType(Block b) {
		Config thing = BlockStorage.getLocationInfo(b.getLocation());
		return thing.contains("liquidtype")? CustomLiquid.getLiquidByKey(thing.getString("liquidtype")) : null;
	}
	public static int getMills(Block b) {
		Config thing = BlockStorage.getLocationInfo(b.getLocation());
		return thing.contains("mb")? Integer.parseInt(thing.getString("mb")) : 0;
	}
	public static void setMills(Block b, int val) {
		Config thing = BlockStorage.getLocationInfo(b.getLocation());
		thing.setValue("mb", "" + val);
	}
	public static void setLiquidType(Block b, CustomLiquid liquid) {
		Config thing = BlockStorage.getLocationInfo(b.getLocation());
		thing.setValue("liquidtype", liquid.key);
	}
	
}
