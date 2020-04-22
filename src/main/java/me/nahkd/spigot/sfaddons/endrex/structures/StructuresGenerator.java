package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.Schematic;

public abstract class StructuresGenerator {
	
	public abstract void generateStructure(World world, Chunk newChunk, Random rand);

	public static boolean canSafelyGenerate(Schematic structure, Location loc) {
		return canSafelyGenerate(structure.size, loc);
	}
	public static boolean canSafelyGenerate(VectorInt size, Location loc) {
		if (loc.getY() <= 4) return false; // Put sum minimum value
		VectorInt l = new VectorInt();
		for (int x = 0; x < size.getWidth(); x++) for (int z = 0; z < size.getLength(); z++) {
			Block b = l.set(x + loc.getBlockX(), loc.getBlockY(), z + loc.getBlockZ()).toLocation(loc.getWorld()).getBlock();
			if (b.getType() == Material.AIR) return false;
		}
		return true;
	}
	public static boolean canSafelyGenerate(VectorInt size, Location loc, Collection<Material> accepted) {
		if (loc.getY() <= 4) return false; // Put sum minimum value
		VectorInt l = new VectorInt();
		for (int x = 0; x < size.getWidth(); x++) for (int z = 0; z < size.getLength(); z++) {
			Block b = l.set(x + loc.getBlockX(), loc.getBlockY(), z + loc.getBlockZ()).toLocation(loc.getWorld()).getBlock();
			if (!accepted.contains(b.getType())) return false;
		}
		return true;
	}
	public static Block getHighestY(Location loc) {
//		Location anotherOnePlease = loc.clone();
//		anotherOnePlease.setY(estHeight);
//		while (anotherOnePlease.getBlock().getType() == Material.AIR && anotherOnePlease.getBlockY() > 0) anotherOnePlease.add(0, -1, 0);
//		return anotherOnePlease.getBlock();
		Block block = loc.getWorld().getHighestBlockAt(loc);
		if (block.getType() == Material.AIR) block = loc.getWorld().getHighestBlockAt(loc.subtract(0, 1, 0));
		return block;
	}
	
}
