package me.nahkd.spigot.sfaddons.endrex.handlers;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.structures.ForestsGenerator;
import me.nahkd.spigot.sfaddons.endrex.structures.MysteryBoxesGenerator;
import me.nahkd.spigot.sfaddons.endrex.structures.ResourcesGenerator;
import me.nahkd.spigot.sfaddons.endrex.structures.SpongePoweredHouses;

public class ChunksEventsHandlers implements Listener {
	
	static SpongePoweredHouses spongePowered;
	static ForestsGenerator forestsGenerator;
	static MysteryBoxesGenerator mysteryBoxexGenerator;
	static ResourcesGenerator resourcesGenerator;
	static Random random;
	
	public ChunksEventsHandlers() {
		spongePowered = new SpongePoweredHouses();
		forestsGenerator = new ForestsGenerator();
		mysteryBoxexGenerator = new MysteryBoxesGenerator();
		resourcesGenerator = new ResourcesGenerator();
		random = new Random();
	}
	
	@EventHandler
	public void chunkLoad(ChunkLoadEvent event) {
		if (event.isNewChunk() && Endrex.getRunningInstance().getConfig().getBoolean("options.generate-structure", true)) {
			chunkGen(event.getChunk());
		}
	}
	
	public static void chunkGen(Chunk chunk) {
		// Generate structure here
		random.setSeed((chunk.getWorld().getSeed() >> 3) + (chunk.getX() << 16) + chunk.getZ());
		spongePowered.generateStructure(chunk.getWorld(), chunk, random);
		forestsGenerator.generateStructure(chunk.getWorld(), chunk, random);
		mysteryBoxexGenerator.generateStructure(chunk.getWorld(), chunk, random);
		resourcesGenerator.generateStructure(chunk.getWorld(), chunk, random);
	}
	
}
