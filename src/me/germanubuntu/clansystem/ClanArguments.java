package me.germanubuntu.clansystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.germanubuntu.clansystem.clan.Clan;
import me.germanubuntu.clansystem.clan.events.PlayerJoinClanEvent;
import me.germanubuntu.clansystem.commands.ArgumentListener;

public class ClanArguments implements ArgumentListener{

	@Override
	public boolean onArgument(CommandSender sender, Command cmd, String arg2,String[] args) {
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("leave")){
				if(sender.hasPermission("ClanSystem.clan.leave")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						if(ClanSystem.getClanManager().leaveClan(p.getUniqueId())){
							sender.sendMessage(ClanSystem.getMessages().getValues().get("ClanLeaveMessage"));
						}else{
							sender.sendMessage(ClanSystem.getMessages().getValues().get("ErrorByLeavingClanMessage"));
						}	
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("CommandOnlyForUserMessage"));
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("home")){
				if(sender.hasPermission("ClanSystem.clan.home")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						if(ClanSystem.getClanManager().teleportToClanHome(p)){
							p.sendMessage(ClanSystem.getMessages().getValues().get("TeleportToClanHomeMessage"));
						}else{
							p.sendMessage(ClanSystem.getMessages().getValues().get("CantTeleportToClanHomeMessage"));
						}
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("CommandOnlyForUserMessage"));
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("sethome")){
				if(sender.hasPermission("ClanSystem.clan.sethome")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						Clan clan = ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId());
						if(clan != null){
							if(clan.getOwner().equals(p.getUniqueId())){
								clan.setHome(p.getLocation());
								p.sendMessage(ClanSystem.getMessages().getValues().get("ClanHomeSetMessage"));
							}else{
								p.sendMessage(ClanSystem.getMessages().getValues().get("CantSetClanHomeMessage"));
							}
						}else{
							p.sendMessage(ClanSystem.getMessages().getValues().get("CantSetHomeMessage"));
						}
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}	
				return true;
			}
		}
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("kick")){
				if(sender.hasPermission("ClanSystem.clan.kick")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						Player kickPlayer = Bukkit.getPlayer(args[1]);
						
						Clan clan = ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId());
						
						
						if(kickPlayer != null){
							if(ClanSystem.getClanManager().getClanForPlayer(kickPlayer.getUniqueId()).getUUID().equals(clan.getUUID())){
								if(clan.getOwner().equals(p.getUniqueId())){
									if(ClanSystem.getClanManager().leaveClan(kickPlayer.getUniqueId())){
										p.sendMessage(ClanSystem.getMessages().getValues().get("PlayerKickedMessage"));
										kickPlayer.sendMessage(ClanSystem.getMessages().getValues().get("ClanLeaveMessage"));
									}else{
										p.sendMessage(ClanSystem.getMessages().getValues().get("CantKickMessage"));
									}
								}else{
									p.sendMessage(ClanSystem.getMessages().getValues().get("CantKickMessage"));
								}
							}else{
								p.sendMessage(ClanSystem.getMessages().getValues().get("CantKickMessage"));
							}
						}else{
							p.sendMessage(ClanSystem.getMessages().getValues().get("CantKickMessage"));
						}
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			
			if(args[0].equalsIgnoreCase("invite")){
				if(sender.hasPermission("ClanSystem.clan.invite")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						Player invited = Bukkit.getPlayer(args[1]);
					
						if(invited != null){
							Clan clan = ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId());
							if(clan != null){
								if(clan.getOwner().equals(p.getUniqueId())){
									clan.addInvited(invited.getUniqueId());
									p.sendMessage(ClanSystem.getMessages().getValues().get("InvitedSuccessfullyMessage"));
									invited.sendMessage(ClanSystem.getMessages().getValues().get("InvitedMessage").replace("(%clan%)", clan.getName()));
								}else{
									p.sendMessage(ClanSystem.getMessages().getValues().get("CantInvitePlayerMessage"));
								}
							}
						}else{
							p.sendMessage(ClanSystem.getMessages().getValues().get("CantInvitePlayerMessage"));
						}
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("CommandOnlyForUserMessage"));
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("uninvite")){
				if(sender.hasPermission("ClanSystem.clan.uninvite")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						Player uninvited = Bukkit.getPlayer(args[1]);
					
						if(uninvited != null){
							Clan clan = ClanSystem.getClanManager().getClanForPlayer(p.getUniqueId());
							if(clan != null){
								if(clan.getOwner().equals(p.getUniqueId())){
									clan.removeInvited(uninvited.getUniqueId());
									p.sendMessage(ClanSystem.getMessages().getValues().get("PlayerAreNowUnInviteMessage"));
								}else{
									p.sendMessage(ClanSystem.getMessages().getValues().get("CantUnInvitedMessage"));
								}
							}
						}else{
							p.sendMessage(ClanSystem.getMessages().getValues().get("CantUnInvitedMessage"));
						}	
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("CommandOnlyForUserMessage"));
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("join")){
				if(sender.hasPermission("ClanSystem.clan.join")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						Clan clan = ClanSystem.getClanManager().getClan(args[1]);
						if(clan.isInvited(p.getUniqueId())){
							if(!ClanSystem.getClanManager().isPlayerInClan(p.getUniqueId())){
								PlayerJoinClanEvent event = new PlayerJoinClanEvent(clan, p);
								
								Bukkit.getServer().getPluginManager().callEvent(event);
								if(!event.isCancelled()){
									clan.removeInvited(p.getUniqueId());
									clan.addUser(p.getUniqueId());
									p.sendMessage(ClanSystem.getMessages().getValues().get("JoinedInClanMessage").replace("(%clan%)", clan.getName()));
								}
							}else{
								p.sendMessage(ClanSystem.getMessages().getValues().get("IsAlreadyInClanMessage"));
							}
						}else{
							p.sendMessage(ClanSystem.getMessages().getValues().get("NoInviteMessage"));
						}
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
		}
		return false;
	}

}
