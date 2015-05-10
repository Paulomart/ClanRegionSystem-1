package me.germanubuntu.clansystem;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class Config {
	
	@Getter
	private File file;
	@Getter
	private int maxClans, maxNameLenght, maxContractionLenght;
	
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
			config.set("MaxClans", 999999999);
			config.set("MaxNameLenght", 8);
			config.set("MaxContractionLenght", 4);
			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
		maxClans = config.getInt("MaxClans");
		maxNameLenght = config.getInt("MaxNameLenght");
		maxContractionLenght = config.getInt("MaxContractionLenght");
	}
	
}
