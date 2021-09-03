package me.nahkd.spigot.sfaddons.endrex.items.resources;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;

public class EndrexResourceItem extends EndrexItem {
	
	// private final SlimefunItemStack item;
	private boolean applyRecipe;
	private RecipeDisplayItem machine;
	private ItemStack recipeOutput;
	
	public EndrexResourceItem(JavaPlugin plugin, SlimefunItemStack item, int randomMin, int randomMax) {
		super(EndrexItems.CATEGORY_RESOURCES, item, RecipeType.GEO_MINER, new ItemStack[0]);
		this.applyRecipe = false;
		machine = null;
		recipeOutput = null;
		
		new EndrexResource(new NamespacedKey(plugin, item.getItemId()), this, randomMin, randomMax - randomMin).register();
	}
	
	// public String getName() {return item.getItemID();}
	public EndrexResourceItem addMachineRecipe(RecipeDisplayItem machine, ItemStack output) {
		this.machine = machine;
		this.recipeOutput = output;
		this.applyRecipe = true;
		return this;
	}
	
	@Override
	public void postRegister() {
		if (applyRecipe) {
			machine.getDisplayRecipes().add(recipeOutput);
			//addRecipe(new ItemStack[] {item}, recipeOutput);
		}
		super.postRegister();
	}
	
}
