package me.germanubuntu.clansystem.clan.events;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanLoadEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private String name, contraction;
	private UUID owner, clanUUID;
	private List<UUID> users;
	private int kills, deaths;
	private HashMap<String, Object> values;
	private Location home;
	
	public ClanLoadEvent(String name, String contraction, UUID owner, List<UUID> uuids, UUID clanUUID, Location home, int kills, int deaths, HashMap<String, Object> values){
		this.name = name;
		this.contraction = contraction;
		this.owner = owner;
		this.users = uuids;
		this.clanUUID = clanUUID;
		this.home = home;
		this.kills = kills;
		this.deaths = deaths;
		this.values = values;
	}
	
	public String getName(){
		return name;
	}
	
	public String getContraction(){
		return contraction;
	}
	
	public UUID getOwner(){
		return owner;
	}
	
	public List<UUID> getUsers(){
		return users;
	}
	
	public UUID getClanUUID(){
		return clanUUID;
	}
	
	public Location getHome(){
		return home;
	}
	
	public int getDeaths(){
		return deaths;
	}
	
	public int getKills(){
		return kills;
	}
	
	public HashMap<String, Object> getValues(){
		return values;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
