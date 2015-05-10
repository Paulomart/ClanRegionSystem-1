package me.germanubuntu.clansystem.clan;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.germanubuntu.clansystem.ClanSystem;
import me.germanubuntu.clansystem.clan.events.ClanCreateEvent;
import me.germanubuntu.clansystem.clan.events.ClanDeleteEvent;
import me.germanubuntu.clansystem.clan.events.ClanInitLoadEvent;
import me.germanubuntu.clansystem.clan.events.ClanLoadEvent;
import me.germanubuntu.clansystem.clan.events.PlayerLeaveClanEvent;

public class ClanManager {
	
	private static ClanManager clanManager;
	@Getter
	private ClanSystem clanSystem;
	@Getter
	private List<Clan> clans;
	@Getter
	private File clanFilesDir;
	
	private ClanManager(ClanSystem system){
		this.clanSystem = system;
		this.clans = new ArrayList<Clan>();
		if(!system.getDataFolder().exists()){
			system.getDataFolder().mkdirs();
		}
		clanFilesDir = new File(system.getDataFolder().getAbsolutePath()+"/clans");
		if(!clanFilesDir.exists()){
			clanFilesDir.mkdirs();
		}
	}
	
	public double calculateKD(Clan clan){
		if(clan.getDeaths() != 0){
			double kills = clan.getKills();
			double deaths = clan.getDeaths();
			
			double kd = kills/deaths;
			return kd;
		}else{
			return clan.getKills();
		}
	}
	
	public Map<Integer, Clan> getBestClans(){
		Map<Integer, Clan> bestClans = new HashMap<Integer, Clan>();
		bestClans.put(1, getBestClan());
		bestClans.put(2, getSecondBestClan());
		bestClans.put(3, getThirdBestClan());
		
		return bestClans;
	}
	
	public Clan getBestClan(){
		Clan first = null;
		
		for(Clan clan : clans){
			if(first != null){
				if(this.calculateKD(clan) >= this.calculateKD(first)){
					first = clan;
				}
			}else{
				first = clan;
			}
		}
		return first;
	}
	
	public Clan getSecondBestClan(){
		Clan second = null;
		Clan best = getBestClan();
		
		for(Clan clan : clans){
			if(second != null){
				if(!clan.getUUID().equals(best.getUUID())){
					if(this.calculateKD(clan) >= this.calculateKD(second)){
						second = clan;
					}
				}
			}else{
				if(!clan.getUUID().equals(best.getUUID())){
					second = clan;
				}
			}
		}
		return second;
	}
	
	public Clan getThirdBestClan(){
		Clan third = null;
		Clan second = getSecondBestClan();
		Clan best = getBestClan();
		
		for(Clan clan : clans){
			if(third != null){
				if(!clan.getUUID().equals(best.getUUID()) && !clan.getUUID().equals(second.getUUID())){
					if(this.calculateKD(clan) >= this.calculateKD(third)){
						third = clan;
					}
				}
			}else{
				if(!clan.getUUID().equals(best.getUUID()) && !clan.getUUID().equals(second.getUUID())){
					third = clan;
				}
			}
		}
		return third;
	}
	
	public boolean createNormalClan(String name, String contraction, UUID owner){
		if(name.length() <= ClanSystem.getPluginConfig().getMaxNameLenght() && contraction.length() <= ClanSystem.getPluginConfig().getMaxContractionLenght()){
			if(this.clans.size() <= ClanSystem.getPluginConfig().getMaxClans()-1){
				List<UUID> users = new ArrayList<UUID>();
				users.add(owner);
				if(this.getClan(name) == null){
					UUID uuid = UUID.randomUUID();;
					while(this.clanExists(uuid)){
						uuid = UUID.randomUUID();
					}
					ClanCreateEvent event = new ClanCreateEvent(name, contraction, Bukkit.getPlayer(owner), uuid);
					Bukkit.getPluginManager().callEvent(event);
					if(!event.isCancelled()){
						this.clans.add(new NormalClan(name, contraction, owner, users, uuid, null, 0, 0, new HashMap<String, Object>()));
						return true;
					}
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	public boolean clanExists(UUID clan){
		if(this.getClan(clan) == null){
			return false;
		}else{
			return true;
		}
	}
	
	public Clan getClan(String name){
		for(Clan clan : clans){
			if(clan.getName().equalsIgnoreCase(name)){
				return clan;
			}
		}
		return null;
	}
	
	public Clan getClan(UUID uuid){
		for(Clan clan : clans){
			if(clan.getUUID().equals(uuid)){
				return clan;
			}
		}
		return null;
	}
	
	public void saveAllClans(){
		for(Clan clan : clans){
			clan.save(getClanFile(clan));
		}
	}
	
	public void loadAllClans(){
		File[] clanFiles = clanFilesDir.listFiles();
		for(File file : clanFiles){
			if(file.getName().endsWith(".clan")){
				Clan c = this.loadClan(file);
				if(c != null){
					clans.add(c);
				}
			}
		}
	}
	
	public Clan loadClan(File f){
		
		ClanInitLoadEvent event = new ClanInitLoadEvent();
		Bukkit.getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
			//Get all user
			List<String> uuidStrings = config.getStringList("clan.users");
			List<UUID> uuids = new ArrayList<UUID>();
		
			for(String uuidString : uuidStrings){
				uuids.add(UUID.fromString(uuidString));
			}
		
			//Get Owner
			UUID owner = UUID.fromString(config.getString("clan.owner"));
		
			//Get Clan UUID
			UUID clanUUID = UUID.fromString(config.getString("clan.uuid"));
		
			//Get Clan Home
			Location home = null;
			if(config.getBoolean("clan.hashome")){
				home = new Location(Bukkit.getWorld(config.getString("clan.home.world")), config.getInt("clan.home.x"), config.getInt("clan.home.y"), config.getInt("clan.home.z"));
			}
		
			HashMap<String, Object> values = new HashMap<String, Object>();
		
			for (String key : config.getConfigurationSection("ClanValues").getKeys(false)) {
				values.put(key, config.getString("ClanValues." + key));
			}
			
			String name = config.getString("clan.name");
			String contraction = config.getString("clan.contraction");
			int kills = config.getInt("clan.kills");
			int deaths = config.getInt("clan.deaths");
			ClanLoadEvent e = new ClanLoadEvent(name, contraction, owner, uuids, clanUUID, home, kills, deaths, values);
			Bukkit.getPluginManager().callEvent(e);
			if(!e.isCancelled()){
				return new NormalClan(name, contraction, owner, uuids, clanUUID, home, kills, deaths, values);
			}
		}
		return null;
	}
	
	public boolean teleportToClanHome(Player p){
		Clan clan = getClanForPlayer(p.getUniqueId());
		if(clan != null){
			if(clan.hasHome()){
				p.teleport(clan.getHome());
				return true;
			}
		}else{
			return false;
		}
		return false;
	}
	
	public boolean isPlayerInClan(UUID player){
		for(Clan clan : clans){
			for(UUID user : clan.getUsers()){
				if(user.equals(player)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean leaveClan(UUID player){
		Clan clan = getClanForPlayer(player);
		PlayerLeaveClanEvent event = new PlayerLeaveClanEvent(clan, Bukkit.getPlayer(player));
		
		Bukkit.getServer().getPluginManager().callEvent(event);
		
		if(!event.isCancelled()){
			if(clan != null){
				if(clan.getOwner().equals(player)){
					Bukkit.getPlayer(player).sendMessage(ClanSystem.getMessages().getValues().get("WriteDeleteMessage"));
					return false;
				}else{
					clan.removeUser(player);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean deleteClan(UUID owner){
		Clan clan = getClanForPlayer(owner);
		if(clan != null){
			if(clan.getOwner().equals(owner)){
				ClanDeleteEvent event = new ClanDeleteEvent(clan);
				Bukkit.getServer().getPluginManager().callEvent(event);
				if(!event.isCancelled()){
					this.clans.remove(clan);
					getClanFile(clan).delete();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean deleteClanWithoutPermissions(Clan clan){
		if(clan != null){
			return this.deleteClan(clan.getOwner());
		}
		return false;
	}
	
	public File getClanFile(Clan clan){
		return new File(clanFilesDir.getAbsolutePath()+"/"+clan.getUUID().toString()+".clan");
	}
	
	public Clan getClanForPlayer(UUID player){
		for(Clan clan : clans){
			for(UUID user : clan.getUsers()){
				if(user.equals(player)){
					return clan;
				}
			}
		}
		return null;
	}
	
	public boolean isInSameClan(Player p1, Player p2){
		Clan clan1 = this.getClanForPlayer(p1.getUniqueId());
		Clan clan2 = this.getClanForPlayer(p2.getUniqueId());
		if(clan1 != null && clan2 != null){
			if(clan1.getUUID().equals(clan2.getUUID())){
				return true;
			}
		}
		return false;
	}
	
	public static ClanManager getClanManager(ClanSystem system){
		if(clanManager == null){
			clanManager = new ClanManager(system);
		}
		return clanManager;
	}
}
