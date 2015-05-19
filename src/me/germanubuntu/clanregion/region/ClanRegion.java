package me.germanubuntu.clanregion.region;

import java.io.File;

import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.Chunk;
import org.bukkit.Location;

public interface ClanRegion {
	
	public Location getRegionBlock();
	public Chunk getChunk();
	
	public Clan getClan();
	public void setClan(Clan clan);
	
	public void save(File file);
	
}
