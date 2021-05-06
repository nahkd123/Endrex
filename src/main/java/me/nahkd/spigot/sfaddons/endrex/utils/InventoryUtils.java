package me.nahkd.spigot.sfaddons.endrex.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * the biscuit guy said that some of these methods no longer needed. idk where to find
 * those, so I decide to create my own method.
 * @author nahkd123
 *
 */
@SuppressWarnings("deprecation")
public class InventoryUtils {
	
	/**
	 * Check if the item is either not null or not air
	 * @param item
	 * @return
	 */
	public static boolean isNotAir(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static void consumeHand(PlayerInventory inv, EquipmentSlot slot, int amount) {
		ItemStack main = new ItemStack(inv.getItemInMainHand());
		ItemStack off = new ItemStack(inv.getItemInOffHand());
		
		// waiting for everyone to use Java 14 so I can use switch expression.
		switch (slot) {
		case HAND: inv.setItemInMainHand(changeAmount(main, main.getAmount() - amount)); return;
		case OFF_HAND: inv.setItemInOffHand(changeAmount(off, off.getAmount() - amount)); return;
		default: break;
		}
	}
	public static void setHand(PlayerInventory inv, EquipmentSlot slot, ItemStack item) {
		switch (slot) {
		case HAND: inv.setItemInMainHand(item); return;
		case OFF_HAND: inv.setItemInOffHand(item); return;
		default: break;
		}
	}
	
	public static ItemStack changeAmount(ItemStack source, int amount) {
		source.setAmount(amount);
		return amount <= 0? null : source;
	}
	
	public static void dropItem(InventoryBlock btype, Block block) {
		BlockMenu inv = BlockStorage.getInventory(block);
		for (int s : btype.getInputSlots()) {
			ItemStack is = inv.getItemInSlot(s);
			if (is != null && is.getType() != Material.AIR) {
				block.getWorld().dropItem(block.getLocation(), is);
				inv.replaceExistingItem(s, null);
			}
		}
		for (int s : btype.getOutputSlots()) {
			ItemStack is = inv.getItemInSlot(s);
			if (is != null && is.getType() != Material.AIR) {
				block.getWorld().dropItem(block.getLocation(), is);
				inv.replaceExistingItem(s, null);
			}
		}
	}
	
	public static String getFriendlyName(ItemStack item) {
		if (!isNotAir(item)) return "Air";
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName()) return meta.getDisplayName();
			else return null;
		} else return null;
	}
	
}
