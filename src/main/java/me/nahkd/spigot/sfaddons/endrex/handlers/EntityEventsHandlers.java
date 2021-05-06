package me.nahkd.spigot.sfaddons.endrex.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

public class EntityEventsHandlers implements Listener {
	
	@EventHandler
	public void enderDragonDed(EntityDeathEvent event) {
		if (event.getEntityType() == EntityType.ENDER_DRAGON) {
			for (int i = 0; i < 16; i++) {
				Location location = new Location(event.getEntity().getWorld(), -20 + (Math.random() * 40), 75, -20 + (Math.random() * 40));
				event.getEntity().getWorld().dropItem(location, EndrexItems.DRAGON_SCALE.getItem());
			}
		}
	}
	
	@EventHandler
	public void triggeringEnderman(EntityTargetEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN && event.getTarget() instanceof Player) {
			Player player = (Player) event.getTarget();
			Enderman endy = (Enderman) event.getEntity();
			ItemStack helmet = player.getInventory().getHelmet();
			if (event.getReason() == TargetReason.CLOSEST_PLAYER && InventoryUtils.isNotAir(helmet) && SlimefunUtils.isItemSimilar(helmet, EndrexItems.MASK_OF_ENDER.getItem(), false)) {
				event.setCancelled(true);
				endy.setTarget(null);
			}
		}
	}
	
}
