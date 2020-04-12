package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import me.nahkd.spigot.sfaddons.endrex.items.resources.EndrexMineableResource;

public class ResourcesGenerator extends StructuresGenerator {

	@Override
	public void generateStructure(World world, Chunk newChunk, Random rand) {
		int skips = rand.nextInt(9);
		for (int i = 0; i < skips; i++) rand.nextInt();
		for (EndrexMineableResource resource : EndrexMineableResource.getOres()) {
			double hit = rand.nextDouble();
			if (hit < resource.generateChance) {
				// Bukkit.broadcastMessage("yes " + newChunk.getX() + " : " + newChunk.getZ());
				resource.generator.onGenerate(newChunk, rand);
				break;
			}
		}
	}
	
}
