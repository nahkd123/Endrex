package me.nahkd.spigot.sfaddons.endrex.items;

import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;

public class EndrexSkullItems {

	public static ItemStack PEARL_ORE;
	public static ItemStack RESONANT_ENDER_BUCKET;
	public static ItemStack RESONANT_ENDER;
	public static ItemStack ENHANCED_CRUCIBLE_EMPTY;
	
	public static void init() {
		PEARL_ORE = 				SkullItem.fromHash("bf20120cb26dfe8a92f83a6e842b5b44062932919bd404eed362ccfff5ca33db");
		RESONANT_ENDER_BUCKET = 	SkullItem.fromHash("29d2b425ff0012ab3d98a78384fedcba1a4c2971fbe078ea60c33a44ab5da076");
		RESONANT_ENDER = 			SkullItem.fromHash("92db9e7ebb60b27e9c016fbc13a7d4e76a4ec7b4b3af5cb7976952090ad4cbbd");
		ENHANCED_CRUCIBLE_EMPTY = 	SkullItem.fromHash("6ff1e1b02ea22effb72f025f245871afc87d1e393bf1e42964ff768532bc0106");
	}
	
}
