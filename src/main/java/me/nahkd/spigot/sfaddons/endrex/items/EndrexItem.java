package me.nahkd.spigot.sfaddons.endrex.items;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;

public class EndrexItem extends SlimefunItem {

	private Runnable onPostReg;
	
	public EndrexItem(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
	}

	/**
	 * Same as {@link SlimefunItem#register(SlimefunAddon)}, but return the item for chaining.
	 * Well it's kinda useless, but idc
	 * @param addon
	 * @return
	 */
	public EndrexItem registerChain(SlimefunAddon addon) {register(addon); return this;}

	public EndrexItem addHandlerChain(ItemHandler handlers) {
		addItemHandler(handlers);
		return this;
	}
	public EndrexItem itemUseHandler(ItemUseHandler handler) {return addHandlerChain(handler);}
	
	@Override
	public void postRegister() {if (this.onPostReg != null) this.onPostReg.run();}
	
}
