package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;

import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.Schematic;

public class ForestsGenerator extends StructuresGenerator {

	List<Schematic> trees;
	
	public ForestsGenerator() {
		trees = new ArrayList<Schematic>();
		trees.add(Endrex.getSchematic("structures/magictree/0.nsm"));
		trees.add(Endrex.getSchematic("structures/magictree/1.nsm"));
		trees.add(Endrex.getSchematic("structures/magictree/2.nsm"));
	}
	
	@Override
	public void generateStructure(World world, Chunk newChunk, Random rand) {
		if (world.getEnvironment() != Environment.THE_END) return;
		if (newChunk.getX() >= -32 && newChunk.getZ() >= -32 && newChunk.getX() <= 32 && newChunk.getZ() <= 32) return;
		int mx = rand.nextInt(16);
		for (int i = 0; i < mx; i++) rand.nextInt();
		double forestHit = rand.nextDouble();
		if (forestHit <= 0.08) {
			int genX = rand.nextInt(13) + (newChunk.getX() * 16),
				genZ = rand.nextInt(13) + (newChunk.getZ() * 16);
			int trees = rand.nextInt(5) + 2;
			for (int treeIndex = 0; treeIndex < trees; treeIndex++) {
				Block highestBlock = world.getHighestBlockAt(genX, genZ);
				Location loc = highestBlock.getLocation();
				Schematic treeSchem = this.trees.get(rand.nextInt(this.trees.size()));
				if (canSafelyGenerate(treeSchem.size, loc, Arrays.asList(Material.END_STONE)))
					treeSchem.pasteSchematic(world, new VectorInt(genX, highestBlock.getY(), genZ), null, Arrays.asList(Material.AIR));
				genX += rand.nextInt(20) - 3;
				genZ += rand.nextInt(20) - 3;
			}
		}
	}

}
