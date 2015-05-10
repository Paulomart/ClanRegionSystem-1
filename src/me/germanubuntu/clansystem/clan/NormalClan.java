package me.germanubuntu.clansystem.clan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class NormalClan implements Clan{
	
	private String name, contraction;
	private UUID owner, uuid;
	private List<UUID> users;
	private List<UUID> invitedPlayers;
	private Location home;
	private int deaths, kills;
	private HashMap<String, Object> values;
	
	protected NormalClan(String name, String contraction, UUID owner, List<UUID> users, UUID clanUUID, Location home, int kills, int deaths, HashMap<String, Object> values){
		invitedPlayers = new ArrayList<UUID>();
		this.name = name;
		this.contraction = contraction;
		this.owner = owner;
		this.users = users;
		this.uuid = clanUUID;
		this.home = home;
		this.kills = kills;
		this.deaths = deaths;
		this.values = values;
		values.put("ExampleValue", false);
	}
	
	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public List<UUID> getUsers() {
		return users;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getContraction() {
		return contraction;
	}

	@Override
	public void setOwner(UUID uuid) {
		this.owner = uuid;
	}

	@Override
	public void addUser(UUID uuid) {
		this.users.add(uuid);
	}

	@Override
	public void removeUser(UUID uuid) {
		this.users.remove(uuid);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setContraction(String contraction) {
		this.contraction = contraction;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
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
		config.set("clan.name", name);
		config.set("clan.contraction", contraction);
		config.set("clan.owner", owner.toString());
		config.set("clan.uuid", uuid.toString());
		config.set("clan.deaths", deaths);
		config.set("clan.kills", kills);
		config.set("ClanValues", values);
		
		if(hasHome()){
			config.set("clan.home.x", home.getBlockX());
			config.set("clan.home.y", home.getBlockY());
			config.set("clan.home.z", home.getBlockZ());
			config.set("clan.home.world", home.getWorld().getName());
			config.set("clan.hashome", true);
		}else{
			config.set("clan.hashome", false);
		}
		
		List<String> usersString = new ArrayList<String>();
		for(UUID uuid : users){
			usersString.add(uuid.toString());
		}
		
		config.set("clan.users", usersString);
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isInvited(UUID uuid) {
		for(UUID invited : invitedPlayers){
			if(invited.equals(uuid)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void addInvited(UUID uuid) {
		this.invitedPlayers.add(uuid);
	}

	@Override
	public void removeInvited(UUID uuid) {
		this.invitedPlayers.remove(uuid);
	}

	@Override
	public Location getHome() {
		return home;
	}

	@Override
	public void setHome(Location loc) {
		this.home = loc;
	}

	@Override
	public boolean hasHome() {
		if(home != null){
			return true;
		}
		return false;
	}

	@Override
	public int getKills() {
		return kills;
	}

	@Override
	public int getDeaths() {
		return deaths;
	}

	@Override
	public void setKills(int x) {
		kills = x;
	}

	@Override
	public void setDeaths(int x) {
		deaths = x;
	}

	@Override
	public Object getValue(String id) {
		return values.get(id);
	}

	@Override
	public void putValue(String id, Object obj) {
		values.put(id, obj);
	}

	@Override
	public boolean hasKey(String id) {
		return values.containsKey(id);
	}
}
