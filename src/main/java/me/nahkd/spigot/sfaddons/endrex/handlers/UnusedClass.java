package me.nahkd.spigot.sfaddons.endrex.handlers;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * Unused class. Please don't ever register this in official build.
 * @author nahkd123
 *
 */
public class UnusedClass implements Listener {
	
	@EventHandler
	public void projectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntityType() == EntityType.SHULKER_BULLET) {
			event.setCancelled(true);
		}
	}
	
}
