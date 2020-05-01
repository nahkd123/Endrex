package me.nahkd.spigot.sfaddons.endrex.lang;

import java.util.HashMap;

import me.nahkd.spigot.sfaddons.endrex.utils.EndrexUtils;

public class Localize {
	
	HashMap<String, String> mapped = new HashMap<String, String>();
	
	public Localize(String... lines) {
		String multiLinesKey = null;
		StringBuilder multiLines = null;
		for (String l : lines) {
			if (l.startsWith("#")) continue;
			if (multiLinesKey != null) {
				if (EndrexUtils.trimRight(l).endsWith("\\")) {
					final String trimed = EndrexUtils.trimRight(l);
					multiLinesKey = EndrexUtils.trim(trimed.substring(0, trimed.length() - 1));
					multiLines = new StringBuilder();
					continue;
				}
				String[] pair = l.split("=");
				String key = EndrexUtils.trim(pair[0]);
				String value = EndrexUtils.trim(pair[1]);
				mapped.put(key, value);
			} else {
				if (l.startsWith("    ")) multiLines.append(EndrexUtils.trim(l.substring(4)) + "\n");
				else {
					mapped.put(multiLinesKey, multiLines.toString());
					multiLinesKey = null;
				}
			}
		}
	}
	
	public String get(String key, String def) {return mapped.getOrDefault(key, def);}
	
}
