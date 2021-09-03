package me.nahkd.spigot.sfaddons.endrex.handlers;

import java.util.List;
import java.util.Optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import me.nahkd.spigot.sfaddons.endrex.items.mysterious.MysteriousEquipment;
import me.nahkd.spigot.sfaddons.endrex.utils.EndrexLoreBuilder;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

public class InventoryEventsHandlers implements Listener {
	
	@EventHandler
	public void click(InventoryClickEvent event) {
		if (event.getClickedInventory() != event.getInventory()) return;
		switch (event.getView().getTitle()) {
		case "Slimefun Guide": return;
		}

		ItemStack itemStack = event.getCurrentItem();
		if (!InventoryUtils.isNotAir(itemStack)) return;
		ItemMeta meta = itemStack.getItemMeta();
		Optional<String> id = Slimefun.getItemDataService().getItemData(meta);
		if (id.isPresent() && MysteriousEquipment.getMappedItems().containsKey(id.get())) {
			MysteriousEquipment equipment = MysteriousEquipment.getMappedItems().get(id.get());
			if (meta.getLore().get(0).equals(EndrexLoreBuilder.CLICK_TO_UNLOCK)) {
				// Add crap to the item
				List<String> lore = meta.getLore();
				lore.set(0, "§7§oMagically created");
				meta.setLore(lore);
				
				equipment.applyEnchantment(meta);
				
				itemStack.setItemMeta(meta);
				event.setCurrentItem(new ItemStack(itemStack));
				event.setCancelled(true);
			}
		}
	} 
	
}
