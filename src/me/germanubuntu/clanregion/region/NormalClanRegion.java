package me.germanubuntu.clanregion.region;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import me.germanubuntu.clansystem.ClanSystem;
import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class NormalClanRegion implements ClanRegion {
	
	private UUID clan;
	private Location regionBlock;
	
	protected NormalClanRegion(UUID clan, Location regionBlock){
		this.clan = clan;
		this.regionBlock = regionBlock;
	}
	
	@Override
	public Location getRegionBlock() {
		return regionBlock;
	}

	@Override
	public Chunk getChunk() {
		return regionBlock.getChunk();
	}

	@Override
	public Clan getClan() {
		return ClanSystem.getClanManager().getClan(clan);
	}

	@Override
	public void setClan(Clan clan) {
		this.clan = clan.getUUID();
	}

	@Override
	public void save(File file) {
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("region.clan", this.clan.toString());
		config.set("region.world", this.regionBlock.getWorld().getName());
		config.set("region.blockX", this.regionBlock.getBlockX());
		config.set("region.blockY", this.regionBlock.getBlockY());
		config.set("region.blockZ", this.regionBlock.getBlockZ());
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
