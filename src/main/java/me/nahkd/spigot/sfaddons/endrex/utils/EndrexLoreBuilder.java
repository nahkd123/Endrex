package me.nahkd.spigot.sfaddons.endrex.utils;

public class EndrexLoreBuilder {
	
	private static final String liquidCapacitySymbol = "\u2045 \u2046";
	
	public static String liquidCapacity(int capacity) {return "&8\u21E8 &e" + liquidCapacitySymbol + " &7Liquid Capacity: &e" + capacity;}
	public static String liquidPerTick(int mbPerTick) {return "&8\u21E8 &b" + liquidCapacitySymbol + " &b" + mbPerTick + " &7MB/tick";}
	public static String clickToUnlock() {return "&7Click to unlock";}
	
}
