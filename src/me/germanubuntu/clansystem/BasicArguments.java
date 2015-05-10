package me.germanubuntu.clansystem;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.germanubuntu.clansystem.clan.Clan;
import me.germanubuntu.clansystem.commands.ArgumentListener;

public class BasicArguments implements ArgumentListener{

	@Override
	public boolean onArgument(CommandSender sender, Command cmd, String arg2,String[] args) {
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("delete")){
				if(sender.hasPermission("ClanSystem.clan.delete")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						if(ClanSystem.getClanManager().deleteClan(p.getUniqueId())){
							sender.sendMessage(ClanSystem.getMessages().getValues().get("ClanDeleteSuccessfully"));
						}else{
							sender.sendMessage(ClanSystem.getMessages().getValues().get("CantDeleteYourClanMessage"));
						}
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("CommandOnlyForUserMessage"));
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("list")){
				if(sender.hasPermission("ClanSystem.clan.list")){
					sender.sendMessage(ChatColor.GREEN+"Clans: ");
					for(Clan clan : ClanSystem.getClanManager().getClans()){
						StringBuilder builder = new StringBuilder();
						builder.append(ClanSystem.getMessages().getGreenPrefix());
						builder.append("Name: ");
						builder.append(clan.getName());
						builder.append("| Contraction: ");
						builder.append(clan.getContraction());
						builder.append("| KD: ");
						builder.append(ClanSystem.getClanManager().calculateKD(clan));
						
						sender.sendMessage(builder.toString());
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("best") || args[0].equalsIgnoreCase("top")){
				if(sender.hasPermission("ClanSystem.clan.best")){
					
					Map<Integer, Clan> bestClans = ClanSystem.getClanManager().getBestClans();
					sender.sendMessage(ChatColor.GREEN+"Best Clans: ");
					if(bestClans.get(1) != null){
						StringBuilder builder = new StringBuilder();
						builder.append(ClanSystem.getMessages().getGreenPrefix());
						builder.append("(1.)Name: ");
						builder.append(bestClans.get(1).getName());
						builder.append("| Contraction: ");
						builder.append(bestClans.get(1).getContraction());
						builder.append("| KD: ");
						builder.append(ClanSystem.getClanManager().calculateKD(bestClans.get(1)));
						
						sender.sendMessage(builder.toString());
					}
					if(bestClans.get(2) != null){
						StringBuilder builder = new StringBuilder();
						builder.append(ClanSystem.getMessages().getGreenPrefix());
						builder.append("(2.)Name: ");
						builder.append(bestClans.get(2).getName());
						builder.append("| Contraction: ");
						builder.append(bestClans.get(2).getContraction());
						builder.append("| KD: ");
						builder.append(ClanSystem.getClanManager().calculateKD(bestClans.get(2)));
						
						sender.sendMessage(builder.toString());
					}
					if(bestClans.get(3) != null){
						StringBuilder builder = new StringBuilder();
						builder.append(ClanSystem.getMessages().getGreenPrefix());
						builder.append("(3.)Name: ");
						builder.append(bestClans.get(3).getName());
						builder.append("| Contraction: ");
						builder.append(bestClans.get(3).getContraction());
						builder.append("| KD: ");
						builder.append(ClanSystem.getClanManager().calculateKD(bestClans.get(3)));
						
						sender.sendMessage(builder.toString());
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
		}
		if(args.length == 2){
				if(args[0].equalsIgnoreCase("deleteother")){
					if(sender.hasPermission("ClanSystem.clan.deleteother")){
						Clan clan = ClanSystem.getClanManager().getClan(args[1]);
						if(clan != null){
							if(ClanSystem.getClanManager().deleteClanWithoutPermissions(clan)){
								sender.sendMessage(ClanSystem.getMessages().getValues().get("ClanDeleteSuccessfully"));
							}else{
								sender.sendMessage(ClanSystem.getMessages().getValues().get("CantDeleteYourClanMessage"));
							}
						}
						return true;
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
					}
				}
			
				if(args[0].equalsIgnoreCase("info")){
					if(sender.hasPermission("ClanSystem.clan.info")){
					Clan clan = ClanSystem.getClanManager().getClan(args[1]);
					if(clan != null){
						sender.sendMessage(ChatColor.GOLD+"----------");
						sender.sendMessage(ChatColor.GREEN+"Name: "+clan.getName());
						sender.sendMessage(ChatColor.GREEN+"Contraction: "+clan.getContraction());
						sender.sendMessage(ChatColor.GREEN+"K/D: "+clan.getKills()+"/"+clan.getDeaths()+"="+ClanSystem.getClanManager().calculateKD(clan));
						sender.sendMessage(ChatColor.GREEN+"Owner: "+Bukkit.getOfflinePlayer(clan.getOwner()).getName());
						sender.sendMessage(ChatColor.GREEN+"User: ");
												
						for(UUID uuid : clan.getUsers()){
							sender.sendMessage(ChatColor.GREEN+Bukkit.getOfflinePlayer(uuid).getName());
						}
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
		}
		if(args.length == 3){
			if(args[0].equalsIgnoreCase("create")){
				if(sender.hasPermission("ClanSystem.clan.create")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						if(!ClanSystem.getClanManager().isPlayerInClan(p.getUniqueId())){
							if(ClanSystem.getClanManager().createNormalClan(args[1], args[2], p.getUniqueId())){
								p.sendMessage(ClanSystem.getMessages().getValues().get("ClanCreatedSuccessfullyMessage"));
							}else{
								sender.sendMessage(ClanSystem.getMessages().getValues().get("ErrorByCreatingClanMessage"));
							}
						}else{
							sender.sendMessage(ClanSystem.getMessages().getValues().get("IsAlreadyInClanMessage"));
						}
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("CommandOnlyForUserMessage"));
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
