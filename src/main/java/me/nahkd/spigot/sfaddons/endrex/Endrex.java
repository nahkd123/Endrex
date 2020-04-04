package me.nahkd.spigot.sfaddons.endrex;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkullItems;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.Liquids;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;

public class Endrex extends JavaPlugin implements SlimefunAddon {

	private static boolean syncBlockChange;
	public static boolean allowSyncBlockChange() {return syncBlockChange;}
	
    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        /*
        // Slimefun4 also already comes with a bundled version of bStats
        // You can use bStats to collect usage data about your plugin
        // More info: https://bstats.org/getting-started
        // Set bStatsId to the id of your plugin
        int bStatsId = -1;
        new Metrics(this, bStatsId);
        */ // TODO remove messy comments
        CommandSender logger = getServer().getConsoleSender();
        long timer = System.currentTimeMillis();
        
        // Config
        syncBlockChange = cfg.getBoolean("performance.syncMachineBlockChange");
        
        EndrexSkullItems.init();
        EndrexRecipeType.init(this);
        Liquids.init(this);
        EndrexItems.init(this);
        
        Liquids.postInit();
        
        logger.sendMessage("§3[Endrex] §bPlugin enabled in " + (System.currentTimeMillis() - timer) + "ms");
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {return "https://github.com/nahkd123/Endrex/issues";}
    @Override
    public JavaPlugin getJavaPlugin() {return this;}

}
