package me.germanubuntu.clansystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class Messages {
	
	@Getter
	private String greenPrefix, redPrefix;
	
	@Getter 
	private Map<String, String> values;
	
	protected Messages(){
		greenPrefix = "["+ChatColor.GREEN+"ClanSystem"+ChatColor.WHITE+"]";
		redPrefix  = "["+ChatColor.RED+"ClanSystem"+ChatColor.WHITE+"]";
		
		values = new HashMap<String, String>();
		values.put("ClanCreatedSuccessfullyMessage", greenPrefix+" Clan was created successfully");
		values.put("CommandOnlyForUserMessage", redPrefix+" This command is only for Player!");
		values.put("IsAlreadyInClanMessage", redPrefix+" You are already in a clan!");
		values.put("WriteDeleteMessage", redPrefix+" Write '/clan delete' to delete your clan!");
		values.put("ErrorByCreatingClanMessage", redPrefix+" Cant create this clan!");
		values.put("ErrorByLeavingClanMessage", redPrefix+" You cant leave your clan!");
		values.put("ClanLeaveMessage",  greenPrefix+" You left your clan successfully");
		values.put("CantDeleteYourClanMessage", redPrefix+" You cant delete your clan!");
		values.put("ClanDeleteSuccessfully", greenPrefix+" You deleted your clan successfully");
		values.put("InvitedSuccessfullyMessage", greenPrefix+" Invited player successfully!");
		values.put("CantInvitePlayerMessage", redPrefix+" Cant invite this player!");
		values.put("InvitedMessage", greenPrefix+" You are invited to a clan!((%clan%)) write '/clan join (%clan%)' to join!");
		values.put("NoInviteMessage", redPrefix+" You aren't invited in this clan!");
		values.put("JoinedInClanMessage", greenPrefix+" You joined in this clan!");
		values.put("PlayerAreNowUnInviteMessage", greenPrefix+" this player is  uninvited!");
		values.put("CantUnInvitedMessage", redPrefix+" Cant uninvite this player!");
		values.put("JoinedInClanMessage",  greenPrefix+" You joined in the clan (%clan%)!");
		values.put("CantTeleportToClanHomeMessage",  redPrefix+" Cant teleport to clan home!");
		values.put("TeleportToClanHomeMessage", greenPrefix+" You are now in your clan home!");
		values.put("CantSetClanHomeMessage",  redPrefix+" Cant set clan home!");
		values.put("ClanHomeSetMessage", greenPrefix+" You set your clan home!");
		values.put("CantKickMessage", redPrefix+" Cant kick this player!");
		values.put("PlayerKickedMessage", greenPrefix+" This player left your clan!");
		values.put("NoPermissionsMessage", redPrefix+" You cant use this command!");
		values.put("NoClanMessage", redPrefix+" You need a Clan to use this command!");
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
