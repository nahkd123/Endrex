package me.nahkd.spigot.sfaddons.endrex.nahkdschem2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

/**
 * Region a.k.a selection
 * @author nahkd123
 *
 */
public class Region {
	
	World world;
	VectorInt size;
	VectorInt location;
	
	public Region(World world) {
		this.world = world;
		size = new VectorInt();
		location = new VectorInt();
	}
	public Region(World world, VectorInt loc1, VectorInt loc2) {
		location = VectorInt.min(loc1, loc2);
		size = VectorInt.max(loc1, loc2).add(new VectorInt(location).neg()).add(1, 1, 1);
		this.world = world;
	}
	/**
	 * A loop
	 * @param consumer
	 */
	public void forEachBlocks(Consumer<VectorInt> consumer) {
		VectorInt loc = new VectorInt();
		for (int x = 0; x < size.x; x++) for (int y = 0; y < size.y; y++) for (int z = 0; z < size.z; z++) {
			loc.set(x, y, z).add(location.x, location.y, location.z);
			consumer.accept(loc);
		}
	}
	/**
	 * Get list of blocks
	 * @return
	 */
	public List<Block> getBlocks() {
		List<Block> bs = new ArrayList<Block>();
		forEachBlocks((loc) -> {bs.add(loc.toLocation(world).getBlock());});
		return bs;
	}
	
	public void visualize(Player player, BlockData data) {
		forEachBlocks((loc) -> {player.sendBlockChange(loc.toLocation(world), data);});
	}
	
}
