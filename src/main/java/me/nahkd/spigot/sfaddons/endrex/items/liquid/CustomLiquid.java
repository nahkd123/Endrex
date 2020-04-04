package me.nahkd.spigot.sfaddons.endrex.items.liquid;

import org.bukkit.NamespacedKey;

/**
 * This isn't actual liquid. It's not like what you might think...
 * @author nahkd123
 *
 */
public class CustomLiquid {
	
	public final String key;
	
	public CustomLiquid(NamespacedKey key) {
		this.key = key.toString();
	}
	
	public boolean canConvertTo(CustomLiquid target) {return false;}
	public int convertTo(CustomLiquid target, int millibucketFrom) {return millibucketFrom;}
	
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}
	
}
