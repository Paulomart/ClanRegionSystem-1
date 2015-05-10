package me.germanubuntu.clansystem.clan.events;

import java.util.UUID;

import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanCreateEvent extends Event implements Cancellable{
	
	
	
    private static final HandlerList handlers = new HandlerList();
    private String name;
    private String contraction;
    private Player creator;
    private UUID clanUUID;
    
    private boolean cancelled;
    
    public ClanCreateEvent(String name, String contraction, Player creator, UUID clanUUID) {
    	this.name = name;
    	this.contraction = contraction;
    	this.creator = creator;
    	this.clanUUID = clanUUID;
        cancelled = false;
    }
    
    public UUID getClanUUID(){
    	return clanUUID;
    }
    
    public Player getCreator(){
    	return creator;
    }
    
    public String getContraction() {
        return contraction;
    }
    
    public String getName() {
        return name;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
