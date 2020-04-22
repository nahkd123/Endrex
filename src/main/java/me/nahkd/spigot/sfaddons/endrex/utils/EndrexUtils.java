package me.nahkd.spigot.sfaddons.endrex.utils;

import java.util.Base64;

import org.bukkit.block.Block;

import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullBlock;

/**
 * Contains utility that I don't know where to put them
 * @author nahkd123
 *
 */
public class EndrexUtils {
	
	/**
	 * Set the skull from texture hash.
	 * @param b The block
	 * @param hash The texture hash
	 */
	public static void setSkullFromHash(Block b, String hash) {
		final String url = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + hash + "\"}}}";
		final String base64 = Base64.getEncoder().encodeToString(url.getBytes());
		SkullBlock.setFromBase64(b, base64);
	}
	
}
