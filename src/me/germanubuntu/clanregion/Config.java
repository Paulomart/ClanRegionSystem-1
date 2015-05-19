package me.germanubuntu.clanregion;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class Config {
	
	@Getter
	private File file;
	@Getter
	private boolean customBlockRecipe, beaconToChunkCommand, protectChest, protectEntity, protectPlayer, showChunk;
	@Getter
	private int maxRegions;
	
	protected Config(File file){
		this.file = file;
	}
	
	public void load(){
		YamlConfiguration config = null;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(file);
			config.set("CustomBlockRecipe", true);
			config.set("BeaconToChunkCommand", false);
			config.set("ProtectChest", true);
			config.set("ProtectEntity", true);
			config.set("ProtectPlayer", false);
			config.set("ShowChunk", false);
			config.set("MaxRegions", 9999);
			
			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
		customBlockRecipe = config.getBoolean("CustomBlockRecipe");
		beaconToChunkCommand = config.getBoolean("BeaconToChunkCommand");
		protectChest = config.getBoolean("ProtectChest");
		protectEntity = config.getBoolean("ProtectEntity");
		protectPlayer = config.getBoolean("ProtectPlayer");
		showChunk = config.getBoolean("ShowChunk");
		maxRegions = config.getInt("MaxRegions");
	}
	
}
