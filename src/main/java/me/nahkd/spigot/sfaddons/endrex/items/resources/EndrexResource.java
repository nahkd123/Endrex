package me.nahkd.spigot.sfaddons.endrex.items.resources;

import org.bukkit.NamespacedKey;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;

public class EndrexResource implements GEOResource {

	private final NamespacedKey key;
	private final EndrexResourceItem item;
	private final int randomMin;
	private final int randomDelta;
	
	public EndrexResource(NamespacedKey key, EndrexResourceItem item, int randomMin, int randomMax) {
		this.key = key;
		this.item = item;
		this.randomMin = randomMin;
		this.randomDelta = randomMax - randomMin;
	}
	
	@Override
	public NamespacedKey getKey() {return key;}

	@Override
	public int getDefaultSupply(Environment environment, Biome biome) {
		switch (environment) {
		case THE_END: return randomMin;
		default: return 0;
		}
	}

	@Override
	public int getMaxDeviation() {return randomDelta;}

	@Override
	public String getName() {
		ItemStack is = item.getItem();
		return is.hasItemMeta() && is.getItemMeta().hasDisplayName()? is.getItemMeta().getDisplayName() : item.getId();
	}

	@Override
	public ItemStack getItem() {return item.getItem();}

	@Override
	public boolean isObtainableFromGEOMiner() {return true;}

}
