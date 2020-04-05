package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.nahkd.spigot.sfaddons.endrex.schem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.schem2.nahkdSchematic2;

public abstract class StructuresGenerator {
	
	public abstract void generateStructure(World world, Chunk newChunk, Random rand);
	
	public static boolean canSafelyGenerate(nahkdSchematic2 structure, Location loc) {
		VectorInt l = new VectorInt();
		for (int x = 0; x < structure.size.getWidth(); x++) for (int z = 0; z < structure.size.getLength(); z++) {
			Block b = l.set(x + loc.getBlockX(), loc.getBlockY(), z + loc.getBlockZ()).toLocation(loc.getWorld()).getBlock();
			if (b.getType() == Material.AIR) return false;
		}
		return true;
	}
	public static Block getHighestY(Location loc) {
		loc.setY(255);
		while (loc.getBlock().getType() == Material.AIR) loc.add(0, 1, 0); 
		return loc.getBlock();
	}
	
}
