package me.germanubuntu.clanregion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	
	private ItemStack claimItem;
	
	public ItemStack getClaimItem(){
		return claimItem;
	}
	
	protected Items(){
		claimItem = new ItemStack(Material.BEACON);
		ItemMeta meta = claimItem.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN+"Claim Beacon");
		List<String> lore = new ArrayList<String>();
		lore.add("Rightclick a Block and claim it!");
		meta.setLore(lore);
		claimItem.setItemMeta(meta);
	}
	
}
