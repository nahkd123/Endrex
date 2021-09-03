package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;

/**
 * This isn't actual liquid. It's not like what you might think...
 * @author nahkd123
 *
 */
public class CustomLiquid {
	
	private static HashMap<String, CustomLiquid> inited = new HashMap<String, CustomLiquid>();
	public static CustomLiquid getLiquidByKey(NamespacedKey key) {
		return inited.get(key.toString());
	}
	
	/**
	 * Get custom liquid type by string
	 * @deprecated Should be used internally by Endrex
	 * @param key
	 * @return
	 */
	@Deprecated
	public static CustomLiquid getLiquidByKey(String key) {
		return inited.get(key);
	}
	public static Collection<CustomLiquid> getAllLiquids() {
		return inited.values();
	}
	
	public final String key;
	public final ItemStack defaultDisplay;
	public ItemStack bucket;
	public PlayerSkin crucibleSkull;
	
	public CustomLiquid(NamespacedKey key, ItemStack defaultDisplay) {
		this.key = key.toString();
		this.defaultDisplay = defaultDisplay;
		
		inited.put(this.key, this);
	}
	
	/** A overrideable method */
	public boolean canConvertTo(CustomLiquid target) {return false;}
	/** A overrideable method */
	public int convertTo(CustomLiquid target, int millibucketFrom) {return millibucketFrom;}
	public boolean hasBucket() {return bucket != null && bucket.getType() != Material.AIR;}
	
	// public void setBucket(ItemStack bucket) {this.bucket = bucket;}
	
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CustomLiquid)) return false;
		return ((CustomLiquid) obj).key.equals(key);
	}
}
