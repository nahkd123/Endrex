package me.nahkd.spigot.sfaddons.endrex.items.resources;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;

public class EndResourceItem extends EndrexItem {
	
	private final SlimefunItemStack item;
	private boolean applyRecipe;
	private SlimefunMachine machine;
	private ItemStack recipeOutput;
	
	public EndResourceItem(JavaPlugin plugin, SlimefunItemStack item, int randomMin, int randomMax) {
		super(EndrexItems.CATEGORY_RESOURCES, item, RecipeType.GEO_MINER, new ItemStack[0]);
		this.item = item;
		this.applyRecipe = false;
		machine = null;
		recipeOutput = null;
		
		new EndResource(new NamespacedKey(plugin, item.getItemID()), this, randomMin, randomMax - randomMin).register();
	}
	
	// public String getName() {return item.getItemID();}
	public EndResourceItem addMachineRecipe(SlimefunMachine machine, ItemStack output) {
		this.machine = machine;
		this.recipeOutput = output;
		this.applyRecipe = true;
		return this;
	}
	
	@Override
	public void postRegister() {
		if (applyRecipe) {
			machine.addRecipe(new ItemStack[] {item}, recipeOutput);
		}
		super.postRegister();
	}
	
}
