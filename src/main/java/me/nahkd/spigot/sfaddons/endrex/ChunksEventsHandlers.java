package me.nahkd.spigot.sfaddons.endrex;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import me.nahkd.spigot.sfaddons.endrex.structures.SpongePoweredHouses;

public class ChunksEventsHandlers implements Listener {
	
	SpongePoweredHouses spongePowered;
	
	public ChunksEventsHandlers() {
		spongePowered = new SpongePoweredHouses();
	}
	
	@EventHandler
	public void chunkLoad(ChunkLoadEvent event) {
		if (event.isNewChunk()) {
			// Generate structure here
			spongePowered.generateStructure(event.getWorld(), event.getChunk(), new Random((event.getWorld().getSeed() >> 3) + event.getChunk().getX() + event.getChunk().getZ()));
		}
	}
	
}
