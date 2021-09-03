package me.nahkd.spigot.sfaddons.endrex.items;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.LiquidBucket;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.Liquids;
import me.nahkd.spigot.sfaddons.endrex.items.machines.DustsFabricator;
import me.nahkd.spigot.sfaddons.endrex.items.machines.EnhancedElectricCrucible;
import me.nahkd.spigot.sfaddons.endrex.items.misc.EndRespawnAnchor;
import me.nahkd.spigot.sfaddons.endrex.items.misc.MysteriousTeleporter;
import me.nahkd.spigot.sfaddons.endrex.items.mysterious.MysteriousEquipment;
import me.nahkd.spigot.sfaddons.endrex.items.mysterious.RandomEnchantmentEntry;
import me.nahkd.spigot.sfaddons.endrex.items.resources.EndrexMineableResource;
import me.nahkd.spigot.sfaddons.endrex.items.resources.EndrexResourceItem;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;
import me.nahkd.spigot.sfaddons.endrex.utils.EndrexLoreBuilder;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;
import me.nahkd.spigot.sfaddons.endrex.utils.ItemStackWrapper;

public class EndrexItems {
	
	public static ItemGroup CATEGORY_RESOURCES;
	public static EndrexItem ENDER_PEARL_ORE;
	public static EndrexItem CHORUS_ORE;
	public static EndrexItem END_STONE_COAL_ORE;
	public static EndrexItem END_STONE_IRON_ORE;
	public static EndrexItem END_STONE_LAPIS_ORE;
	public static EndrexItem END_STONE_REDSTONE_ORE;
	public static EndrexItem END_STONE_GOLD_ORE;
	public static EndrexItem RESONANT_ENDER_BUCKET;
	public static EndrexItem RESONANT_ENDER_DUST;
	public static EndrexItem ENDERIUM;
	public static EndrexItem ENDERIUM_BLOCK;
	public static EndrexItem DRAGON_SCALE;
	public static EndrexItem REINFORCED_DRAGON_SCALE;
	public static EndrexItem MYSTHERIUM_ORE;
	public static EndrexItem MYSTHERIUM;
	public static EndrexItem MYSTHERIUM_BUCKET;
	
	public static ItemGroup CATEGORY_MACHINES;
	public static EnhancedElectricCrucible ENHANCED_ELECTRIC_CRUCIBLE_1;
	public static EnhancedElectricCrucible ENHANCED_ELECTRIC_CRUCIBLE_2;
	public static DustsFabricator DUSTS_FABRICATOR_1;
	public static DustsFabricator DUSTS_FABRICATOR_2;
	
	public static ItemGroup CATEGORY_WEAPONS_AND_EQUIPMENTS;
	public static EndrexItem ENDERIUM_SWORD;
	public static EndrexItem EMPOWERED_ENDERIUM_SWORD;
	public static EndrexItem ELYTRA_OF_THE_LITTLE_DRAGON;
	public static EndrexItem REINFORCED_ELYTRA;
	public static EndrexItem MASK_OF_ENDER;
	public static EndrexItem MYSTERIOUS_SWORD;
	public static EndrexItem MYSTERIOUS_PICKAXE;
	public static EndrexItem MYSTERIOUS_AXE;
	
	public static ItemGroup CATEGORY_MISCELLANEOUS;
	public static EndRespawnAnchor END_RESPAWN_ANCHOR;
	public static MysteriousTeleporter MYSTERIOUS_TELEPORTER;
	public static EndrexItem MYSTERIOUS_TELEPORTER_LINKER;
	
	public static void init(Endrex plugin) {
		// Resources
		CATEGORY_RESOURCES = new ItemGroup(new NamespacedKey(plugin, "resources"), new CustomItemStack(PlayerHead.getItemStack(EndrexSkulls.PEARL_ORE), "&eEndrex &7- &bResources"));
		initResources(plugin);
		
		// Machines
		CATEGORY_MACHINES = new ItemGroup(new NamespacedKey(plugin, "machines"), new CustomItemStack(PlayerHead.getItemStack(EndrexSkulls.ENHANCED_CRUCIBLE_EMPTY), "&eEndrex &7- &bBasic Machines"));
		initCrucibles(plugin);
		initDustsFarbicators(plugin);
		
		// Weapons
		CATEGORY_WEAPONS_AND_EQUIPMENTS = new ItemGroup(new NamespacedKey(plugin, "weapons_n_equipments"), new CustomItemStack(Material.DIAMOND_SWORD, "&eEndrex &7- &bWeapons and Equipments"));
		initWeapons(plugin);
		initEquipments(plugin);
		
		CATEGORY_MISCELLANEOUS = new ItemGroup(new NamespacedKey(plugin, "miscellaneous"), new CustomItemStack(Material.ENDER_EYE, "&eEndrex &7- &bMiscellaneous"));
		initInterestingThings(plugin);
		
		// Other stuffs
		EnhancedElectricCrucible.addRecipe(ENDER_PEARL_ORE.getItem(), Liquids.RESONANT_ENDER, 1000);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.ENDER_PEARL), Liquids.RESONANT_ENDER, 200); // Better to smelt ore!
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.COBBLESTONE), Liquids.LAVA, 65);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.STONE), Liquids.LAVA, 75);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.BLAZE_ROD), Liquids.LAVA, 100);
		EnhancedElectricCrucible.addRecipe(new ItemStack(Material.BLAZE_POWDER), Liquids.LAVA, 50);
		
		DustsFabricator.addRecipe(Liquids.RESONANT_ENDER, 500, RESONANT_ENDER_DUST.getItem());
	}
	
	private static void initCrucibles(Endrex plugin) {
		// The main reason why I created crucible is because I want to find a way to use
		// ender pearl ore.
		
		ENHANCED_ELECTRIC_CRUCIBLE_1 = new EnhancedElectricCrucible(CATEGORY_MACHINES, new SlimefunItemStack(
				"ENHANCED_CRUCIBLE_1",
				PlayerHead.getItemStack(EndrexSkulls.ENHANCED_CRUCIBLE_EMPTY),
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
				PlayerHead.getItemStack(EndrexSkulls.ENHANCED_CRUCIBLE_EMPTY),
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
	private static void initDustsFarbicators(Endrex plugin) {
		DUSTS_FABRICATOR_1 = new DustsFabricator(CATEGORY_MACHINES, new SlimefunItemStack(
				"DUSTS_FABRICATOR_1",
				PlayerHead.getItemStack(EndrexSkulls.DUSTS_FABRICATOR),
				"&bDusts Fabricator",
				"",
				LoreBuilder.machine(MachineTier.BASIC, MachineType.MACHINE),
				LoreBuilder.speed(1),
				LoreBuilder.powerPerSecond(32),
				EndrexLoreBuilder.liquidCapacity(3000),
				EndrexLoreBuilder.liquidPerTick(50)
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE),
				new ItemStack(Material.COBBLESTONE), SlimefunItems.ELECTRIC_FURNACE, new ItemStack(Material.COBBLESTONE),
				SlimefunItems.HEATING_COIL, new ItemStack(Material.OBSIDIAN), SlimefunItems.HEATING_COIL
				}, 50, 16, 3000)
				.registerChain(plugin);
		DUSTS_FABRICATOR_2 = new DustsFabricator(CATEGORY_MACHINES, new SlimefunItemStack(
				"DUSTS_FABRICATOR_2",
				PlayerHead.getItemStack(EndrexSkulls.DUSTS_FABRICATOR),
				"&bDusts Fabricator &7- &eII",
				"",
				LoreBuilder.machine(MachineTier.BASIC, MachineType.MACHINE),
				LoreBuilder.speed(1),
				LoreBuilder.powerPerSecond(32),
				EndrexLoreBuilder.liquidCapacity(3000),
				EndrexLoreBuilder.liquidPerTick(100)
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE),
				new ItemStack(Material.COBBLESTONE), DUSTS_FABRICATOR_1.getItem(), new ItemStack(Material.COBBLESTONE),
				SlimefunItems.HEATING_COIL, new ItemStack(Material.OBSIDIAN), SlimefunItems.HEATING_COIL
				}, 100, 16, 3000)
				.registerChain(plugin);
	}
	private static void initResources(Endrex plugin) {
		ENDER_PEARL_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("ENDER_PEARL_ORE", PlayerHead.getItemStack(EndrexSkulls.PEARL_ORE), "&fEnder Pearl Ore", "", "&7How about throwing it away?"), 4, 19)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.ENDER_PEARL, 4))
				.itemUseHandler((e) -> {
					e.getPlayer().launchProjectile(EnderPearl.class);
					e.cancel();
					InventoryUtils.consumeHand(e.getPlayer().getInventory(), e.getInteractEvent().getHand(), 1);
				})
				.registerChain(plugin);
		CHORUS_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("CHORUS_ORE", PlayerHead.getItemStack(EndrexSkulls.CHORUS_ORE), "&fChorus Ore", "", "&7Even though it's \"Chorus\", doesn't", "&7mean that you can eat it."), 7, 23)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.CHORUS_FRUIT, 4))
				.registerChain(plugin);
		
		// Overworld resources
		END_STONE_COAL_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("END_STONE_COAL_ORE", PlayerHead.getItemStack(EndrexSkulls.COAL_ORE), "&fCoal Ore with End Stone", "", "&7Why such thing like this", "&7do exists?"), 9, 27)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.COAL, 3))
				.registerChain(plugin);
		END_STONE_IRON_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("END_STONE_IRON_ORE", PlayerHead.getItemStack(EndrexSkulls.IRON_ORE), "&fIron Ore with End Stone", "", "&7Why such thing like this", "&7do exists?"), 8, 25)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new CustomItemStack(SlimefunItems.IRON_DUST, 3))
				.registerChain(plugin);
		END_STONE_LAPIS_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("END_STONE_LAPIS_ORE", PlayerHead.getItemStack(EndrexSkulls.LAPIS_ORE), "&fLapis Ore with End Stone", "", "&7Why such thing like this", "&7do exists?"), 5, 12)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.LAPIS_LAZULI, 14))
				.registerChain(plugin);
		END_STONE_REDSTONE_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("END_STONE_REDSTONE_ORE", PlayerHead.getItemStack(EndrexSkulls.REDSTONE_ORE), "&fRedstone Ore with End Stone", "", "&7Why such thing like this", "&7do exists?"), 5, 12)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new ItemStack(Material.REDSTONE, 8))
				.registerChain(plugin);
		END_STONE_GOLD_ORE = new EndrexResourceItem(plugin, new SlimefunItemStack("END_STONE_GOLD_ORE", PlayerHead.getItemStack(EndrexSkulls.GOLD_ORE), "&fGold Ore with End Stone", "", "&7Why such thing like this", "&7do exists?"), 0, 5)
				.addMachineRecipe((RecipeDisplayItem) RecipeType.ORE_CRUSHER.getMachine(), new CustomItemStack(SlimefunItems.GOLD_DUST, 2))
				.registerChain(plugin);
		
		RESONANT_ENDER_BUCKET = new LiquidBucket(CATEGORY_RESOURCES, new SlimefunItemStack("RESONANT_ENDER_BUCKET", PlayerHead.getItemStack(EndrexSkulls.RESONANT_ENDER_BUCKET), "&bBucket of Resonant Ender", "", "&7No you can't pour liquid", "&fyet :(")).registerChain(plugin);
		RESONANT_ENDER_DUST = new EndrexItem(CATEGORY_RESOURCES, new SlimefunItemStack("RESONANT_ENDER_DUST", Material.CYAN_DYE, "&bResonant Ender Dust"), EndrexRecipeType.DUSTS_FABRICATOR, new ItemStack[0]).registerChain(plugin);
		ENDERIUM = new EndrexItem(CATEGORY_RESOURCES, new SlimefunItemStack("ENDERIUM", Material.LIGHT_BLUE_DYE, "&bEnderium"), RecipeType.SMELTERY, new ItemStack[] {
				SlimefunItems.ALUMINUM_DUST, SlimefunItems.ALUMINUM_DUST, SlimefunItems.ALUMINUM_DUST, SlimefunItems.SILVER_DUST, SlimefunItems.SILVER_DUST, SlimefunItems.COPPER_DUST, RESONANT_ENDER_DUST.getItem(), RESONANT_ENDER_DUST.getItem(), null
		}).registerChain(plugin);
		ENDERIUM_BLOCK = new EndrexItem(CATEGORY_RESOURCES, new SlimefunItemStack("ENDERIUM_BLOCK", Material.LIGHT_BLUE_CONCRETE, "&bEnderium Block"), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				ENDERIUM.getItem(), ENDERIUM.getItem(), ENDERIUM.getItem(),
				ENDERIUM.getItem(), ENDERIUM.getItem(), ENDERIUM.getItem(),
				ENDERIUM.getItem(), ENDERIUM.getItem(), ENDERIUM.getItem()
		}).registerChain(plugin);
		DRAGON_SCALE = new EndrexItem(CATEGORY_RESOURCES, new SlimefunItemStack("DRAGON_SCALE", Material.BLACK_DYE, "&eDragon Scale", "", "&7Press F for Ender Dragon"), EndrexRecipeType.KILL_ENDER_DRAGON, new ItemStack[0]).registerChain(plugin);
		REINFORCED_DRAGON_SCALE = new EndrexItem(CATEGORY_RESOURCES, (SlimefunItemStack) new ItemStackWrapper(new SlimefunItemStack("REINFORCED_DRAGON_SCALE", Material.BLACK_DYE, "&eReinforced Dragon Scale", "", "&7Now harder than before")).addEnchant(Enchantment.DURABILITY, 0).addFlag(ItemFlag.HIDE_ENCHANTS).getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				ENDERIUM_BLOCK.getItem(), ENDERIUM_BLOCK.getItem(), ENDERIUM_BLOCK.getItem(),
				DRAGON_SCALE.getItem(), DRAGON_SCALE.getItem(), DRAGON_SCALE.getItem(),
				SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE
		}).registerChain(plugin);
		{
			SlimefunItemStack oreblock = new SlimefunItemStack("MYSTHERIUM_ORE", Material.PURPLE_CONCRETE, "&5Mystherium Ore", "", "&7Very mysterious block");
			MYSTHERIUM = new EndrexItem(CATEGORY_RESOURCES, new SlimefunItemStack("MYSTHERIUM", Material.PURPLE_DYE, "&5Mystherium", "", "&7Very mysterious thing"), RecipeType.ORE_CRUSHER, new ItemStack[] {oreblock, null, null, null, null, null, null, null, null}).registerChain(plugin);
			MYSTHERIUM_ORE = new EndrexMineableResource(CATEGORY_RESOURCES, oreblock, 0.09, MYSTHERIUM.getItem()).registerChain(plugin);
		}
		MYSTHERIUM_BUCKET = new LiquidBucket(CATEGORY_RESOURCES, new SlimefunItemStack("MYSTHERIUM_BUCKET", PlayerHead.getItemStack(EndrexSkulls.MYSTHERIUM_BUCKET), "&bBucket of Mystherium Liquid", "", "&7No you can't pour liquid", "&7yet :(")).registerChain(plugin);
	}
	private static void initWeapons(Endrex plugin) {
		ENDERIUM_SWORD = new EndrexEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, (SlimefunItemStack) new ItemStackWrapper(new SlimefunItemStack(
				"ENDERIUM_SWORD",
				Material.IRON_SWORD,
				"&bEnderium Sword",
				"&7Pearl Stealer I",
				"",
				"&fHas a higher chance to get",
				"&fEnder Pearl from Enderman"
				))
				.addEnchant(Enchantment.DAMAGE_ALL, 1)
				.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
						null, ENDERIUM.getItem(), null,
						null, ENDERIUM.getItem(), null,
						null, new ItemStack(Material.STICK), null
		}).addHandlerChain(EndrexItems.pearlStealer(0.40)).registerChain(plugin);
		EMPOWERED_ENDERIUM_SWORD = new EndrexEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, (SlimefunItemStack) new ItemStackWrapper(new SlimefunItemStack(
				"EMPOWERED_ENDERIUM_SWORD",
				Material.IRON_SWORD,
				"&bEmpowered Enderium Sword",
				"&7Pearl Stealer II",
				"",
				"&fHas a even higher chance to get",
				"&fEnder Pearl from Enderman"
				))
				.addEnchant(Enchantment.DAMAGE_ALL, 3)
				.addEnchant(Enchantment.DURABILITY, 1)
				.getItem(), RecipeType.ANCIENT_ALTAR, new ItemStack[] {
						SlimefunItems.ENDER_LUMP_2, ENDERIUM.getItem(), SlimefunItems.ENDER_LUMP_2,
						ENDERIUM.getItem(), ENDERIUM_SWORD.getItem(), ENDERIUM.getItem(),
						SlimefunItems.ENDER_LUMP_2, ENDERIUM.getItem(), SlimefunItems.ENDER_LUMP_2
		}).addHandlerChain(EndrexItems.pearlStealer(0.75)).registerChain(plugin);
		
		MYSTERIOUS_SWORD = new MysteriousEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, new SlimefunItemStack(
				"MYSTERIOUS_SWORD",
				Material.IRON_SWORD,
				"&dMysterious Sword",
				EndrexLoreBuilder.CLICK_TO_UNLOCK,
				"",
				"&fGet a random enchantments",
				"&feverytime you craft it."
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				null, MYSTHERIUM.getItem(), null,
				null, MYSTHERIUM.getItem(), null,
				null, new ItemStack(Material.STICK), null
		}).addEnchantment(
				// Spagetti code
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.20, 1, 1),
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.12, 2, 4),
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.05, 5, 6),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_ALL, 0.08, 1, 2),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_ALL, 0.05, 3, 3),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_ALL, 0.02, 4, 6),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_UNDEAD, 0.03, 1, 2),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_ARTHROPODS, 0.03, 1, 2),
				new RandomEnchantmentEntry(Enchantment.FIRE_ASPECT, 0.01, 1, 2),
				
				new RandomEnchantmentEntry(Enchantment.MENDING, 0.01, 1, 1)
				).registerChain(plugin);
		MYSTERIOUS_PICKAXE = new MysteriousEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, new SlimefunItemStack(
				"MYSTERIOUS_PICKAXE",
				Material.IRON_PICKAXE,
				"&dMysterious Pickaxe",
				EndrexLoreBuilder.CLICK_TO_UNLOCK,
				"",
				"&fGet a random enchantments",
				"&feverytime you craft it."
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				MYSTHERIUM.getItem(), MYSTHERIUM.getItem(), MYSTHERIUM.getItem(),
				null, new ItemStack(Material.STICK), null,
				null, new ItemStack(Material.STICK), null
		}).addEnchantment(
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.20, 1, 1),
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.12, 2, 4),
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.05, 5, 6),
				new RandomEnchantmentEntry(Enchantment.DIG_SPEED, 0.15, 1, 2),
				new RandomEnchantmentEntry(Enchantment.DIG_SPEED, 0.09, 3, 5),
				new RandomEnchantmentEntry(Enchantment.DIG_SPEED, 0.06, 6, 8),
				new RandomEnchantmentEntry(Enchantment.LOOT_BONUS_BLOCKS, 0.10, 1, 1),
				new RandomEnchantmentEntry(Enchantment.LOOT_BONUS_BLOCKS, 0.07, 2, 4),
				new RandomEnchantmentEntry(Enchantment.LOOT_BONUS_BLOCKS, 0.03, 5, 6),

				new RandomEnchantmentEntry(Enchantment.MENDING, 0.01, 1, 1),
				new RandomEnchantmentEntry(Enchantment.SILK_TOUCH, 0.01, 1, 2) // Silk Touch II confirmed by Notch
				).registerChain(plugin);
		MYSTERIOUS_AXE = new MysteriousEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, new SlimefunItemStack(
				"MYSTERIOUS_AXE",
				Material.IRON_AXE,
				"&dMysterious Axe",
				EndrexLoreBuilder.CLICK_TO_UNLOCK,
				"",
				"&fGet a random enchantments",
				"&feverytime you craft it."
				), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				MYSTHERIUM.getItem(), MYSTHERIUM.getItem(), null,
				MYSTHERIUM.getItem(), new ItemStack(Material.STICK), null,
				null, new ItemStack(Material.STICK), null
		}).addEnchantment(
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.20, 1, 1),
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.12, 2, 4),
				new RandomEnchantmentEntry(Enchantment.DURABILITY, 0.05, 5, 6),
				new RandomEnchantmentEntry(Enchantment.DIG_SPEED, 0.15, 1, 2),
				new RandomEnchantmentEntry(Enchantment.DIG_SPEED, 0.09, 3, 5),
				new RandomEnchantmentEntry(Enchantment.DIG_SPEED, 0.06, 6, 8),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_ALL, 0.08, 1, 2),
				new RandomEnchantmentEntry(Enchantment.DAMAGE_ALL, 0.05, 3, 3),

				new RandomEnchantmentEntry(Enchantment.MENDING, 0.01, 1, 1),
				new RandomEnchantmentEntry(Enchantment.SILK_TOUCH, 0.01, 1, 2) // Silk Touch II confirmed by Jeb
				).registerChain(plugin);
	}
	private static void initEquipments(Endrex plugin) {
		ELYTRA_OF_THE_LITTLE_DRAGON = new EndrexEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, (SlimefunItemStack) new ItemStackWrapper(new SlimefunItemStack("ELYTRA_OF_THE_LITTLE_DRAGON", Material.ELYTRA, "&eElytra of The Little Dragon", "", "&7It's hard to name items...")).addEnchant(Enchantment.DURABILITY, 2).getItem(), RecipeType.ANCIENT_ALTAR, new ItemStack[] {
				SlimefunItems.AIR_RUNE, SlimefunItems.ELYTRA_SCALE, SlimefunItems.AIR_RUNE,
				SlimefunItems.AIR_RUNE, new ItemStack(Material.LEATHER_CHESTPLATE), SlimefunItems.AIR_RUNE,
				DRAGON_SCALE.getItem(), SlimefunItems.ELYTRA_SCALE, DRAGON_SCALE.getItem()
		}).registerChain(plugin);
		REINFORCED_ELYTRA = new EndrexEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, (SlimefunItemStack) new ItemStackWrapper(new SlimefunItemStack("REINFORCED_ELYTRA", Material.ELYTRA, "&eReinforced Elytra")).addEnchant(Enchantment.DURABILITY, 5).getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				null, SlimefunItems.ELYTRA_SCALE, null,
				REINFORCED_DRAGON_SCALE.getItem(), new ItemStack(Material.ELYTRA), REINFORCED_DRAGON_SCALE.getItem(),
				SlimefunItems.REINFORCED_PLATE, SlimefunItems.ELYTRA_SCALE, SlimefunItems.REINFORCED_PLATE
		}).registerChain(plugin);
		MASK_OF_ENDER = new EndrexEquipment(CATEGORY_WEAPONS_AND_EQUIPMENTS, new SlimefunItemStack("MASK_OF_ENDER", Material.LEATHER_HELMET, Color.AQUA, "&bMask of Ender", "", "&7It must be something", "&7special..."), RecipeType.ARMOR_FORGE, new ItemStack[] {
				new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.ENDER_PEARL),
				new ItemStack(Material.ENDER_EYE), new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.ENDER_EYE),
				null, null, null
		}).registerChain(plugin);
	}
	private static void initInterestingThings(Endrex plugin) {
		END_RESPAWN_ANCHOR = new EndRespawnAnchor(CATEGORY_MISCELLANEOUS, new SlimefunItemStack("END_RESPAWN_ANCHOR", PlayerHead.getItemStack(EndrexSkulls.RESPAWN_ANCHOR), "&eEnd Respawn Anchor", "", "&7Allow you to respawn in", "&7The End.", "&7Must provide it with", "&7Enderium blocks to make", "&7it works"), RecipeType.ANCIENT_ALTAR, new ItemStack[] {
				SlimefunItems.ENDER_LUMP_3, new ItemStack(Material.END_STONE), SlimefunItems.ENDER_RUNE,
				new ItemStack(Material.END_STONE), new ItemStack(Material.ENCHANTING_TABLE), new ItemStack(Material.END_STONE),
				SlimefunItems.ENDER_RUNE, new ItemStack(Material.END_STONE), SlimefunItems.ENDER_LUMP_3
		}).registerChain(plugin);
		MYSTERIOUS_TELEPORTER = new MysteriousTeleporter(CATEGORY_MISCELLANEOUS, new SlimefunItemStack("MYSTERIOUS_TELEPORTER", PlayerHead.getItemStack(EndrexSkulls.MYSTERIOUS_TELEPORTER), "&dMysterious Teleporter", "", "&7Where could you teleport to?", "&7Cost 1 Mystherium per", "&7teleportation."), RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
				MYSTHERIUM.getItem(), new ItemStack(Material.CHORUS_FLOWER), MYSTHERIUM.getItem(),
				SlimefunItems.ENDER_LUMP_2, SlimefunItems.MAGIC_EYE_OF_ENDER, SlimefunItems.ENDER_LUMP_2,
				new ItemStack(Material.END_STONE), MYSTHERIUM.getItem(), new ItemStack(Material.END_STONE)
		}).registerChain(plugin);
		MYSTERIOUS_TELEPORTER_LINKER = new EndrexItem(CATEGORY_MISCELLANEOUS, new SlimefunItemStack("MYSTERIOUS_TELEPORTER_LINKER", Material.BLAZE_ROD, "&dMysterious Teleporter Linker", "", "&7Link 2 mysterious teleporters", "&7together.", "&7Destroy after use", "", "&eRight click &7to first teleporter", "&7to bind location.", "&eRight click &7to second teleporter", "&7to link."), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
				null, new ItemStack(Material.ENDER_EYE), null,
				null, new ItemStack(Material.BLAZE_ROD), null,
				null, SlimefunItems.ENDER_LUMP_2, null
		}).registerChain(plugin);
	}
	
	private static EntityKillHandler pearlStealer(double chance) {
		return (e, entity, killer, item) -> {
			ThreadLocalRandom random = ThreadLocalRandom.current();
			if (e.getEntityType() == EntityType.ENDERMAN && random.nextDouble() <= 0.6D) {
				e.getDrops().add(new ItemStack(Material.ENDER_PEARL));
			}
		};
	}
}