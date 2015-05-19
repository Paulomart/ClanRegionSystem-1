package me.germanubuntu.clanregion.region.events;

import me.germanubuntu.clanregion.region.ClanRegion;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionDestroyEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private ClanRegion region;
	private Player destroyer;
	
	public RegionDestroyEvent(ClanRegion region, Player destroyer){
		cancelled = false;
		this.region = region;
		this.destroyer = destroyer;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Player getDestroyer(){
		return destroyer;
	}
	
	public ClanRegion getRegion(){
		return region;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
