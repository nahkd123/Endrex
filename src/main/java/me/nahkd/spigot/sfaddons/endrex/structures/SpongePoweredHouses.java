package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;

import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.schem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.schem2.nahkdSchematic2;

public class SpongePoweredHouses extends StructuresGenerator {

	nahkdSchematic2 schem;
	
	public SpongePoweredHouses() {
		schem = Endrex.getSchematic("structures/other/SpongePowered.nsm");
	}
	
	@Override
	public void generateStructure(World world, Chunk newChunk, Random rand) {
		if (world.getEnvironment() != Environment.THE_END) return;
		// 10% will get sponge house
		if (rand.nextDouble() <= 0.10) {
			int
					genX = rand.nextInt(13) + (newChunk.getX() * 16),
					genZ = rand.nextInt(13) + (newChunk.getZ() * 16);
			Location loc = new Location(world, genX, 10, genZ);
			Block highestBlock = getHighestY(loc);
			if (canSafelyGenerate(schem, highestBlock.getLocation())) {
				schem.pasteSchematic(world, new VectorInt(genX, highestBlock.getY(), genZ));
			}
		}
	}

}
