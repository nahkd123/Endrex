package me.nahkd.spigot.sfaddons.endrex.utils;

/**
 * The lore "builder"
 * @author nahkd123
 *
 */
public class EndrexLoreBuilder {
	
	private static final String liquidCapacitySymbol = "\u2045 \u2046";
	
	/**
	 * Display current liquid capacity
	 * @param capacity
	 * @return
	 */
	public static String liquidCapacity(int capacity) {return "&8\u21E8 &e" + liquidCapacitySymbol + " &7Liquid Capacity: &e" + capacity;}
	
	/**
	 * Display the speed of the machine
	 * @param mbPerTick
	 * @return
	 */
	public static String liquidPerTick(int mbPerTick) {return "&8\u21E8 &b" + liquidCapacitySymbol + " &b" + mbPerTick + " &7MB/tick";}
	
	/**
	 * The "click to unlock" thing, applies to mysterious items
	 */
	public static final String CLICK_TO_UNLOCK = "&7Click to unlock";
	
}
