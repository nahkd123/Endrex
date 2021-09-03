package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.Schematic;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.loot.LootTableEntry;

public class MysteryBoxesGenerator extends StructuresGenerator {

	Schematic schem;
	List<LootTableEntry> lootTable;
	
	public MysteryBoxesGenerator() {
		schem = Endrex.getSchematic("structures/other/mysterybox.nsm");
		lootTable = Arrays.asList(
				new LootTableEntry(0.02, new ItemStack(Material.ENDER_PEARL, 3)),
				new LootTableEntry(0.03, new ItemStack(Material.ENDER_PEARL, 2)),
				new LootTableEntry(0.03, new ItemStack(Material.ENDER_PEARL, 1)),
				
				new LootTableEntry(0.02, new ItemStack(Material.EXPERIENCE_BOTTLE, 6)),
				new LootTableEntry(0.02, new ItemStack(Material.EXPERIENCE_BOTTLE, 3)),
				new LootTableEntry(0.03, new ItemStack(Material.EXPERIENCE_BOTTLE, 2)),
				
				new LootTableEntry(0.02, new CustomItemStack(EndrexItems.ENDERIUM.getItem(), 1)),
				new LootTableEntry(0.01, new CustomItemStack(EndrexItems.ENDERIUM.getItem(), 2)),

				new LootTableEntry(0.04, new ItemStack(Material.DIAMOND, 1)),
				new LootTableEntry(0.03, new ItemStack(Material.DIAMOND, 2)),
				new LootTableEntry(0.01, EndrexItems.ENDERIUM_SWORD.getItem()),
				new LootTableEntry(0.02, EndrexItems.MASK_OF_ENDER.getItem()),
				new LootTableEntry(0.02, SlimefunItems.AIR_RUNE),
				new LootTableEntry(0.03, SlimefunItems.ENDER_RUNE),
				new LootTableEntry(0.02, SlimefunItems.GOLD_16K),
				new LootTableEntry(0.01, SlimefunItems.BLANK_RUNE)
				);
	}
	
	@Override
	public void generateStructure(World world, Chunk newChunk, Random rand) {
		if (world.getEnvironment() != Environment.THE_END) return;
		if (newChunk.getX() >= -32 && newChunk.getZ() >= -32 && newChunk.getX() <= 32 && newChunk.getZ() <= 32) return;
		int mx = rand.nextInt(13);
		for (int i = 0; i < mx; i++) rand.nextInt();
		double hit = rand.nextDouble();
		if (hit <= 0.03) {
			int genX = rand.nextInt(9) + (newChunk.getX() * 16),
				genZ = rand.nextInt(9) + (newChunk.getZ() * 16),
				y = 100 + rand.nextInt(32);
			if (world.getBlockAt(genX, y, genZ).getBiome() == Biome.SMALL_END_ISLANDS) return;
			schem.pasteSchematic(world, new VectorInt(genX, y, genZ), lootTable);
		}
	}

}
