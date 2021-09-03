package me.nahkd.spigot.sfaddons.endrex.handlers;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.misc.EndRespawnAnchor;

public class PlayerEventsHandlers implements Listener {
	
	private Endrex plugin;
	private NamespacedKey LAST_DEATH_WORLD;
	
	public PlayerEventsHandlers(Endrex plugin) {
		this.plugin = plugin;
		LAST_DEATH_WORLD = new NamespacedKey(plugin, "last_death_world");
	}
	
	@EventHandler
	public void ded(PlayerDeathEvent event) {
		event.getEntity().getPersistentDataContainer().set(LAST_DEATH_WORLD, PersistentDataType.STRING, event.getEntity().getWorld().getName());
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (!player.getPersistentDataContainer().has(LAST_DEATH_WORLD, PersistentDataType.STRING)) return;
		World world = plugin.getServer().getWorld(player.getPersistentDataContainer().get(LAST_DEATH_WORLD, PersistentDataType.STRING));
		if (world == null) return;
		if (world.getEnvironment() == Environment.THE_END && player.getPersistentDataContainer().has(EndRespawnAnchor.getKeyWorldWorld(world), PersistentDataType.STRING)) {
			NamespacedKey key = EndRespawnAnchor.getKeyWorldWorld(world);
			String[] arr = player.getPersistentDataContainer().get(key, PersistentDataType.STRING).split("\\:");
			int x = Integer.parseInt(arr[0]), y = Integer.parseInt(arr[1]), z = Integer.parseInt(arr[2]);
			Block anchor = world.getBlockAt(x, y, z);
			if (BlockStorage.check(anchor, EndrexItems.END_RESPAWN_ANCHOR.getId())) {
				int stage = Integer.parseInt(BlockStorage.getLocationInfo(anchor.getLocation(), "stage"));
				if (stage <= 0) {
					player.sendMessage("§7Feed your anchor next time!");
					player.getPersistentDataContainer().remove(key);
					return;
				}
				Config config = BlockStorage.getLocationInfo(anchor.getLocation());
				stage--;
				config.setValue("stage", stage + "");
				EndrexItems.END_RESPAWN_ANCHOR.setTextureByStage(stage, anchor);
				event.setRespawnLocation(new Location(world, x, y, z));
				return;
			} else {
				player.sendMessage("§7It seems like someone broke your respawn anchor...");
				player.getPersistentDataContainer().remove(key);
				return;
			}
		}
	}
	
}
