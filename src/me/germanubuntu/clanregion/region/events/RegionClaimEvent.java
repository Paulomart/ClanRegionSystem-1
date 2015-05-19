package me.germanubuntu.clanregion.region.events;

import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionClaimEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private Clan clan;
	private Chunk chunk;
	private Location claimBlockLocation;
	private Player player;
	
	public RegionClaimEvent(Clan clan, Chunk chunk, Location claimBlockLocation, Player player){
		cancelled = false;
		this.clan = clan;
		this.chunk = chunk;
		this.claimBlockLocation = claimBlockLocation;
		this.player = player;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Location getClaimBlockLocation(){
		return claimBlockLocation;
	}
	
	public Chunk getChunk(){
		return chunk;
	}
	
	public Clan getClan(){
		return clan;
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
