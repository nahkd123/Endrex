package me.nahkd.spigot.sfaddons.endrex.items.misc;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

public class EndRespawnAnchor extends EndrexItem {

	public EndRespawnAnchor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
		
		addItemHandler(new BlockPlaceHandler(true) {
			@Override
			public void onPlayerPlace(BlockPlaceEvent event) {
				BlockStorage.addBlockInfo(event.getBlock(), "stage", "0");
			}
		}, (BlockUseHandler) (event) -> {
			if (event.getClickedBlock().isPresent()) {
				Block b = event.getClickedBlock().get();
				Player player = event.getPlayer();
				ItemStack hand = event.getItem();
				Config dat = BlockStorage.getLocationInfo(b.getLocation());
				if (b.getWorld().getEnvironment() != Environment.THE_END) {
					event.cancel();
					// if (player.getPersistentDataContainer().has(worlds.get(b.getWorld()), PersistentDataType.STRING)) player.getPersistentDataContainer().remove(worlds.get(b.getWorld()));
					BlockStorage.clearBlockInfo(b);
					b.setType(Material.AIR);
					b.getWorld().createExplosion(b.getLocation(), 6.9F, true);
					player.sendMessage("§7And yes, it doesn't work in here.");
					// And no, stop telling me to use ChatColor thing, I'll use Alt + 21 instead.
					return;
				}
				int stage = dat.contains("stage")? Integer.parseInt(dat.getString("stage")) : 0;
				if (InventoryUtils.isNotAir(hand) && SlimefunUtils.isItemSimilar(hand, EndrexItems.ENDERIUM_BLOCK.getItem(), true) && stage < 4) { 
					stage++;
					setTextureByStage(stage, b);
					InventoryUtils.consumeHand(player.getInventory(), event.getHand(), 1);
					dat.setValue("stage", stage + "");
					b.getWorld().playSound(b.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 0.8F + (stage / 4F * 0.2F));
					event.cancel();
					return;
				}
				player.sendMessage("Respawn Anchor: Changed respawn point for this world");
				player.getPersistentDataContainer().set(worlds.get(b.getWorld()), PersistentDataType.STRING, b.getX() + ":" + b.getY() + ":" + b.getZ());
			}
		});
	}
	
	
	public void setTextureByStage(int stage, Block block) {
	    if (stage < 0 || stage > 4) throw new IllegalArgumentException("invaild texture stage (" + stage + ")");
		// EndrexUtils.setSkullFromHash(block, EndrexSkulls.RESPAWN_ANCHOR_STATES[stage]);
	    PlayerHead.setSkin(block, EndrexSkulls.RESPAWN_ANCHOR_STATES[stage], true);
	}
	
	private static HashMap<World, NamespacedKey> worlds;
	public static void init(Endrex plugin) {
		worlds = new HashMap<World, NamespacedKey>();
		// Some servers may have more than 1 world with "The End" enviroment
		for (World world : plugin.getServer().getWorlds()) if (world.getEnvironment() == Environment.THE_END) {
			worlds.put(world, new NamespacedKey(plugin, "respawn_anchor_" + world.getName()));
		}
	}
	public static NamespacedKey getKeyWorldWorld(World world) {return worlds != null? worlds.get(world) : null;}
	
	public EndRespawnAnchor registerChain(SlimefunAddon addon) {register(addon); return this;}
}
