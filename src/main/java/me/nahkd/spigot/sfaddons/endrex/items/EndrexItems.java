package me.nahkd.spigot.sfaddons.endrex.items;

import java.util.HashMap;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

public class EndrexItems {
	
	protected static HashMap<Class<? extends SlimefunItem>, SlimefunItem> items;
	public static void init() {
		items = new HashMap<Class<? extends SlimefunItem>, SlimefunItem>();
	}
	public static void destroy() {
		items.clear();
		items = null;
	}
	
}
