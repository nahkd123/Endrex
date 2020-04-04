package me.nahkd.spigot.sfaddons.endrex.items.machines;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.CustomLiquid;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.LiquidStorage;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

public class EnhancedElectricCrucible extends EndrexItem implements EnergyNetComponent, InventoryBlock {
	
	// Since ItemStack do overrides hashCode(), we can use HashMap
	// Since 1.15 i guess
	private static HashMap<ItemStack, CustomLiquid> inputs_liquidTypes = new HashMap<ItemStack, CustomLiquid>();
	private static HashMap<ItemStack, Integer> inputs_liquidAmount = new HashMap<ItemStack, Integer>();    
	// Should we create another class to store "recipe" info?
	// Note that object creation uses at least 16 bytes
	private static HashMap<Block, CustomLiquid> processing = new HashMap<Block, CustomLiquid>();
	private static HashMap<Block, Integer> processMbLeft = new HashMap<Block, Integer>();
	
	private final int mbPerTick;
	private final int jPerTick;
	private final int liquidCapacity;
	
	public EnhancedElectricCrucible(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int mbPerTick, int jPerTick, int liquidCapacity) {
		super(category, item, recipeType, recipe);
		this.mbPerTick = mbPerTick;
		this.jPerTick = jPerTick;
		this.liquidCapacity = liquidCapacity;
		
		createPreset(this, "&8Enhanced Electric Crucible", this::menuPreset);
		registerBlockHandler(id, (player, block, tool, reason) -> {
			BlockMenu inv = BlockStorage.getInventory(block);
			if (inv != null) {
				// Drop items in inventory
				InventoryUtils.dropItem(this, block);
				
				processing.remove(block);
				processMbLeft.remove(block);
			}
			return true;
		});
	}
	
	public static void addRecipe(ItemStack input, CustomLiquid type, int output) {
		inputs_liquidTypes.put(input, type);
		inputs_liquidAmount.put(input, output);
	}
	
	@Override
	public void preRegister() {
		addItemHandler(new BlockTicker() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void tick(Block b, SlimefunItem item, Config data) {
				try {
					onTick(b, item, data);
				} catch (Exception e) {
					error("o no! an exception thrown", e);
				}
			}
			
			@Override
			public boolean isSynchronized() {return false;}
		});
	}
	
	private static final ItemStack VANILLA_BUCKET = new ItemStack(Material.BUCKET);
	@SuppressWarnings("deprecation")
	private void onTick(Block b, SlimefunItem sfi, Config data) {
		BlockMenu inv = BlockStorage.getInventory(b);
		CustomLiquid liquid = null;
		int mb = 0;
		if (data.contains("mb") && data.contains("liquidtype") && (mb = LiquidStorage.getMills(b)) > 0) {
			// liquid = CustomLiquid.getLiquidByKey(data.getString("liquidtype"));
			liquid = LiquidStorage.getLiquidType(b);
		}
		
		// If the input is a bucket, we'll take it instantly
		ItemStack input = inv.getItemInSlot(getInputSlots()[0]);
		ItemStack output = inv.getItemInSlot(getOutputSlots()[0]);
		if (input != null && VANILLA_BUCKET.isSimilar(input) && mb >= 1000 && (output == null || output.getType() == Material.AIR || SlimefunUtils.isItemSimilar(output, liquid.bucket, true))) {
			// Empty bucket, "pour" to it
			inv.consumeItem(getInputSlots()[0]);
			mb -= 1000;
			inv.pushItem(liquid.bucket, getOutputSlots());
		} else {
			// Check if the bucket is vaild
			// The ItemStack do override hashCode, but only works with same item amount
			for (CustomLiquid otherLiquid : CustomLiquid.getAllLiquids()) if (
					otherLiquid.hasBucket() &&
					SlimefunUtils.isItemSimilar(input, otherLiquid.bucket, true) &&
					(liquid == null || mb <= 0 || liquid.equals(otherLiquid)) &&
					mb + 1000 <= liquidCapacity &&
					(output == null || output.getType() == Material.AIR || SlimefunUtils.isItemSimilar(output, VANILLA_BUCKET, true))
					) {
				mb += 1000;
				if (liquid == null) liquid = otherLiquid;
				inv.consumeItem(getInputSlots()[0]);
				inv.pushItem(VANILLA_BUCKET, getOutputSlots());
				break;
			}
		}
		// Processing liquids
		int mbLeft = 0;
		if (processing.containsKey(b)) {
			if (ChargableBlock.getCharge(b) >= jPerTick && mb < liquidCapacity) {
				mbLeft = processMbLeft.get(b);
				int mbTick;
				if (mbPerTick > mbLeft) mbTick = mbLeft;
				else mbTick = mbPerTick;
				
				mb += mbTick;
				mbLeft -= mbTick;
				if (mbLeft <= 0) {
					processing.remove(b);
					processMbLeft.remove(b);
				} else processMbLeft.put(b, mbLeft);
				if (liquid == null) liquid = processing.get(b);
				
				ChargableBlock.addCharge(b, -jPerTick);
			}
		} else if (input != null && input.getType() != Material.AIR && ChargableBlock.getCharge(b) >= jPerTick && mb < liquidCapacity) {
			// Get "recipe" and add it to map for processing
			for (Entry<ItemStack, CustomLiquid> entry : inputs_liquidTypes.entrySet()) {
				//  && inputs_liquidTypes.containsKey(input)
				ItemStack entryItem = entry.getKey();
				CustomLiquid recipeLiquid = entry.getValue();
				// if (entryItem.isSimilar(input) && (liquid == null || liquid.equals(recipeLiquid))) {
				if (SlimefunUtils.isItemSimilar(input, entryItem, true) && (liquid == null || liquid.equals(recipeLiquid))) {
					int mbPerItem = inputs_liquidAmount.get(entryItem);
					processing.put(b, recipeLiquid);
					processMbLeft.put(b, mbPerItem);
					if (liquid == null || mb <= 0) liquid = recipeLiquid;
					inv.consumeItem(getInputSlots()[0]);
					break;
				}
			}
		}
		
		if (mb > liquidCapacity) mb = liquidCapacity;
		if (liquid != null) {
			LiquidStorage.setLiquidType(b, liquid);
			LiquidStorage.setMills(b, mb);
		}
		
		// Update UI
		if (liquid == null || mb <= 0) {
			if (!GUI_NOLIQUID.isSimilar(inv.getItemInSlot(13))) inv.replaceExistingItem(13, GUI_NOLIQUID);
		} else {
			// Yes ik this might cause lag, soon I'll add if check in here
			String name = liquid.defaultDisplay.hasItemMeta() && liquid.defaultDisplay.getItemMeta().hasDisplayName()? liquid.defaultDisplay.getItemMeta().getDisplayName() : "&cI forgot to add the name for this liquid :|";
			inv.replaceExistingItem(13, new CustomItem(
					liquid.defaultDisplay,
							name,
							"",
							"&7Capacity: &e" + mb + "&7/" + liquidCapacity + " MB",
							"&7Processing: &e" + mbLeft + " &7MB"
					));
		}
	}
	
	// TODO should we create another thing called "liquid storage block"?

	private static final int[] BGINPUTSLOTS = {0, 1, 2, 9, 11, 18, 19, 20};
	private static final int[] BGINFOSLOTS = {3, 4, 5, 12, 14, 21, 22, 23};
	private static final int[] BGOUTPUTSLOTS = {6, 7, 8, 15, 17, 24, 25, 26};
	private static final CustomItem GUI_INPUT = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aInput");
	private static final CustomItem GUI_OUTPUT = new CustomItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bOutput");
	private static final CustomItem GUI_NOLIQUID = new CustomItem(Material.BARRIER, "&cNo Liquid", "&7Nope, nothing :shrug:");
	private void menuPreset(BlockMenuPreset preset) {
		// preset.setSize(27); // dafuq this cause error tho
		for (int s : BGINPUTSLOTS) preset.addItem(s, GUI_INPUT, ChestMenuUtils.getEmptyClickHandler());
		for (int s : BGINFOSLOTS) preset.addItem(s, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
		for (int s : BGOUTPUTSLOTS) preset.addItem(s, GUI_OUTPUT, ChestMenuUtils.getEmptyClickHandler());
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
