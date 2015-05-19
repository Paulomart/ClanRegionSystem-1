package me.germanubuntu.clanregion;

import java.util.UUID;

import me.germanubuntu.clanregion.region.ClanRegion;
import me.germanubuntu.clanregion.region.events.PlayerBreakBlockInRegionEvent;
import me.germanubuntu.clanregion.region.events.RegionClaimEvent;
import me.germanubuntu.clanregion.region.events.RegionDestroyEvent;
import me.germanubuntu.clansystem.ClanSystem;
import me.germanubuntu.clansystem.clan.Clan;
import me.germanubuntu.clansystem.clan.events.ClanDeleteEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener{
	
	@EventHandler
	public void playerBreakBlock(BlockBreakEvent event){
		if(!event.isCancelled()){
			if(!ClanRegionSystem.getClanRegionManager().canBreakBlock(event.getPlayer(), event.getBlock())){
				event.setCancelled(true);
			}else{
				PlayerBreakBlockInRegionEvent e = new PlayerBreakBlockInRegionEvent(event.getBlock(), event.getPlayer());
				Bukkit.getPluginManager().callEvent(e);
				if(e.isCancelled()){
					event.setCancelled(true);
				}
			}
			if(event.getBlock().getType() == Material.BEACON){
				ClanRegion region = ClanRegionSystem.getClanRegionManager().getRegionForChunk(event.getBlock().getChunk());
				if(region != null){
					if(region.getRegionBlock().equals(event.getBlock().getLocation())){
						RegionDestroyEvent e = new RegionDestroyEvent(region, event.getPlayer());
						Bukkit.getPluginManager().callEvent(e);
						if(!e.isCancelled()){
							event.getBlock().setType(Material.AIR);
							ClanRegionSystem.getClanRegionManager().removeRegion(region);
							event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("DestroyerDestroyedRegionMessage"));
							for(UUID uuid : region.getClan().getUsers()){
								Bukkit.getPlayer(uuid).sendMessage(ClanRegionSystem.getMessages().getValues().get("DestroyedRegionClanMessage"));
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void playerDamageEvent(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			Player p = (Player) event.getDamager();
			if(!this.canAttack(p, event.getEntity())){
				event.setCancelled(true);
			}
		}
		if(event.getDamager() instanceof Projectile){
			Projectile proj = (Projectile) event.getDamager();
			if(proj.getShooter() instanceof Player){
				Player p = (Player) proj.getShooter();
				if(!this.canAttack(p, event.getEntity())){
					event.setCancelled(true);
				}
			}
		}
	}
	
	private boolean canAttack(Player p, Entity e){
		boolean ret = true;
		if(!ClanRegionSystem.getClanRegionManager().canBreakBlock(p, e.getLocation().getBlock())){
			if(ClanRegionSystem.getPluginConfig().isProtectEntity()){
				if(!(e instanceof Player)){
					ret = false;
				}
			}
			if(ClanRegionSystem.getPluginConfig().isProtectPlayer()){
				if(e instanceof Player){
					ret = false;
				}
			}
		}
		return ret;
	}
	
	@EventHandler
	public void chestOpenEvent(InventoryOpenEvent event){
		if(ClanRegionSystem.getPluginConfig().isProtectChest()){
			if(event.getInventory().getHolder() instanceof Chest){
				Chest chest = (Chest) event.getInventory().getHolder();
				if(event.getPlayer() instanceof Player){
					Player p = (Player) event.getPlayer();
					if(!ClanRegionSystem.getClanRegionManager().canBreakBlock(p, chest.getBlock())){
						event.setCancelled(true);
					}
				}
			}else if(event.getInventory().getHolder() instanceof Furnace){
				Furnace furnace = (Furnace) event.getInventory().getHolder();
				if(event.getPlayer() instanceof Player){
					Player p = (Player) event.getPlayer();
					if(!ClanRegionSystem.getClanRegionManager().canBreakBlock(p, furnace.getBlock())){
						event.setCancelled(true);
					}
				}
			}else if(event.getInventory().getHolder() instanceof DoubleChest){
				DoubleChest chest = (DoubleChest) event.getInventory().getHolder();
				if(event.getPlayer() instanceof Player){
					Player p = (Player) event.getPlayer();
					if(!ClanRegionSystem.getClanRegionManager().canBreakBlock(p, chest.getLocation().getBlock())){
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void playerPlaceBlock(BlockPlaceEvent event){
		if(!event.isCancelled()){
			if(!ClanRegionSystem.getClanRegionManager().canBreakBlock(event.getPlayer(), event.getBlock())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void playerInteractBlockEvent(PlayerInteractEvent event){
		if(!event.isCancelled()){
			if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK){
				int amm = event.getPlayer().getItemInHand().getAmount();
				ItemStack item2 = ClanRegionSystem.getItems().getClaimItem();
				item2.setAmount(amm);
				if(event.getPlayer().getItemInHand().equals(item2)){
					if(!ClanRegionSystem.getClanRegionManager().isInAntiClaimRegion(event.getClickedBlock().getLocation())){
						if(!ClanRegionSystem.getClanRegionManager().existsRegion(event.getClickedBlock().getChunk())){
							Clan clan = ClanSystem.getClanManager().getClanForPlayer(event.getPlayer().getUniqueId());
							if(clan != null){
								Location loc = new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY()+1, event.getClickedBlock().getLocation().getZ());
								if(ClanRegionSystem.getClanRegionManager().getRegionCount(clan.getUUID()) <= ClanRegionSystem.getPluginConfig().getMaxRegions()){
									if(loc.getBlock().getType() == Material.AIR){
										
										RegionClaimEvent e = new RegionClaimEvent(clan, loc.getChunk(), loc, event.getPlayer());
										Bukkit.getPluginManager().callEvent(e);
										
										if(!e.isCancelled()) {
											ClanRegionSystem.getClanRegionManager().createRegion(loc, clan);
											loc.getBlock().setType(Material.BEACON);
											event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("RegionClaimedMessage"));
											event.setCancelled(true);
											if(event.getPlayer().getItemInHand().getAmount() == 1){
												ItemStack item = new ItemStack(Material.AIR);
												event.getPlayer().setItemInHand(item);
											}else{
												event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
											}	
										}
									}else{
										event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("CantClaimRegionMessage"));
										event.setCancelled(true);
									}
								}else{
									event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("CantClaimRegionMessage"));
									event.setCancelled(true);
								}
							}else{
								event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("CantClaimRegionMessage"));
								event.setCancelled(true);
							}
						}else{
							event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("CantClaimRegionMessage"));
							event.setCancelled(true);
						}
					}else{
						event.getPlayer().sendMessage(ClanRegionSystem.getMessages().getValues().get("CantClaimRegionMessage"));
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void clanDeleteEvent(ClanDeleteEvent event){
		if(!event.isCancelled()){
			for(ClanRegion region : ClanRegionSystem.getClanRegionManager().getRegions()){
				if(region.getClan().getUUID().equals(event.getClan().getUUID())){
					ClanRegionSystem.getClanRegionManager().removeRegion(region);
				}
			}
		}
	}
}
