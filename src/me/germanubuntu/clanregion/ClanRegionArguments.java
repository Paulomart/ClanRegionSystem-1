package me.germanubuntu.clanregion;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.germanubuntu.clanregion.chunk.ChunkHelper;
import me.germanubuntu.clanregion.region.ClanRegion;
import me.germanubuntu.clansystem.ClanSystem;
import me.germanubuntu.clansystem.commands.ArgumentListener;

public class ClanRegionArguments implements ArgumentListener{

	@Override
	public boolean onArgument(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(args.length == 1){
			if(ClanRegionSystem.getPluginConfig().isBeaconToChunkCommand()){
				if(args[0].equalsIgnoreCase("claim")){
					if(sender.hasPermission("ClanSystem.clan.claim")){
						if(sender instanceof Player){
							Player player = (Player) sender;
							if(player.getItemInHand().getType() == Material.BEACON){
								int amm = player.getItemInHand().getAmount();
								player.getInventory().setItemInHand(ClanRegionSystem.getItems().getClaimItem());
								player.getItemInHand().setAmount(amm);
								player.sendMessage(ClanRegionSystem.getMessages().getValues().get("SelectRegionMessage"));
							}else{
								player.sendMessage(ClanRegionSystem.getMessages().getValues().get("MustHaveBeaconInHandMessage"));
							}
						}
					}else{
						sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
					}
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("chunkinfo")){
				if(sender.hasPermission("ClanSystem.clan.chunkinfo")){
					if(sender instanceof Player){
						Player player = (Player) sender;
						Chunk chunk = player.getEyeLocation().getChunk();
						ClanRegion region = ClanRegionSystem.getClanRegionManager().getRegionForChunk(chunk);
						
						if(region == null){
							player.sendMessage(ChatColor.GREEN+"X: "+chunk.getX()+"| Z: "+chunk.getZ());
						}else{
							player.sendMessage(ChatColor.GREEN+"X: "+chunk.getX()+"| Z: "+chunk.getZ()+"| Clan: "+region.getClan().getName());
						}
						if(ClanRegionSystem.getPluginConfig().isShowChunk()){
							ChunkHelper.showChunk(chunk);
						}
					}
				}else{
					sender.sendMessage(ClanSystem.getMessages().getValues().get("NoPermissionsMessage"));
				}
				return true;
			}
		}
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("addAntiRegion")){
				if(sender.hasPermission("ClanSystem.clan.addantiregion")){
					if(sender instanceof Player){
						Player player = (Player) sender;
						ClanRegionSystem.getClanRegionManager().addAntiClaimRegions(player.getLocation(), Integer.valueOf(args[1]));
						player.sendMessage(ClanRegionSystem.getMessages().getValues().get("AntiRegionCreatedMessage"));
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
