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
import me.nahkd.spigot.sfaddons.endrex.items.machines.EnhancedElectricCrucible;
import me.nahkd.spigot.sfaddons.endrex.items.resources.EndResourceItem;
import me.nahkd.spigot.sfaddons.endrex.utils.PlayerInventoryUtils;

public class EndrexItems {
	
	public static Category CATEGORY_RESOURCES;
	public static EndrexItem ENDER_PEARL_ORE;
	
	public static Category CATEGORY_MACHINES;
	public static EnhancedElectricCrucible ENHANCED_ELECTRIC_CRUCIBLE_1;
	
	public static void init(Endrex plugin) {
		// Resources
		CATEGORY_RESOURCES = new Category(new NamespacedKey(plugin, "resources"), new CustomItem(EndrexSkullItems.PEARL_ORE, "&eEndrex &7- &bResources"));
		ENDER_PEARL_ORE = new EndResourceItem(plugin, new SlimefunItemStack("ENDER_PEARL_ORE", EndrexSkullItems.PEARL_ORE, "&fEnder Pearl Ore", "", "&7How about throwing it away?"), 4, 19)
				.addMachineRecipe((SlimefunMachine) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.ENDER_PEARL, 4))
				.itemUseHandler((e) -> {
					e.getPlayer().launchProjectile(EnderPearl.class);
					e.cancel();
					PlayerInventoryUtils.consumeHand(e.getPlayer().getInventory(), e.getInteractEvent().getHand(), 1);
				})
				.registerChain(plugin);
		
		// Machines
		CATEGORY_MACHINES = new Category(new NamespacedKey(plugin, "machines"), new CustomItem(EndrexSkullItems.ENHANCED_CRUCIBLE_EMPTY, "&eEndrex &7- &bBasic Machines"));
		ENHANCED_ELECTRIC_CRUCIBLE_1 = new EnhancedElectricCrucible(CATEGORY_MACHINES, new SlimefunItemStack(
				"ENHANCED_CRUCIBLE_1",
				EndrexSkullItems.ENHANCED_CRUCIBLE_EMPTY,
				"&eEnhanced Electric Crucible",
				"",
				LoreBuilder.machine(MachineTier.BASIC, MachineType.MACHINE),
				LoreBuilder.speed(1),
				LoreBuilder.powerPerSecond(32)
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
						SlimefunItems.DAMASCUS_STEEL_INGOT, new ItemStack(Material.IRON_INGOT), SlimefunItems.DAMASCUS_STEEL_INGOT,
						SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.CRUCIBLE, SlimefunItems.DAMASCUS_STEEL_INGOT,
						SlimefunItems.HEATING_COIL, new ItemStack(Material.IRON_INGOT), SlimefunItems.HEATING_COIL
				}, 50, 16)
				.registerChain(plugin);
	}
	
}
