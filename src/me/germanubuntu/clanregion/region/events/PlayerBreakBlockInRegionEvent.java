package me.germanubuntu.clanregion.region.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBreakBlockInRegionEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private Block block;
	private Player destroyer;
	
	public PlayerBreakBlockInRegionEvent(Block block, Player destroyer){
		cancelled = false;
		this.block = block;
		this.destroyer = destroyer;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Player getDestroyer(){
		return destroyer;
	}
	
	public Block getBlock(){
		return block;
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
