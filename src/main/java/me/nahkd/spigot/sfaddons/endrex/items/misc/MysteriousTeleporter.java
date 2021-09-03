package me.nahkd.spigot.sfaddons.endrex.items.misc;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

public class MysteriousTeleporter extends EndrexItem {

	private static NamespacedKey linkKey;
	public static void init(Endrex plugin) {
		linkKey = new NamespacedKey(plugin, "linkto");
	}
	
	public MysteriousTeleporter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
		
		addItemHandler((BlockUseHandler) (event)-> {
			// Since this uses skull, we can use TileState ...
			// ... or use the config thing?
			if (event.getClickedBlock().isPresent()) {
				Block block = event.getClickedBlock().get();
				Config data = BlockStorage.getLocationInfo(block.getLocation());
				ItemStack hand = event.getItem();
				if (EndrexItems.MYSTERIOUS_TELEPORTER_LINKER.isItem(hand)) {
					if (data.contains("linkto")) {
						event.getPlayer().sendMessage("Unable to use: This teleporter has been linked to another one.");
						return;
					}
					ItemMeta meta = hand.getItemMeta();
					String locStr = block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
					if (!meta.getPersistentDataContainer().has(linkKey, PersistentDataType.STRING)) {
						meta.getPersistentDataContainer().set(linkKey, PersistentDataType.STRING, locStr);
						hand.setItemMeta(meta);
						InventoryUtils.setHand(event.getPlayer().getInventory(), event.getHand(), hand);
						event.getPlayer().sendMessage("Mysterious Teleporter Linker: Linked from the linker to the teleporter.");
						return;
					}
					String linkStr = meta.getPersistentDataContainer().get(linkKey, PersistentDataType.STRING);
					if (locStr.equalsIgnoreCase(linkStr)) {
						event.getPlayer().sendMessage("Unable to use: You cannot link to the same teleporter.");
						return;
					}
					String[] strings = linkStr.split(":");
					
					Block destBlock = Bukkit.getWorld(strings[0]).getBlockAt(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
					if (!BlockStorage.hasBlockInfo(destBlock)) {
						event.getPlayer().sendMessage("Unable to use: Cannot find the previously linked teleporter, self-destructing...");
						InventoryUtils.consumeHand(event.getPlayer().getInventory(), event.getHand(), 1);
						return;
					}
					Config anotherData = BlockStorage.getLocationInfo(destBlock.getLocation());
					data.setValue("linkto", linkStr);
					anotherData.setValue("linkto", locStr);
					event.getPlayer().sendMessage("Mysterious Teleporter Linker: Linked from the teleporter to another teleporter.");
					InventoryUtils.consumeHand(event.getPlayer().getInventory(), event.getHand(), 1);
					return;
				} else if (EndrexItems.MYSTHERIUM.isItem(hand)) {
					Location location = null;
					if (!data.contains("linkto")) {
						// Retry 100 times, if not success will ask player to move to new location
						for (int i = 0; i < 100; i++) {
							int x = Endrex.getRandomizer().nextInt(1000) - 500;
							int z = Endrex.getRandomizer().nextInt(1000) - 500;
							Block dest = block.getWorld().getHighestBlockAt(x, z);
							if (dest.getType() == Material.AIR || dest.getY() < 0) {
								if (i < 99) continue;
								event.getPlayer().sendMessage("§7It seem like it doesn't do anything... How about moving it to new location?");
								return;
							}
							dest = dest.getRelative(BlockFace.UP);
							String srcStr = block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
							String destStr = block.getWorld().getName() + ":" + x + ":" + dest.getY() + ":" + z;
							data.setValue("linkto", destStr);
							location = dest.getLocation();
							dest.setType(Material.PLAYER_HEAD);
							//EndrexUtils.setSkullFromHash(dest, EndrexSkulls.MYSTERIOUS_TELEPORTER_HASH);
							PlayerHead.setSkin(block, EndrexSkulls.MYSTERIOUS_TELEPORTER, true);
							BlockStorage.setBlockInfo(dest.getLocation(), "{\"id\":\"" + getId() + "\",\"linkto\":\"" + srcStr +"\"}", false);
							break;
						}
					} else {
						String[] strings = data.getString("linkto").split(":");
						location = new Location(Bukkit.getWorld(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
					}
					InventoryUtils.consumeHand(event.getPlayer().getInventory(), event.getHand(), 1);
					event.cancel();
					event.getPlayer().teleport(location, TeleportCause.PLUGIN);
					event.getPlayer().playSound(location, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
				}
			}
		});
		addHandlerChain(new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                drops.clear();
                Config data = BlockStorage.getLocationInfo(e.getBlock().getLocation());
                if (data.contains("linkto")) {
                    String[] strings = data.getString("linkto").split(":");
                    Block destBlock = Bukkit.getWorld(strings[0]).getBlockAt(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                    
                    BlockStorage.clearBlockInfo(destBlock);
                    destBlock.setType(Material.AIR);
                    
                    BlockStorage.clearBlockInfo(e.getBlock());
                    e.getBlock().setType(Material.AIR);
                    e.setCancelled(true);
                }
            }
        });
		
	}
	
	public MysteriousTeleporter registerChain(SlimefunAddon addon) {
		super.registerChain(addon);
		return this;
	}

}
