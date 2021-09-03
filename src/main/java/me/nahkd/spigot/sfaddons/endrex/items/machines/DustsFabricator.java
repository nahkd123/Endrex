package me.nahkd.spigot.sfaddons.endrex.items.machines;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItem;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.CustomLiquid;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.LiquidStorage;
import me.nahkd.spigot.sfaddons.endrex.utils.InventoryUtils;

@SuppressWarnings("deprecation")
public class DustsFabricator extends EndrexItem implements EnergyNetComponent, InventoryBlock {
    
    private static HashMap<CustomLiquid, Integer> inputs_mbPerItem = new HashMap<CustomLiquid, Integer>();
    private static HashMap<CustomLiquid, ItemStack> inputs_output = new HashMap<CustomLiquid, ItemStack>();

    private static HashMap<Block, CustomLiquid> processing = new HashMap<Block, CustomLiquid>();
    private static HashMap<Block, Integer> processMbLeft = new HashMap<Block, Integer>();
    
    private final int mbPerTick;
    private final int jPerTick;
    private final int liquidCapacity;
    
    public DustsFabricator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int mbPerTick, int jPerTick, int liquidCapacity) {
        super(category, item, recipeType, recipe);
        this.mbPerTick = mbPerTick;
        this.jPerTick = jPerTick;
        this.liquidCapacity = liquidCapacity;
        
        createPreset(this, "&8Dusts Fabricator", this::menuPreset);
        addItemHandler(new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                Block block = e.getBlock();
                BlockMenu inv = BlockStorage.getInventory(block);
                if (inv != null) {
                    // Drop items in inventory
                    InventoryUtils.dropItem(DustsFabricator.this, block);
                    drops.clear();
                    
                    processing.remove(block);
                    processMbLeft.remove(block);
                }
            }
        });
    }
    
    public static void addRecipe(CustomLiquid type, int mbPerItem, ItemStack output) {
        inputs_mbPerItem.put(type, mbPerItem);
        inputs_output.put(type, output);
    }
    
    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {
            
            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                try {
                    onTick(b, item, data);
                } catch (Exception e) {
                    error("o no! an exception thrown", e);
                }
            }
            
            @Override
            public boolean isSynchronized() {return false;}
        });
    }
    
    private static final ItemStack VANILLA_BUCKET = new ItemStack(Material.BUCKET);
    private void onTick(Block b, SlimefunItem sfi, Config data) {
        BlockMenu inv = BlockStorage.getInventory(b);
        CustomLiquid liquid = null;
        int mb = 0;
        if (data.contains("mb") && data.contains("liquidtype") && (mb = LiquidStorage.getMills(b)) > 0) {
            // liquid = CustomLiquid.getLiquidByKey(data.getString("liquidtype"));
            liquid = LiquidStorage.getLiquidType(b);
        }
        // int oldMb = mb;
        
        // If the input is a bucket, we'll take it instantly
        ItemStack input = inv.getItemInSlot(getInputSlots()[0]);
        ItemStack output = inv.getItemInSlot(16);
        if (input != null && VANILLA_BUCKET.isSimilar(input) && mb >= 1000 && (output == null || output.getType() == Material.AIR || SlimefunUtils.isItemSimilar(output, liquid.bucket, true))) {
            // Empty bucket, "pour" to it
            inv.consumeItem(getInputSlots()[0]);
            mb -= 1000;
            inv.pushItem(liquid.bucket, 16);
        } else {
            // Check if the bucket is vaild
            // The ItemStack do override hashCode, but only works with same item amount
            for (CustomLiquid otherLiquid : CustomLiquid.getAllLiquids()) if (
                otherLiquid.hasBucket() &&
                SlimefunUtils.isItemSimilar(input, otherLiquid.bucket, true) &&
                (liquid == null || mb <= 0 || liquid.equals(otherLiquid)) &&
                mb + 1000 <= liquidCapacity &&
                (output == null || output.getType() == Material.AIR || SlimefunUtils.isItemSimilar(output, VANILLA_BUCKET, true)) &&
                inputs_output.containsKey(otherLiquid)
                ) {
                mb += 1000;
                if (liquid == null) liquid = otherLiquid;
                inv.consumeItem(getInputSlots()[0]);
                inv.pushItem(VANILLA_BUCKET, 16);
                break;
            }
        }
        
        int mbLeft = 0;
        // Process
        if (getCharge(b.getLocation()) >= jPerTick) {
            if (processMbLeft.containsKey(b)) {
                CustomLiquid processingLiquid = processing.get(b);
                int processingLeft = processMbLeft.get(b);
                processingLeft -= mbPerTick;
                if (processingLeft <= 0) {
                    ItemStack outputItem = new ItemStack(inputs_output.get(processingLiquid));
                    inv.pushItem(outputItem, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
                    processing.remove(b);
                    processMbLeft.remove(b);
                } else processMbLeft.put(b, processingLeft);
                mbLeft = processingLeft;
                removeCharge(b.getLocation(), jPerTick);
            } else if (liquid != null && mb > 0) {
                // Consume liquid
                int mbPerItem = inputs_mbPerItem.get(liquid);
                if (mb >= mbPerItem) {
                    mb -= mbPerItem;
                    processing.put(b, liquid);
                    processMbLeft.put(b, mbPerItem);
                }
            }
        }
        
        if (mb > liquidCapacity) mb = liquidCapacity;
        if (liquid != null) {
            LiquidStorage.setLiquidType(b, liquid);
            LiquidStorage.setMills(b, mb);
        }
        
        if (liquid == null || (mb <= 0 && mbLeft <= 0)) {
            if (!GUI_NOLIQUID.isSimilar(inv.getItemInSlot(13))) inv.replaceExistingItem(13, GUI_NOLIQUID);
        } else {
            String name = liquid.defaultDisplay.hasItemMeta() && liquid.defaultDisplay.getItemMeta().hasDisplayName()? liquid.defaultDisplay.getItemMeta().getDisplayName() : "&cI forgot to add the name for this liquid :|";
            inv.replaceExistingItem(13, new CustomItemStack(
                liquid.defaultDisplay,
                name,
                "",
                "&7Capacity: &e" + mb + "&7/" + liquidCapacity + " MB",
                "&7Processing: &e" + mbLeft + " &7MB"
            ));
        }
    }

    private static final int[] BGINPUTSLOTS = {0, 1, 2, 9, 11, 18, 19, 20};
    private static final int[] BGINFOSLOTS = {3, 4, 5, 12, 14, 21, 22, 23};
    private static final int[] BGOUTPUTSLOTS = {6, 7, 8, 15, 17, 24, 25, 26};
    private static final int[] BGOUTPUTSLOTS_DUSTS = {45, 46, 47, 48, 49, 50, 51, 52, 53};
    
    private static final CustomItemStack GUI_INPUT = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aBucket Input");
    private static final CustomItemStack GUI_OUTPUT = new CustomItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&bBucket Output");
    private static final CustomItemStack GUI_OUTPUT_DUSTS = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&bDusts Output");
    private static final CustomItemStack GUI_WAIT = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "&ePlease wait", "&7Maybe about 0.25s");
    private static final CustomItemStack GUI_NOLIQUID = new CustomItemStack(Material.BARRIER, "&cNo Liquid", "&7Put a bucket of liquid", "&7in the input slot and take", "&7it from the bucket output", "&7slot.");
    
    private void menuPreset(BlockMenuPreset preset) {
        for (int s : BGINPUTSLOTS) preset.addItem(s, GUI_INPUT, ChestMenuUtils.getEmptyClickHandler());
        for (int s : BGINFOSLOTS) preset.addItem(s, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        for (int s : BGOUTPUTSLOTS) preset.addItem(s, GUI_OUTPUT, ChestMenuUtils.getEmptyClickHandler());
        for (int s : BGOUTPUTSLOTS_DUSTS) preset.addItem(s, GUI_OUTPUT_DUSTS, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(13, GUI_WAIT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public EnergyNetComponentType getEnergyComponentType() {return EnergyNetComponentType.CONSUMER;}

    @Override
    public int getCapacity() {return 128;}
    
    public DustsFabricator registerChain(SlimefunAddon plugin) {
        register(plugin);
        return this;
    }

    
    @Override
    public int[] getInputSlots() {return new int[] {10};}

    @Override
    public int[] getOutputSlots() {return new int[] {16, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};}
    
}
