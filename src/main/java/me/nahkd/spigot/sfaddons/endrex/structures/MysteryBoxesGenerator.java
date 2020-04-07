package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.inventory.ItemStack;

import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.schem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.schem2.nahkdSchematic2;
import me.nahkd.spigot.sfaddons.endrex.schem2.loot.LootTableEntry;

public class MysteryBoxesGenerator extends StructuresGenerator {

	nahkdSchematic2 schem;
	public MysteryBoxesGenerator() {
		schem = Endrex.getSchematic("structures/other/mysterybox.nsm");
	}
	
	@Override
	public void generateStructure(World world, Chunk newChunk, Random rand) {
		if (world.getEnvironment() != Environment.THE_END) return;
		if (newChunk.getX() >= -32 && newChunk.getZ() >= -32 && newChunk.getX() <= 32 && newChunk.getZ() <= 32) return;
		int mx = rand.nextInt(16);
		for (int i = 0; i < mx; i++) rand.nextInt();
		double hit = rand.nextDouble();
		if (hit <= 0.25) {
			int
				genX = rand.nextInt(13) + (newChunk.getX() * 16),
				genZ = rand.nextInt(13) + (newChunk.getZ() * 16);
			// Location loc = new Location(world, genX, 100 + rand.nextInt(32), genZ);
			Bukkit.broadcastMessage("mystery box " + genX + " - " + genZ);
			schem.pasteSchematic(world, new VectorInt(genX, 100 + rand.nextInt(32), genZ), Arrays.asList(
					new LootTableEntry(0.5, new ItemStack(Material.ENDER_PEARL, 3)),
					new LootTableEntry(0.6, new ItemStack(Material.ENDER_PEARL, 2)),
					new LootTableEntry(0.7, new ItemStack(Material.ENDER_PEARL, 1))
					));
		}
	}

}
