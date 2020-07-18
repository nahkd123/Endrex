package me.nahkd.spigot.sfaddons.endrex.items;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemHandler;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class EndrexItem extends SlimefunItem {

	private Runnable onPostReg;
	
	public EndrexItem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
	}

	/**
	 * Same as {@link SlimefunItem#register(SlimefunAddon)}, but return the item for chaining.
	 * Well it's kinda useless, but idc
	 * @param addon
	 * @return
	 */
	public EndrexItem registerChain(SlimefunAddon addon) {register(addon); return this;}

	public EndrexItem addHandlerChain(ItemHandler... handlers) {
		addItemHandler(handlers);
		return this;
	}
	public EndrexItem itemUseHandler(ItemUseHandler handler) {return addHandlerChain(handler);}
	public EndrexItem onPostRegister(Runnable runnable) {
		this.onPostReg = runnable;
		return this;
	}
	
	@Override
	public void postRegister() {
		if (this.onPostReg != null) this.onPostReg.run();
	}
	
}
