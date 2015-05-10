package me.germanubuntu.clansystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ArgumentListener {
	public boolean onArgument(CommandSender sender, Command cmd, String arg2, String[] args);
}
