package me.germanubuntu.clansystem.clan;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

public interface Clan {
	
	public void setOwner(UUID uuid);
	public void addUser(UUID uuid);
	public void removeUser(UUID uuid);
	public void setName(String name);
	public void setContraction(String contraction);
	
	public UUID getUUID();
	public void setUUID(UUID uuid);
	
	public void save(File file);
	
	public boolean isInvited(UUID uuid);
	public void addInvited(UUID uuid);
	public void removeInvited(UUID uuid);
	
	public UUID getOwner();
	public List<UUID> getUsers();
	public String getName();
	public String getContraction();
	
	public Location getHome();
	public void setHome(Location loc);
	public boolean hasHome();
	
	public int getKills();
	public int getDeaths();
	public void setKills(int x);
	public void setDeaths(int x);
	
	public Object getValue(String id);
	public void putValue(String id, Object obj);
	public boolean hasKey(String id);
}
