package me.germanubuntu.clanregion;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import me.germanubuntu.clanregion.region.ClanRegionManager;
import me.germanubuntu.clansystem.ClanSystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanRegionSystem extends JavaPlugin{
	
	@Getter
	private static Items items;
	@Getter
	private static ClanRegionManager clanRegionManager;
	@Getter
	private static Messages messages;
	@Getter
	private static Config pluginConfig;
	@Getter
	private static ClanRegionSystem plugin;
	
	@Override
	public void onEnable(){
		
		plugin = this;
		
		items = new Items();
		
		if(!this.getDataFolder().exists()){
			this.getDataFolder().mkdirs();
		}
		
		pluginConfig = new Config(new File(this.getDataFolder().getAbsolutePath()+"/config.yml"));
		pluginConfig.load();
		if(pluginConfig.isCustomBlockRecipe()){
			File recipeFile = new File(this.getDataFolder().getAbsolutePath()+"/recipe.yml");
			YamlConfiguration config = null;
			if(!recipeFile.exists()){
				try {
					recipeFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				config = YamlConfiguration.loadConfiguration(recipeFile);
				config.set("craft.1", 20);
				config.set("craft.2", 20);
				config.set("craft.3", 20);
				config.set("craft.4", 57);
				config.set("craft.5", 138);
				config.set("craft.6", 57);
				config.set("craft.7", 57);
				config.set("craft.8", 57);
				config.set("craft.9", 57);
				try {
					config.save(recipeFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			config = YamlConfiguration.loadConfiguration(recipeFile);
			ShapedRecipe recipe = new ShapedRecipe(items.getClaimItem());
			recipe.shape("123", "456" , "789");
			
			recipe.setIngredient('1', Material.getMaterial(config.getInt("craft.1")));
			recipe.setIngredient('2', Material.getMaterial(config.getInt("craft.2")));
			recipe.setIngredient('3', Material.getMaterial(config.getInt("craft.3")));
			recipe.setIngredient('4', Material.getMaterial(config.getInt("craft.4")));
			recipe.setIngredient('5', Material.getMaterial(config.getInt("craft.5")));
			recipe.setIngredient('6', Material.getMaterial(config.getInt("craft.6")));
			recipe.setIngredient('7', Material.getMaterial(config.getInt("craft.7")));
			recipe.setIngredient('8', Material.getMaterial(config.getInt("craft.8")));
			recipe.setIngredient('9', Material.getMaterial(config.getInt("craft.9")));
			Bukkit.addRecipe(recipe);
		}
		
		clanRegionManager = ClanRegionManager.getClanRegionManager(this);
		clanRegionManager.loadAllRegions();
		
		this.getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		messages = new Messages();
		messages.loadConfig(new File(this.getDataFolder().getAbsolutePath()+"/messages.yml"));
		ClanSystem.getCommandListener().registerListener(new ClanRegionArguments());
	}
	
	@Override
	public void onDisable(){
		clanRegionManager.saveAllRegions();
	}
}
