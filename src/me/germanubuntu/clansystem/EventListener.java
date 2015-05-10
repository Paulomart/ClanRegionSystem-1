package me.germanubuntu.clansystem;

import me.germanubuntu.clansystem.clan.Clan;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventListener implements Listener{
	
	@EventHandler
	public void onDamageEvent(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			if(event.getEntity() instanceof Player){
				Player damager = (Player) event.getDamager();
				Player damaged = (Player) event.getEntity();
				if(ClanSystem.getClanManager().isInSameClan(damager, damaged)){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void playerDeath(PlayerDeathEvent event){
		Clan clan = ClanSystem.getClanManager().getClanForPlayer(event.getEntity().getUniqueId());
		if(clan != null){
			Player killer = event.getEntity().getKiller();
			if(killer != null){
				if(killer instanceof Player){
					clan.setDeaths(clan.getDeaths()+1);
					
					Clan killerClan = ClanSystem.getClanManager().getClanForPlayer(killer.getUniqueId());
					if(killerClan != null) {
						killerClan.setKills(killerClan.getKills()+1);
					}
				}
			}
		}
	}
	
}
