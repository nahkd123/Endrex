package me.nahkd.spigot.sfaddons.endrex.nahkdschem2.ext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.bukkit.block.Block;

import me.nahkd.spigot.sfaddons.endrex.Endrex;

/**
 * Add some stuffs to schematic thing (like save custom block for example)
 * @author nahkd123
 *
 */
public abstract class SchematicExtension {
	
	public final int id;
	
	/**
	 * A extension constructor
	 * @param id The ID (to save to file)
	 */
	public SchematicExtension(int id) {
		this.id = id;
		extensions.put(id, this);
	}
	
	/**
	 * Do some stuffs when saving to file
	 * @param stream
	 * @param block
	 * @throws IOException
	 */
	public abstract void serialize(OutputStream stream, Block block) throws IOException;
	/**
	 * Do some stuffs when reading from file
	 * @param stream
	 * @param block
	 * @throws IOException
	 */
	public abstract void deserialize(InputStream stream, Block block) throws IOException;
	public abstract boolean serializable(Block b);
	
	protected static HashMap<Integer, SchematicExtension> extensions = new HashMap<Integer, SchematicExtension>();
	public static boolean isSerializable(Block b) {
		for (SchematicExtension e : extensions.values()) if (e.serializable(b)) return true;
		return false;
	}
	/**
	 * Get the extension to serialize.
	 * @param b
	 * @return Either the extension or null if it can't serialize (which may faster than
	 * {@link SchematicExtension#isSerializable(Block)})
	 */
	public static SchematicExtension getExtensionToSerialize(Block b) {
		for (SchematicExtension e : extensions.values()) if (e.serializable(b)) return e;
		return null;
	}
	public static void initDefault(Endrex plugin) {
		new SlimefunSchematicExtension();
	}
	
}
