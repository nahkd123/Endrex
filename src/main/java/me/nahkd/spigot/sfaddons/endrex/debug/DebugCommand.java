package me.nahkd.spigot.sfaddons.endrex.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nahkd.spigot.sfaddons.endrex.handlers.ChunksEventsHandlers;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.Region;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.VectorInt;
import me.nahkd.spigot.sfaddons.endrex.nahkdschem2.Schematic;
import me.nahkd.spigot.sfaddons.endrex.structures.StructuresGenerator;

public class DebugCommand implements CommandExecutor {

	World spW;
	VectorInt sp1;
	VectorInt sp2;
	Schematic clip;
	
	public DebugCommand() {
		spW = null;
		sp1 = sp2 = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender instanceof Player && sender.hasPermission("endrex.debug")) {
			Player p = (Player) sender;
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("sp1, sp2, savesp, loadsp, copy, paste, cangenerate <size X and Z>, genchunk");
			} else if (args[0].equalsIgnoreCase("cangenerate")) {
				int x = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				p.sendMessage(StructuresGenerator.canSafelyGenerate(new VectorInt(x, 0, z), p.getLocation().subtract(0, 1, 0)) + "");
			} else if (args[0].equalsIgnoreCase("sp1")) {
				Location f = p.getTargetBlock(null, 7).getLocation();
				sp1 = VectorInt.fromLocation(f);
				spW = f.getWorld();
			} else if (args[0].equalsIgnoreCase("sp2")) {
				Location f = p.getTargetBlock(null, 7).getLocation();
				sp2 = VectorInt.fromLocation(f);
				spW = f.getWorld();
			} else if (args[0].equalsIgnoreCase("savesp")) {
				try {
					FileOutputStream o = new FileOutputStream(new File("gay.nsm"));
					clip.writeToStream(o);
					o.close();
					sender.sendMessage("saved as /gay.nsm");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (args[0].equalsIgnoreCase("loadsp")) {
				try {
					FileInputStream i = new FileInputStream(new File("gay.nsm"));
					clip = new Schematic().fromStream(i);
					i.close();
					sender.sendMessage("loaded from /gay.nsm");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (args[0].equalsIgnoreCase("copy")) {
				clip = new Schematic().fromRegion(new Region(spW, sp1, sp2));
			} else if (args[0].equalsIgnoreCase("paste")) {
				clip.pasteSchematic(p.getWorld(), VectorInt.fromLocation(p.getLocation()));
			} else if (args[0].equalsIgnoreCase("genchunk")) {
				ChunksEventsHandlers.chunkGen(p.getWorld().getChunkAt(p.getLocation()));
				p.sendMessage("done");
			}
			return true;
		}
		return false;
	}

}
