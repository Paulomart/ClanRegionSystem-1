package me.germanubuntu.clansystem.clan.events;

import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveClanEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
	private Clan clan;
	private Player player;
	private boolean cancelled;
	
	public PlayerLeaveClanEvent(Clan clan, Player player){
		this.clan = clan;
		this.player = player;
		this.cancelled = false;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Clan getClan(){
		return clan;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
