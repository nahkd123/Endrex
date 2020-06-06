package me.nahkd.spigot.sfaddons.endrex;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.nahkd.spigot.sfaddons.endrex.handlers.ChunksEventsHandlers;
import me.nahkd.spigot.sfaddons.endrex.handlers.EntityEventsHandlers;
import me.nahkd.spigot.sfaddons.endrex.handlers.InventoryEventsHandlers;
import me.nahkd.spigot.sfaddons.endrex.handlers.PlayerEventsHandlers;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexItems;
import me.nahkd.spigot.sfaddons.endrex.items.EndrexSkulls;
import me.nahkd.spigot.sfaddons.endrex.items.liquid.Liquids;
import me.nahkd.spigot.sfaddons.endrex.items.misc.EndRespawnAnchor;
import me.nahkd.spigot.sfaddons.endrex.items.misc.MysteriousTeleporter;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.Schematic;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.ext.SchematicExtension;
import me.nahkd.spigot.sfaddons.endrex.recipes.EndrexRecipeType;

public class Endrex extends JavaPlugin implements SlimefunAddon {
    private static Endrex INSTANCE;
    private final CommandSender sender = getServer().getConsoleSender();

	private static boolean syncBlockChange;
	private static Random runtimeRandomizer;
	public static boolean allowSyncBlockChange() {return syncBlockChange;}
	public static Random getRandomizer() {return runtimeRandomizer;}
	
	private static HashMap<String, Schematic> loadedSchemas;
	public static Schematic getSchematic(String path) {
		return loadedSchemas.get(path);
	}
	
	private static File cacheFolder;
	public static File getCacheFolder() {return cacheFolder;}

    @Override
    public void onLoad() {
        /* Reduce hook error */
	    INSTANCE = this;
    }

    @Override
    public void onEnable() {
        long timer = System.currentTimeMillis();

        if (!(new File(getDataFolder(), "config.yml").exists())) {
            saveResource("config.yml", false);
        }
    	reloadConfig();

        runtimeRandomizer = new Random();
        
        // Folders and stuffs
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        cacheFolder = new File(getDataFolder(), "cache");
        if (!cacheFolder.exists()) cacheFolder.mkdir();

        SchematicExtension.initDefault(this);
        loadedSchemas = new HashMap<>();
        sender.sendMessage("§3[Endrex] §bLoading schematics...");
        loadSchematic("structures/village0/house0.nsm");
        loadSchematic("structures/village0/central.nsm");
        loadSchematic("structures/magictree/0.nsm");
        loadSchematic("structures/magictree/1.nsm");
        loadSchematic("structures/magictree/2.nsm");
        loadSchematic("structures/magictree/3.nsm");
        loadSchematic("structures/other/SpongePowered.nsm");
        loadSchematic("structures/other/mysterybox.nsm");
        
        // Config
        syncBlockChange = getConfig().getBoolean("performance.syncMachineBlockChange", true);
        
        // Setting up craps
        EndrexSkulls.init();
        EndrexRecipeType.init(this);
        Liquids.init(this);
        EndrexItems.init(this);
        
        EndRespawnAnchor.init(this);
        MysteriousTeleporter.init(this);
        
        Liquids.postInit();
        
        // Events handlers
        getServer().getPluginManager().registerEvents(new ChunksEventsHandlers(), this);
        getServer().getPluginManager().registerEvents(new EntityEventsHandlers(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventsHandlers(this), this);
        getServer().getPluginManager().registerEvents(new InventoryEventsHandlers(), this);
        // getServer().getPluginManager().registerEvents(new UnusedClass(), this);
        
        sender.sendMessage("§3[Endrex] §bPlugin enabled in " + (System.currentTimeMillis() - timer) + "ms");
    }

    @Override
    public void onDisable() {
        long timer = System.currentTimeMillis();
        CommandSender logger = getServer().getConsoleSender();
        
    	// Set stuffs to null to prevent memory leaking (i guess?)
    	INSTANCE = null;
    	runtimeRandomizer = null;
    	loadedSchemas.clear(); loadedSchemas = null;
        logger.sendMessage("§3[Endrex] §bPlugin disabled in " + (System.currentTimeMillis() - timer) + "ms");
    }

    @Override
    public String getBugTrackerURL() {return "https://github.com/nahkd123/Endrex/issues";}
    @Override
    public JavaPlugin getJavaPlugin() {return this;}
    public Schematic loadSchematicFromResource(String path) {
    	try {
			return new Schematic().fromStream(getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			return new Schematic();
		}
    }
    public void loadSchematic(String path) {
    	sender.sendMessage("Loading " + path + " from jar file...");
    	loadedSchemas.put(path, loadSchematicFromResource(path));
    }

    public static Endrex getInstance() {
	    return INSTANCE;
    }

}
