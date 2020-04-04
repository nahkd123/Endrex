package me.nahkd.spigot.sfaddons.endrex.utils;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInventoryUtils {
	
	public static void consumeHand(PlayerInventory inv, EquipmentSlot slot, int amount) {
		ItemStack main = new ItemStack(inv.getItemInMainHand());
		ItemStack off = new ItemStack(inv.getItemInOffHand());
		switch (slot) {
		case HAND:
			inv.setItemInMainHand(changeAmount(main, main.getAmount() - amount));
			return;
		case OFF_HAND:
			inv.setItemInOffHand(changeAmount(off, off.getAmount() - amount));
			return;
		default: break;
		}
	}
	
	public static ItemStack changeAmount(ItemStack source, int amount) {
		source.setAmount(amount);
		return source;
	}
	
}
