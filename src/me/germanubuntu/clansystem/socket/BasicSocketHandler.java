package me.germanubuntu.clansystem.socket;

import java.util.UUID;

import me.germanubuntu.clansystem.ClanSystem;
import me.germanubuntu.clansystem.clan.Clan;
import me.germanubuntu.clansystem.socket.event.JsonServerMessageEvent;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.simple.JSONObject;

public class BasicSocketHandler implements Listener{
	
	@EventHandler
	public void jsonSocketEvent(JsonServerMessageEvent event){
		if(event.saltIsRight()){
			if(event.getJSONObject().containsKey("command")){
				String cmd = (String) event.getJSONObject().get("command");
				if(cmd.equalsIgnoreCase("getOwnerUUID")){
					if(event.getJSONObject().containsKey("clan")){
						JSONObject obj = new JSONObject();
						Clan c = ClanSystem.getClanManager().getClan((String) event.getJSONObject().get("clan"));
						if(c != null){
							obj.put("Owner", c.getOwner().toString());
							event.setReturnMessage(obj.toJSONString());
						}
					}	
				}
				if(cmd.equalsIgnoreCase("getOwnerName")){
					if(event.getJSONObject().containsKey("clan")){
						JSONObject obj = new JSONObject();
						Clan c = ClanSystem.getClanManager().getClan((String) event.getJSONObject().get("clan"));
						if(c != null){
							OfflinePlayer p = Bukkit.getOfflinePlayer(c.getOwner());
							obj.put("Owner", p.getName());
							event.setReturnMessage(obj.toJSONString());
						}
					}	
				}
				if(cmd.equalsIgnoreCase("getClanUUID")){
					if(event.getJSONObject().containsKey("clan")){
						JSONObject obj = new JSONObject();
						Clan c = ClanSystem.getClanManager().getClan((String) event.getJSONObject().get("clan"));
						if(c != null){
							obj.put("UUID", c.getUUID().toString());
							event.setReturnMessage(obj.toJSONString());
						}
					}	
				}
				if(cmd.equalsIgnoreCase("getClanName")){
					if(event.getJSONObject().containsKey("clan")){
						JSONObject obj = new JSONObject();
						Clan c = ClanSystem.getClanManager().getClan(UUID.fromString((String) event.getJSONObject().get("clan")));
						if(c != null){
							obj.put("clan", c.getName());
							event.setReturnMessage(obj.toJSONString());
						}
					}	
				}
			}
		}
	}
	
}
