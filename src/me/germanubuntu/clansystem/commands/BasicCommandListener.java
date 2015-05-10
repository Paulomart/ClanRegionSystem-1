package me.germanubuntu.clansystem.commands;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BasicCommandListener implements CommandExecutor{
	
	@Getter
	private List<ArgumentListener> listeners;
	
	
	public BasicCommandListener(){
		listeners = new ArrayList<ArgumentListener>();
	}
	
	public void registerListener(ArgumentListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(ArgumentListener listener){
		this.listeners.remove(listener);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		boolean used = false;
		if(cmd.getName().equalsIgnoreCase("clan")){
			for(ArgumentListener listener : listeners){
				used = listener.onArgument(sender, cmd, arg2, args);
				if(used){
					break;
				}
			}
			if(!used){
				sender.sendMessage(ChatColor.GOLD+"-------");
				sender.sendMessage(ChatColor.GREEN+"Version: "+Bukkit.getServer().getPluginManager().getPlugin("ClanSystem").getDescription().getVersion());
				sender.sendMessage(ChatColor.GREEN+"Author: GermanUbuntu");
				StringBuilder builder = new StringBuilder();
				for(String s : args){
					builder.append(" ");
					builder.append(s);
				}
				
				sender.sendMessage(ChatColor.GREEN+"Command '/"+cmd.getName()+builder.toString()+"' not found!");
			}
		}
		return true;
	}

}
