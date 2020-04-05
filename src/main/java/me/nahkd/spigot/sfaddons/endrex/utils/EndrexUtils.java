package me.nahkd.spigot.sfaddons.endrex.utils;

import java.util.Base64;

import org.bukkit.block.Block;

import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullBlock;

public class EndrexUtils {
	
	public static void setSkullFromHash(Block b, String hash) {
		final String url = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + hash + "\"}}}";
		final String base64 = Base64.getEncoder().encodeToString(url.getBytes());
		SkullBlock.setFromBase64(b, base64);
	}
	
}
