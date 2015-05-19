package me.germanubuntu.clanregion.region;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import me.germanubuntu.clanregion.ClanRegionSystem;
import me.germanubuntu.clansystem.ClanSystem;
import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ClanRegionManager {
	
	private static ClanRegionManager clanRegionManager;
	@Getter
	private List<ClanRegion> regions;
	@Getter
	private List<NoClaimRegion> antiClaimRegions;
	@Getter
	private ClanRegionSystem clanRegionSystem;
	@Getter
	private File clanRegionDir, antiClaimRegionDir;
	
	private ClanRegionManager(ClanRegionSystem clanRegionSystem){
		regions = new ArrayList<ClanRegion>();
		antiClaimRegions = new ArrayList<NoClaimRegion>();
		this.clanRegionSystem = clanRegionSystem;
		clanRegionDir = new File(clanRegionSystem.getDataFolder().getAbsolutePath()+"/regions");
		antiClaimRegionDir = new File(clanRegionSystem.getDataFolder().getAbsolutePath()+"/antiregions");
		if(!clanRegionDir.exists()){
			clanRegionDir.mkdirs();
		}
		if(!antiClaimRegionDir.exists()){
			antiClaimRegionDir.mkdirs();
		}
	}
	
	public int getRegionCount(UUID clan){
		int x = 0;
		for(ClanRegion region : regions){
			if(region.getClan().getUUID().equals(clan)){
				x = x+1;
			}
		}
		return x;
	}
	
	public void addAntiClaimRegions(Location loc, int distance){
		antiClaimRegions.add(new NoClaimRegion(distance, loc));
	}
	
	public void saveAllRegions(){
		for(ClanRegion region :  regions){
			region.save(getRegionFile(region));
		}
		for(NoClaimRegion region : antiClaimRegions){
			region.save(new File(antiClaimRegionDir.getAbsolutePath()+"/"+region.getLocation().getBlockX()+"-"+region.getLocation().getBlockY()+"-"+region.getLocation().getBlockZ()+".antiregion"));
		}
	}
	
	public void loadAllRegions(){
		File[] files = clanRegionDir.listFiles();
		for(File file : files){
			if(file.getName().endsWith(".clanregion")){
				this.loadRegion(file);
			}
		}
		files = antiClaimRegionDir.listFiles();
		for(File file : files){
			if(file.getName().endsWith(".antiregion")){
				this.loadAntiRegion(file);
			}
		}
		antiClaimRegions.add(new NoClaimRegion(0, new Location(Bukkit.getWorlds().get(0), 0, 0, 0)));
	}
	
	public void loadAntiRegion(File file){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		Location loc = new Location(Bukkit.getWorld(config.getString("location.world")), config.getInt("location.x"), config.getInt("location.y"), config.getInt("location.z"));
		antiClaimRegions.add(new NoClaimRegion(config.getInt("distance"), loc));
	}
	
	public boolean existsRegion(Chunk chunk){
		for(ClanRegion region : regions){
			if(region.getChunk().equals(chunk)){
				return true;
			}else if(region.getChunk().getX() == chunk.getX() && region.getChunk().getZ() == chunk.getZ()){
				return true;
			}
		}
		return false;
	}
	
	public void createRegion(Location regionBlock, Clan clan){
		regions.add(new NormalClanRegion(clan.getUUID(), regionBlock));
	}
	
	public void loadRegion(File f){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		UUID clan = UUID.fromString(config.getString("region.clan"));
		Location regionBlock = new Location(Bukkit.getWorld(config.getString("region.world")), config.getInt("region.blockX"), config.getInt("region.blockY"), config.getInt("region.blockZ"));
		regions.add(new NormalClanRegion(clan, regionBlock));
	}
	
	public boolean canBreakBlockAtChunk(Player p, Chunk chunk){
		if(ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId()) != null){
			return hasPermissionsAtChunk(ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId()), chunk);
		}
		return true;
	}
	
	public void removeRegion(ClanRegion region){
		this.regions.remove(region);
		if(this.getRegionFile(region).exists()){
			this.getRegionFile(region).delete();
		}
	}
	
	public boolean canBreakBlock(Player p, Block b){
		Clan clan = ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId());
		ClanRegion region = getRegionForChunk(b.getChunk());
		
		if(clan != null){
			if(b.getType().equals(Material.BEACON)){
				if(region != null){
					if(region.getRegionBlock().equals(b.getLocation())){
						return true;
					}
				}
			}
			if(region != null){
				Chunk c1 = b.getWorld().getChunkAt(region.getChunk().getX()+1, region.getChunk().getZ());
				Chunk c2 = b.getWorld().getChunkAt(region.getChunk().getX()-1, region.getChunk().getZ());
				Chunk c3 = b.getWorld().getChunkAt(region.getChunk().getX(), region.getChunk().getZ()+1);
				Chunk c4 = b.getWorld().getChunkAt(region.getChunk().getX(), region.getChunk().getZ()-1);
				if(isChunkOwner(clan, c1) || isChunkOwner(clan, c2) || isChunkOwner(clan, c3) || isChunkOwner(clan, c4)){
					return true;
				}
			}
			return canBreakBlockAtChunk(p, b.getChunk());
		}else{
			if(region != null){
				return false;
			}
		}
		return true;
	}
	
	public ClanRegion getRegionForChunk(Chunk chunk){
		for(ClanRegion region : regions){
			if(region.getChunk().equals(chunk)){
				if(region.getChunk().equals(chunk)){
					return region;
				}
			}
		}
		return null;
	}
	
	public boolean isChunkOwner(Clan clan, Chunk chunk){
		ClanRegion region = getRegionForChunk(chunk);
		if(region != null){
			if(region.getClan().getUUID().equals(clan.getUUID())){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPermissionsAtChunk(Clan clan, Chunk chunk){
		ClanRegion region = getRegionForChunk(chunk);
		
		if(region == null){
			return true;
		}else{
			if(region.getClan().getUUID().equals(clan.getUUID())){
				return true;
			}
		}
		return false;
	}
	
	public File getRegionFile(ClanRegion region){
		return new File(clanRegionDir.getAbsolutePath()+"/"+region.getChunk().getX()+"-"+region.getChunk().getZ()+".clanregion");
	}
	
	public static ClanRegionManager getClanRegionManager(ClanRegionSystem clanRegionSystem){
		if(clanRegionManager == null){
			clanRegionManager = new ClanRegionManager(clanRegionSystem);
		}
		return clanRegionManager;
	}
	
	public boolean isInAntiClaimRegion(Location loc){
		for(NoClaimRegion region : antiClaimRegions){
			if(region.getLocation().getWorld().equals(loc.getWorld())){
				if(region.getLocation().distance(loc) <= region.getDistance()){
					return true;
				}
			}
		}
		return false;
	}
	
}
