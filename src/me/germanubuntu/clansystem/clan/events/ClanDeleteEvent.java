package me.germanubuntu.clansystem.clan.events;

import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanDeleteEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
    private Clan clan;
    private boolean cancelled;
    
    public ClanDeleteEvent(Clan clan) {
        this.clan = clan;
        cancelled = false;
    }
    
    public Clan getClan() {
        return clan;
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
