package me.nahkd.spigot.sfaddons.endrex.nahkdschem2.ext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.BinaryUtils;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.VectorInt;

public class CustomBlockData {
	
	public byte[] data;
	public final SchematicExtension extension;
	public VectorInt regionLoc;
	
	public CustomBlockData(VectorInt loc, SchematicExtension extension) {data = new byte[0]; this.extension = extension; regionLoc = loc;}
	public CustomBlockData(VectorInt loc, SchematicExtension extension, byte[] bs) {data = bs; this.extension = extension; regionLoc = loc;}
	
	public void writeToStream(OutputStream stream) throws IOException {
		// Write loc
		regionLoc.writeToStream_16bits(stream);
		// Write ID
		BinaryUtils.write_16bits(stream, extension.id);
		// Write byte array
		BinaryUtils.write_byteArray(stream, data);
	}
	
	public static CustomBlockData readFromStream(InputStream stream) throws IOException {
		VectorInt loc = VectorInt.readFromStream_16bits(stream);
		int schemaID = BinaryUtils.read_16bits(stream);
		SchematicExtension schema = SchematicExtension.extensions.get(schemaID);
		if (schema == null) throw new RuntimeException("Couldn't find schematic extension with ID " + schemaID + ": Perhaps you forgot to install a plugin?");
		return new CustomBlockData(loc, schema, BinaryUtils.read_byteArray(stream)); 
	}
	
}
