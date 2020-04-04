package me.nahkd.spigot.sfaddons.endrex.items.machines;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.CustomLiquid;

public class EnhancedElectricCrucible extends EndrexItem implements EnergyNetComponent, InventoryBlock {
	
	// Since ItemStack do overrides hashCode(), we can use HashMap
	// Since 1.15 i guess
	private HashMap<ItemStack, CustomLiquid> inputs_liquidTypes;
	private HashMap<ItemStack, Integer> inputs_liquidAmount;
	// Should we create another class to store "recipe" info?
	// Note that object creation uses at least 16 bytes
	
	private final int mbPerTick;
	private final int jPerTick;
	
	public EnhancedElectricCrucible(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int mbPerTick, int jPerTick) {
		super(category, item, recipeType, recipe);
		this.mbPerTick = mbPerTick;
		this.jPerTick = jPerTick;
		this.inputs_liquidTypes = new HashMap<ItemStack, CustomLiquid>();
		this.inputs_liquidAmount = new HashMap<ItemStack, Integer>();
		
		createPreset(this, "&8Enhanced Electric Crucible", this::menuPreset);
	}

	private static final int[] BGINPUTSLOTS = {0, 1, 2, 9, 11, 18, 19, 20};
	private static final int[] BGINFOSLOTS = {3, 4, 5, 12, 14, 21, 22, 23};
	private static final int[] BGOUTPUTSLOTS = {6, 7, 8, 15, 17, 24, 25, 26};
	private static final CustomItem GUI_INPUT = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aInput");
	private static final CustomItem GUI_OUTPUT = new CustomItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bOutput");
	private static final CustomItem GUI_NOLIQUID = new CustomItem(Material.BARRIER, "&cNo Liquid", "&7Nope, nothing :shrug:");
	private void menuPreset(BlockMenuPreset preset) {
		preset.setSize(27);
		for (int s : BGINPUTSLOTS) preset.addItem(s, GUI_INPUT, ChestMenuUtils.getEmptyClickHandler());
		for (int s : BGINFOSLOTS) preset.addItem(s, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
		for (int s : BGOUTPUTSLOTS) preset.addItem(s, GUI_OUTPUT, ChestMenuUtils.getEmptyClickHandler());
		preset.addItem(26, GUI_OUTPUT, ChestMenuUtils.getEmptyClickHandler());
		preset.addItem(13, GUI_NOLIQUID, ChestMenuUtils.getEmptyClickHandler());
	}

	@Override
	public EnergyNetComponentType getEnergyComponentType() {return EnergyNetComponentType.CONSUMER;}

	@Override
	public int getCapacity() {return 128;}
	
	public EnhancedElectricCrucible registerChain(SlimefunAddon plugin) {
		register(plugin);
		return this;
	}

	
	@Override
	public int[] getInputSlots() {return new int[] {10};}

	@Override
	public int[] getOutputSlots() {return new int[] {16};}
	
}
