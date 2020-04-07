package me.nahkd.spigot.sfaddons.endrex.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public class InventoryUtils {
	
	public static boolean isNotAir(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
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
	
}
