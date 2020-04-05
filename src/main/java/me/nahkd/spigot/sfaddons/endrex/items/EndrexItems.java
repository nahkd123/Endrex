package me.nahkd.spigot.sfaddons.endrex.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderPearl;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.LiquidBucket;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.Liquids;
import me.nahkd.spigot.sfaddons.endrex.items.machines.EnhancedElectricCrucible;
import me.nahkd.spigot.sfaddons.endrex.items.resources.EndResourceItem;
import me.nahkd.spigot.sfaddons.endrex.utils.EndrexLoreBuilder;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

public class EndrexItems {
	
	public static Category CATEGORY_RESOURCES;
	public static EndrexItem ENDER_PEARL_ORE;
	public static EndrexItem RESONANT_ENDER_BUCKET;
	
	public static Category CATEGORY_MACHINES;
	public static EnhancedElectricCrucible ENHANCED_ELECTRIC_CRUCIBLE_1;
	public static EnhancedElectricCrucible ENHANCED_ELECTRIC_CRUCIBLE_2;
	
	public static void init(Endrex plugin) {
		// Resources
		CATEGORY_RESOURCES = new Category(new NamespacedKey(plugin, "resources"), new CustomItem(EndrexSkulls.PEARL_ORE, "&eEndrex &7- &bResources"));
		ENDER_PEARL_ORE = new EndResourceItem(plugin, new SlimefunItemStack("ENDER_PEARL_ORE", EndrexSkulls.PEARL_ORE, "&fEnder Pearl Ore", "", "&7How about throwing it away?"), 4, 19)
				.addMachineRecipe((SlimefunMachine) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.ENDER_PEARL, 4))
				.itemUseHandler((e) -> {
					e.getPlayer().launchProjectile(EnderPearl.class);
					e.cancel();
					InventoryUtils.consumeHand(e.getPlayer().getInventory(), e.getInteractEvent().getHand(), 1);
				})
				.registerChain(plugin);
		RESONANT_ENDER_BUCKET = new LiquidBucket(CATEGORY_RESOURCES, new SlimefunItemStack("RESONANT_ENDER_BUCKET", EndrexSkulls.RESONANT_ENDER_BUCKET, "&bBucket of Resonant Ender", "", "&7No you can't pour liquid", "&fyet :(")).registerChain(plugin);
		
		// Machines
		CATEGORY_MACHINES = new Category(new NamespacedKey(plugin, "machines"), new CustomItem(EndrexSkulls.ENHANCED_CRUCIBLE_EMPTY, "&eEndrex &7- &bBasic Machines"));
		initCrucibles(plugin);
		
		// Other stuffs
		EnhancedElectricCrucible.addRecipe(ENDER_PEARL_ORE.getItem(), Liquids.RESONANT_ENDER, 1000);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.ENDER_PEARL), Liquids.RESONANT_ENDER, 200); // Better to smelt ore!
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.COBBLESTONE), Liquids.LAVA, 65);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.STONE), Liquids.LAVA, 75);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.BLAZE_ROD), Liquids.LAVA, 100);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.BLAZE_POWDER), Liquids.LAVA, 50);
	}
	
	private static void initCrucibles(Endrex plugin) {
		// The main reason why I created crucible is because I want to find a way to use
		// ender pearl ore.
		
		ENHANCED_ELECTRIC_CRUCIBLE_1 = new EnhancedElectricCrucible(CATEGORY_MACHINES, new SlimefunItemStack(
				"ENHANCED_CRUCIBLE_1",
				EndrexSkulls.ENHANCED_CRUCIBLE_EMPTY,
				"&eEnhanced Electric Crucible",
				"",
				LoreBuilder.machine(MachineTier.BASIC, MachineType.MACHINE),
				LoreBuilder.speed(1),
				LoreBuilder.powerPerSecond(32),
				EndrexLoreBuilder.liquidCapacity(2500),
				EndrexLoreBuilder.liquidPerTick(50)
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
						SlimefunItems.DAMASCUS_STEEL_INGOT, new ItemStack(Material.IRON_INGOT), SlimefunItems.DAMASCUS_STEEL_INGOT,
						SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.CRUCIBLE, SlimefunItems.DAMASCUS_STEEL_INGOT,
						SlimefunItems.HEATING_COIL, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.HEATING_COIL
				}, 50, 16, 2500)
				.registerChain(plugin);
		ENHANCED_ELECTRIC_CRUCIBLE_2 = new EnhancedElectricCrucible(CATEGORY_MACHINES, new SlimefunItemStack(
				"ENHANCED_CRUCIBLE_2",
				EndrexSkulls.ENHANCED_CRUCIBLE_EMPTY,
				"&eEnhanced Electric Crucible &7- &eII",
				"",
				LoreBuilder.machine(MachineTier.BASIC, MachineType.MACHINE),
				LoreBuilder.speed(2),
				LoreBuilder.powerPerSecond(48),
				EndrexLoreBuilder.liquidCapacity(5000),
				EndrexLoreBuilder.liquidPerTick(100)
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
						new ItemStack(Material.IRON_INGOT), null, new ItemStack(Material.IRON_INGOT),
						SlimefunItems.DAMASCUS_STEEL_INGOT, ENHANCED_ELECTRIC_CRUCIBLE_1.getItem(), SlimefunItems.DAMASCUS_STEEL_INGOT,
						SlimefunItems.HEATING_COIL, null, SlimefunItems.HEATING_COIL
				}, 100, 24, 5000)
				.registerChain(plugin);
	}
	
}
