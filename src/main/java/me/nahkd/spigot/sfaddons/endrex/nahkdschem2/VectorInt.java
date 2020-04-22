package me.nahkd.spigot.sfaddons.endrex.nahkdschem2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Vector from Bukkit uses 40 bytes in memory, while this one only uses 28 bytes (including
 * the 16 bytes minimum)
 * @author nahkd123
 *
 */
public class VectorInt {
	
	// Don't kill me for putting these public. Using direct is a bit faster than
	// using though setter
	public int x, y, z;
	public VectorInt() {
		x = 0;
		y = 0;
		z = 0;
	}
	public VectorInt(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public VectorInt(VectorInt toClone) {
		this.x = toClone.x;
		this.y = toClone.y;
		this.z = toClone.z;
	}
	
	// General setters (about getters? well you got the public fields)
	public VectorInt setX(int x) {this.x = x; return this;}
	public VectorInt setY(int y) {this.y = y; return this;}
	public VectorInt setZ(int z) {this.z = z; return this;} 
	public VectorInt set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public VectorInt set(VectorInt vec) {set(vec.x, vec.y, vec.z); return this;}
	
	// Math
	public VectorInt add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	public VectorInt add(VectorInt vec) {
		x += vec.x; y += vec.y; z += vec.z;
		return this;
	}
	public VectorInt neg() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	public static VectorInt min(VectorInt a, VectorInt b) {return new VectorInt(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));}
	public static VectorInt max(VectorInt a, VectorInt b) {return new VectorInt(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));}
	
	// For hashmap thing
	@Override
	public int hashCode() {
		return x + y * 15 + z * 31;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof VectorInt)) return false;
		final VectorInt obj2 = (VectorInt) obj;
		return x == obj2.x && y == obj2.y && z == obj2.z;
	}
	
	// Size thing
	public int getWidth() {return x;}
	public int getHeight() {return y;}
	public int getLength() {return z;}
	public VectorInt setWidth(int w) {x = w; return this;}
	public VectorInt setHeight(int h) {y = h; return this;}
	public VectorInt setLength(int l) {z = l; return this;}
	public int getVolume() {return x * y * z;}
	
	// Binary
	public void writeToStream_16bits(OutputStream stream) throws IOException {
		stream.write(new byte[] {
				(byte) (x >> 8),
				(byte) (x - ((x >> 8) << 8)),
				(byte) (y >> 8),
				(byte) (y - ((y >> 8) << 8)),
				(byte) (z >> 8),
				(byte) (z - ((z >> 8) << 8))
		});
	}
	public static VectorInt readFromStream_16bits(InputStream stream) throws IOException {
		byte[] bs = new byte[6];
		BinaryUtils.readToByteArray(stream, bs);
		return new VectorInt((bs[0] << 8) + bs[1], (bs[2] << 8) + bs[3], (bs[4] << 8) + bs[5]);
	}
	
	// Other
	public static VectorInt fromLocation(Location loc) {return new VectorInt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());}
	public Location toLocation(World world) {return new Location(world, x, y, z);}
	@Override
	protected Object clone() throws CloneNotSupportedException {return new VectorInt(x, y, z);}
	
}
