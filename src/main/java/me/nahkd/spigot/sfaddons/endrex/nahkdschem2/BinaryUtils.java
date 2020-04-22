package me.nahkd.spigot.sfaddons.endrex.nahkdschem2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is about stuffs in binary
 * @author nahkd123
 *
 */
public class BinaryUtils {
	
	public static void write_16bits(OutputStream stream, int val) throws IOException {
		stream.write(new byte[] {
				(byte) (val >> 8),
				(byte) (val - ((val >> 8) << 8))
		});
	}
	public static void write_ansii_nulTerminated(OutputStream stream, String str) throws IOException {
		byte[] out = new byte[str.length() + 1]; int index = 0;
		for (char c : str.toCharArray()) {
			out[index] = (byte) c;
			index++;
		}
		out[out.length - 1] = 0x00;
		stream.write(out);
	}
	public static boolean matchHeader(byte[] header, byte[] toMatch) throws IOException {
		if (header.length != toMatch.length) return false;
		for (int i = 0; i < header.length; i++) if (header[i] != toMatch[i]) return false;
		return true;
	}
	public static boolean matchHeader(byte[] header, InputStream stream) throws IOException {
		byte[] bs = new byte[header.length]; 
		readToByteArray(stream, bs);
		return matchHeader(header, bs);
	} // We should call this file signature instead
	
	public static void readToByteArray(InputStream stream, byte[] bs) throws IOException {
		int index = 0; int i;
		try {
			while (index < bs.length) {
				if (stream.available() > 0) {
					i = stream.read(bs, index, bs.length - index);
					index += i;
				}
			}
		} catch (IOException e) {
			// Assume that it's closed, we'll print stack trace and then exit
			e.printStackTrace();
			return;
		}
	}
	
	public static int read_16bits(InputStream stream) throws IOException {
		int i1 = stream.read(), i2 = stream.read();
		if (i1 == -1 || i2 == -1) return 0;
		return (i1 << 8) + i2;
	}
	public static String read_ansii_nulTerminated(InputStream stream) throws IOException {
		StringBuilder str = new StringBuilder();
		int i;
		while ((i = stream.read()) != 0x00 && i != -1) str.append((char) i);
		return str.toString();
	}
	public static void write_byteArray(OutputStream stream, byte[] arr) throws IOException {
		write_16bits(stream, arr.length);
		stream.write(arr);
	}
	public static byte[] read_byteArray(InputStream stream) throws IOException {
		int length = read_16bits(stream);
		byte[] bs = new byte[length];
		readToByteArray(stream, bs);
		return bs;
	}
	
	public static void write_utf8String(OutputStream stream, String str) throws IOException {write_byteArray(stream, str.getBytes());}
	public static String read_utf8String(InputStream stream) throws IOException {return new String(read_byteArray(stream));}
}
