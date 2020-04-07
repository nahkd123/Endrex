package me.nahkd.spigot.sfaddons.endrex.structures;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;

import me.nahkd.spigot.sfaddons.endrex.Endrex;
import me.nahkd.spigot.sfaddons.endrex.schem2.nahkdSchematic2;

public class MysteryBoxesGenerator extends StructuresGenerator {

	nahkdSchematic2 schem;
	public MysteryBoxesGenerator() {
		schem = Endrex.getSchematic("structures/other/mysterybox.nsm");
	}
	
	@Override
	public void generateStructure(World world, Chunk newChunk, Random rand) {
	}

}
