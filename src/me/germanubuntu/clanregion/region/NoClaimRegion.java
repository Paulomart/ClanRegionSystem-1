package me.germanubuntu.clanregion.region;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class NoClaimRegion {
	@Getter
	private int distance;
	@Getter
	private Location location;
	
	protected NoClaimRegion(int distance, Location location){
		this.distance = distance;
		this.location = location;
	}
	
	public void save(File file){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("location.x", location.getBlockX());
		config.set("location.z", location.getBlockY());
		config.set("location.y", location.getBlockZ());
		config.set("location.world", location.getWorld().getName());
		config.set("distance", distance);
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
