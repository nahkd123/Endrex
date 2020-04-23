package me.nahkd.spigot.sfaddons.endrex.nahkdschem2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.ext.CustomBlockData;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.ext.SchematicExtension;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.loot.LootTableEntry;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.loot.LootTables;

/**
 * Welcome to nahkdSchematic2! This one is suppose to be inside your plugin, rather than
 * being a plugin. This one is lightweight, can be shaded into your plugin if you want
 * to. Soon I'll add this to nahkdAPI2...
 * @author nahkd123
 *
 */
public class Schematic {
	
	HashMap<Integer, Material> mappedIDs;
	HashMap<Material, Integer> mappedMaterials;
	
	/**
	 * The size of the schematic. You can modify this, but don't set it to null,
	 * otherwise it will casue error.
	 */
	public VectorInt size;
	short[] schemData; // We'll use short, because it saves memory
	short[] blockStates; // States of block, something like stairs and stuffs
	HashMap<VectorInt, CustomBlockData> customs;
	
	/**
	 * Create an empty schematic
	 */
	public Schematic() {
		mappedIDs = new HashMap<Integer, Material>();
		mappedMaterials = new HashMap<Material, Integer>();
		customs = new HashMap<VectorInt, CustomBlockData>();
		size = new VectorInt();
		schemData = null;
	}
	
	/**
	 * Load the schematic from region in your world. It then can be used anywhere.
	 * @param reg The region to load
	 * @return this
	 */
	public Schematic fromRegion(Region reg) {
		// Make sure we've cleared maps and reseted schematic data
		mappedIDs.clear();
		mappedMaterials.clear();
		customs.clear();
		schemData = new short[reg.size.getVolume()];
		size = new VectorInt(reg.size);
		
		int nextId = 0;
		List<Block> bs = reg.getBlocks();
		List<BlockData> states = new ArrayList<BlockData>();
		int id;
		int index = 0;
		for (Block b : bs) {
			if (!mappedMaterials.containsKey(b.getType())) {
				mappedIDs.put(id = nextId, b.getType());
				mappedMaterials.put(b.getType(), nextId);
				nextId++;
			} else id = mappedMaterials.get(b.getType());
			
			schemData[index] = (short) id;
			index++;
			if (isSpecial(b.getBlockData())) states.add(b.getBlockData());
			SchematicExtension ext = SchematicExtension.getExtensionToSerialize(b);
			if (ext != null) try {
				ByteArrayOutputStream ser = new ByteArrayOutputStream();
				ext.serialize(ser, b);
				VectorInt locVec = VectorInt.fromLocation(b.getLocation()).add(new VectorInt(reg.location).neg());
				CustomBlockData customData = new CustomBlockData(locVec, ext, ser.toByteArray());
				customs.put(locVec, customData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		blockStates = new short[states.size()];
		int outputState;
		index = 0;
		for (BlockData state : states) {
			outputState = 0;
			if (state instanceof Directional) outputState += directionToNumberThing(((Directional) state).getFacing());
			if (state instanceof Bisected) outputState += halfToNumberThing(((Bisected) state).getHalf());
			if (state instanceof Stairs) outputState += cornerToNumberThing(((Stairs) state).getShape()) << 8;
			if (state instanceof Levelled) outputState += ((Levelled) state).getLevel() << 8;
			blockStates[index] = (short) outputState;
			index++;
		}
		return this;
	}
	
	// #2
	private static byte directionToNumberThing(BlockFace dir) {
		switch (dir) {
		case DOWN: 						return 0b00000000;
		case EAST: 						return 0b00000001;
		case EAST_NORTH_EAST: 			return 0b00000010;
		case EAST_SOUTH_EAST: 			return 0b00000011;
		case NORTH: 					return 0b00000100;
		case NORTH_EAST: 				return 0b00000101;
		case NORTH_NORTH_EAST: 			return 0b00000110;
		case NORTH_NORTH_WEST: 			return 0b00000111;
		case NORTH_WEST: 				return 0b00001000;
		case SELF: 						return 0b00001001;
		case SOUTH: 					return 0b00001010;
		case SOUTH_EAST: 				return 0b00001011;
		case SOUTH_SOUTH_EAST: 			return 0b00001100;
		case SOUTH_SOUTH_WEST: 			return 0b00001101;
		case SOUTH_WEST: 				return 0b00001110;
		case UP: 						return 0b00001111;
		case WEST: 						return 0b00010000;
		case WEST_NORTH_WEST: 			return 0b00010001;
		case WEST_SOUTH_WEST: 			return 0b00010000;
		default: 						return 0b00000000;
		}
	}
	private static BlockFace numberToDirection(int number) {
		number = number & 0b00011111;
		if (number == 0b000_00000) return BlockFace.DOWN;
		if (number == 0b000_00001) return BlockFace.EAST;
		if (number == 0b000_00010) return BlockFace.EAST_NORTH_EAST;
		if (number == 0b000_00011) return BlockFace.EAST_SOUTH_EAST;
		if (number == 0b000_00100) return BlockFace.NORTH;
		if (number == 0b000_00101) return BlockFace.NORTH_EAST;
		if (number == 0b000_00110) return BlockFace.NORTH_NORTH_EAST;
		if (number == 0b000_00111) return BlockFace.NORTH_NORTH_WEST;
		if (number == 0b000_01000) return BlockFace.NORTH_WEST;
		if (number == 0b000_01001) return BlockFace.SELF;
		if (number == 0b000_01010) return BlockFace.SOUTH;
		if (number == 0b000_01011) return BlockFace.SOUTH_EAST;
		if (number == 0b000_01100) return BlockFace.SOUTH_SOUTH_EAST;
		if (number == 0b000_01101) return BlockFace.SOUTH_SOUTH_WEST;
		if (number == 0b000_01110) return BlockFace.SOUTH_WEST;
		if (number == 0b000_01111) return BlockFace.UP;
		if (number == 0b000_10000) return BlockFace.WEST;
		if (number == 0b000_10001) return BlockFace.WEST_NORTH_WEST;
		if (number == 0b000_10010) return BlockFace.WEST_SOUTH_WEST;
		return BlockFace.NORTH;
	}
	// #2
	private static byte halfToNumberThing(Half half) {
		switch (half) {
		case BOTTOM: 					return 0b00000000;
		case TOP: 						return 0b00100000;
		default: 						return 0b00000000;
		}
	}
	private static Half numberToHalf(int number) {
		number = number & 0b00100000;
		if (number == 0b00_0_00000) return Half.BOTTOM;
		if (number == 0b00_1_00000) return Half.TOP;
		return Half.BOTTOM;
	}
	// #1
	private static byte cornerToNumberThing(Shape shape) {
		switch (shape) {
		case STRAIGHT: 					return 0b00000_000;
		case INNER_LEFT: 				return 0b00000_001;
		case INNER_RIGHT: 				return 0b00000_010;
		case OUTER_LEFT: 				return 0b00000_011;
		case OUTER_RIGHT: 				return 0b00000_100;
		default: 						return 0b00000_000;
		}
	}
	private static Shape numberToCorner(int number) {
		number = number & 0b00000111;
		if (number == 0b000) return Shape.STRAIGHT;
		if (number == 0b001) return Shape.INNER_LEFT;
		if (number == 0b010) return Shape.INNER_RIGHT;
		if (number == 0b011) return Shape.OUTER_LEFT;
		if (number == 0b100) return Shape.OUTER_RIGHT;
		return Shape.STRAIGHT;
	}
	
	private static boolean isSpecial(BlockData dat) {
		return 
				dat instanceof Directional ||
				dat instanceof Bisected ||
				dat instanceof Stairs ||
				dat instanceof Levelled;
	}
	
	/**
	 * Paste the schematic
	 * @param w
	 * @param loc
	 */
	public void pasteSchematic(World w, VectorInt loc) {
		pasteSchematic(w, loc, null, null);
	}
	public void pasteSchematic(World w, VectorInt loc, List<LootTableEntry> lootTable) {
		pasteSchematic(w, loc, lootTable, null);
	}
	/**
	 * Paste the schematic, and replace chest items with loot inside loot table
	 * @param w
	 * @param loc
	 * @param transparent Block that's won't be replaced while pasting, or null for nothing
	 * @param lootTable The loot table, or null for empty chests
	 */
	public void pasteSchematic(World w, VectorInt loc, List<LootTableEntry> lootTable, Collection<Material> transparent) {
		int index = 0;
		int stateIndex = 0;
		VectorInt bloc = new VectorInt();
		
		for (int x = 0; x < size.x; x++) for (int y = 0; y < size.y; y++) for (int z = 0; z < size.z; z++) {
			Block b = bloc.set(x, y, z).add(loc).toLocation(w).getBlock();
			bloc.set(x, y, z);
			if (transparent != null && transparent.contains(mappedIDs.get((int) schemData[index]))) {
				index++;
				continue;
			}
			b.setType(mappedIDs.get((int) schemData[index]));
			index++;
			if (isSpecial(b.getBlockData())) {
				int lmaoayy = blockStates[stateIndex];
				BlockData blockData = b.getBlockData();
				if (blockData instanceof Directional) {
					Directional dir = (Directional) blockData;
					dir.setFacing(numberToDirection(lmaoayy - ((lmaoayy >> 8) << 8)));
				}
				if (blockData instanceof Bisected) {
					Bisected bis = (Bisected) blockData;
					bis.setHalf(numberToHalf(lmaoayy - ((lmaoayy >> 8) << 8)));
				}
				if (blockData instanceof Stairs) {
					Stairs bis = (Stairs) blockData;
					bis.setShape(numberToCorner(lmaoayy >> 8));
				}
				if (blockData instanceof Levelled) {
					Levelled lv = (Levelled) blockData;
					lv.setLevel((lmaoayy >> 8) & 0b0000_1111);
				}
				b.setBlockData(blockData);
				stateIndex++;
			}
			if (b.getState() instanceof Container && lootTable != null) {
				// Add loot to container
				Container container = (Container) b.getState();
				Inventory inv = container.getInventory();
				ItemStack[] is = inv.getContents();
				LootTables.loot(is, lootTable);
				inv.setContents(is);
			}
			if (customs.containsKey(bloc)) try {
				CustomBlockData dat = customs.get(bloc);
				dat.extension.deserialize(new ByteArrayInputStream(dat.data), b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// WARNING: lots of binary stuffs
	private static final byte[] HEADER = new byte[] {0x13, (byte) 0xA0, (byte) 0xA1, 0x11};
	/**
	 * Write the schematic to stream. This also write custom block data (if present) to
	 * the stream as well.
	 * @param stream
	 * @throws IOException if something went wrong (like no write access or something esle)
	 */
	public void writeToStream(OutputStream stream) throws IOException {
		// Write header (0x13A0A111)
		stream.write(HEADER);
		size.writeToStream_16bits(stream);
		// Write id table (we'll use NUL terminated string and ANSII, rather than UTF-8 (also why you want UTF-8 tho?))
		for (Entry<Integer, Material> entry : mappedIDs.entrySet()) {
			stream.write(0xFF); // Indicate that this isn't the end of table
			BinaryUtils.write_16bits(stream, entry.getKey());
			BinaryUtils.write_ansii_nulTerminated(stream, entry.getValue().toString());
		}
		stream.write(0x00); // End of table
		
		// Write the schemData
		for (short s : schemData) BinaryUtils.write_16bits(stream, s);
		// Write block states
		for (short s : blockStates) BinaryUtils.write_16bits(stream, s); 
		
		// write additional data like custom block data
		for (CustomBlockData b : customs.values()) {
			stream.write(0xFF); // Indicator
			b.writeToStream(stream);
		}
		stream.write(0x00); // Indicator (end of custom data table)
	}
	/**
	 * Read the schematic from stream. This will also read custom block data (if present)
	 * to this object.
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public Schematic fromStream(InputStream stream) throws IOException {
		// Make sure we've cleared maps and reseted schematic data
		mappedIDs.clear();
		mappedMaterials.clear();
		customs.clear();
		
		// Block ID table
		if (!BinaryUtils.matchHeader(HEADER, stream)) throw new IOException("Invaild file signature");
		size = VectorInt.readFromStream_16bits(stream);
		schemData = new short[size.getVolume()];
		int i;
		HashSet<Integer> specials = new HashSet<Integer>();
		while ((i = stream.read()) == 0xFF) {
			int id = BinaryUtils.read_16bits(stream);
			Material mat = Material.valueOf(BinaryUtils.read_ansii_nulTerminated(stream));
			BlockData d = mat.createBlockData();
			if (isSpecial(d)) specials.add(id);
			
			mappedIDs.put(id, mat);
			mappedMaterials.put(mat, id);
		}
		if (i == -1) throw new IOException("Corrupted schematic file");
		
		// Read block data and stuffs
		int statesCount = 0;
		int id;
		for (int j = 0; j < size.getVolume(); j++) {
			id = BinaryUtils.read_16bits(stream);
			schemData[j] = (short) id;
			if (specials.contains(id)) statesCount++;
		}
		// Read block states
		blockStates = new short[statesCount];
		for (int j = 0; j < statesCount; j++) {
			blockStates[j] = (short) BinaryUtils.read_16bits(stream);
		}
		
		// Read custom block data table
		while ((i = stream.read()) == 0xFF) {
			CustomBlockData dat = CustomBlockData.readFromStream(stream);
			customs.put(dat.regionLoc, dat);
		}
		return this;
	}
	
}
