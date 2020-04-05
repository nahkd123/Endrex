package me.nahkd.spigot.sfaddons.endrex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

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
import me.nahkd.spigot.sfaddons.endrex.debug.DebugCommand;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.Liquids;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;
import me.nahkd.spigot.sfaddons.endrex.schem2.nahkdSchematic2;
import me.nahkd.spigot.sfaddons.endrex.schem2.ext.SchematicExtension;

public class Endrex extends JavaPlugin implements SlimefunAddon {

	private static boolean syncBlockChange;
	private static Endrex instance;
	public static boolean allowSyncBlockChange() {return syncBlockChange;}
	public static Endrex getRunningInstance() {return instance;}
	
	private static HashMap<String, nahkdSchematic2> loadedSchemas;
	public static nahkdSchematic2 getSchematic(String path) {
		return loadedSchemas.get(path);
	}
	
	private static File cacheFolder;
	public static File getCacheFolder() {return cacheFolder;}
	
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
        instance = this;
        long timer = System.currentTimeMillis();
        
        // Folders and stuffs
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        cacheFolder = new File(getDataFolder(), "cache");
        if (!cacheFolder.exists()) cacheFolder.mkdir();
        
        loadedSchemas = new HashMap<String, nahkdSchematic2>();
        logger.sendMessage("§3[Endrex] §bLoading schematics...");
        loadSchematic("/structures/village0/house0.nsm");
        loadSchematic("/structures/village0/central.nsm");
        loadSchematic("/structures/other/SpongePowered.nsm");
        
        // Events handlers
        getServer().getPluginManager().registerEvents(new ChunksEventsHandlers(), this);
        
        // Commands
        getCommand("endrexde").setExecutor(new DebugCommand());
        
        // Config
        syncBlockChange = cfg.getBoolean("performance.syncMachineBlockChange");
        
        SchematicExtension.initDefault(this);
        EndrexSkulls.init();
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
    public InputStream getResource(String path) {
    	return getClassLoader().getResourceAsStream(path);
    }
    public nahkdSchematic2 loadSchematicFromResource(String path) {
    	try {
			return new nahkdSchematic2().fromStream(getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			return new nahkdSchematic2();
		}
    }
    public void loadSchematic(String path) {
    	System.out.println("Loading " + path + " from jar file...");
    	loadedSchemas.put(path, loadSchematicFromResource(path));
    }
    
}
