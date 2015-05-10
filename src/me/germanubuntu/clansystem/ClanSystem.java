package me.germanubuntu.clansystem;

import java.io.File;

import lombok.Getter;
import me.germanubuntu.clansystem.clan.ClanManager;
import me.germanubuntu.clansystem.commands.BasicCommandListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanSystem extends JavaPlugin{
	
	@Getter
	private static ClanManager clanManager;
	@Getter
	private static Messages messages;
	@Getter
	private static BasicCommandListener commandListener;
	@Getter
	private static Config pluginConfig;
	
	@Override
	public void onEnable(){
		if(getServer().getOnlineMode()){
			
			if(!this.getDataFolder().exists()){
				this.getDataFolder().mkdirs();
			}
			
			pluginConfig = new Config(new File(this.getDataFolder().getAbsolutePath()+"/config.yml"));
			pluginConfig.load();
			
			clanManager = ClanManager.getClanManager(this);
			clanManager.loadAllClans();
			
			messages = new Messages();
			messages.loadConfig(new File(this.getDataFolder().getAbsolutePath()+"/messages.yml"));
			
			commandListener = new BasicCommandListener();
			
			getCommand("clan").setExecutor(commandListener);
			getCommandListener().registerListener(new BasicArguments());
			getCommandListener().registerListener(new ClanArguments());
			
			this.getServer().getPluginManager().registerEvents(new EventListener(), this);
			
		}else{
			Bukkit.getLogger().warning("Server is not in online-mode!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable(){
		clanManager.saveAllClans();
	}
	
}
