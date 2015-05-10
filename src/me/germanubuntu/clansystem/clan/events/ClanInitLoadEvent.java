package me.germanubuntu.clansystem.clan.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanInitLoadEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
	public ClanInitLoadEvent(){
		cancelled = false;
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
