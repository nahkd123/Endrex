package me.nahkd.spigot.sfaddons.endrex.items.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;

public class EndrexMineableResource extends EndrexItem {

	@FunctionalInterface
	public static interface OreChunksGenerator {
		public void onGenerate(Chunk chunk, Random randomizer);
	}
	private static Collection<EndrexMineableResource> ores = new HashSet<EndrexMineableResource>();
	public static Collection<EndrexMineableResource> getOres() {
		return ores;
	}
	
	public final double generateChance;
	public OreChunksGenerator generator;
	private ItemStack outputItem;

	public ItemStack getOutputItem() {
		return outputItem;
	}
	
	public EndrexMineableResource(Category category, SlimefunItemStack item, double generateChance, ItemStack output) {
		super(category, item, EndrexRecipeType.RANDOMLY_GENERATED, new ItemStack[0]);
		
		this.generateChance = generateChance;
		this.generator = getGenerator(this, 3, 12, 5);
		this.outputItem = output;
		
		registerBlockHandler(id, (player, block, tool, reason) -> {
			// block.getDrops().clear();
			ItemStack drop = new ItemStack(outputItem);
			int fortune = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
			drop.setAmount((fortune > 0? Endrex.getRandomizer().nextInt(fortune) : 0) + 1);
			block.getWorld().dropItem(block.getLocation(), drop);
			block.setType(Material.AIR);
			BlockStorage.clearBlockInfo(block);
			return false;
		});
	}
	public EndrexMineableResource setGenerator(OreChunksGenerator generator) {
		this.generator = generator;
		return this;
	}
	@Override
	public void postRegister() {
		ores.add(this);
	}
	
	public static OreChunksGenerator getGenerator(EndrexMineableResource resource, int generateMin, int generateMax, int spread) {
		return (chunk, random) -> {
			final int amount = generateMin + random.nextInt(generateMax - generateMin);
			final int basex = random.nextInt(15);
			final int basey = random.nextInt(100);
			final int basez = random.nextInt(15);
			for (int i = 0; i < amount; i++) {
				final int x = Math.max(Math.min(basex + random.nextInt(spread * 2) - spread, 15 ), 0);
				final int y = Math.max(Math.min(basey + random.nextInt(spread * 2) - spread, 100), 0);
				final int z = Math.max(Math.min(basez + random.nextInt(spread * 2) - spread, 15 ), 0);
				if (chunk.getBlock(x, y, z).getType() != Material.END_STONE) continue;
				Block block = chunk.getBlock(x, y, z);
				block.setType(resource.getItem().getType());
				BlockStorage.setBlockInfo(block, "{\"id\":\"" + resource.getID() + "\"}", false);
			}
		};
	}

}
