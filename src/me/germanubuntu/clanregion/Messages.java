package me.germanubuntu.clanregion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class Messages{
	
	@Getter
	private String greenPrefix, redPrefix;
	
	private Map<String, String> values;
	
	public Map<String, String> getValues(){
		return values;
	}
	
	protected Messages(){
		greenPrefix = "["+ChatColor.GREEN+"ClanSystem"+ChatColor.WHITE+"]";
		redPrefix  = "["+ChatColor.RED+"ClanSystem"+ChatColor.WHITE+"]";
		
		values = new HashMap<String, String>();
		values.put("CantClaimRegionMessage", redPrefix+" Cant claim this region!");
		values.put("RegionClaimedMessage", greenPrefix+" Region created successfully!");
		values.put("MustHaveBeaconInHandMessage", redPrefix+" You need a Beacon in your hand!");
		values.put("SelectRegionMessage", greenPrefix+" Select the region!");
		values.put("DestroyerDestroyedRegionMessage", greenPrefix+" You destroyed a region!");
		values.put("DestroyedRegionClanMessage", redPrefix+" One of your region was destroyed!");
		values.put("AntiRegionCreatedMessage", greenPrefix+" A new AntiRegion created!");
	}
	
	public void loadConfig(File f){
		YamlConfiguration config;
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(f);
			this.generateConfig(f, config);
		}
		config = YamlConfiguration.loadConfiguration(f);
		List<String> keys = new ArrayList<String>();
		for (Map.Entry<String,String> entry : values.entrySet()) {
			String key = entry.getKey();
			keys.add(key);
		}
		values.clear();
		for(String key : keys){
			values.put(key, config.getString(key)); 
		}
	}
	
	public void generateConfig(File f, YamlConfiguration config){
		for (Map.Entry<String,String> entry : values.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			config.set(key, value);
		}
		try {
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
