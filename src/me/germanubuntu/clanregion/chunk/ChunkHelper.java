package me.germanubuntu.clanregion.chunk;

import me.germanubuntu.clanregion.ClanRegionSystem;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class ChunkHelper {
	
	public static void showChunk(Chunk chunk){
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClanRegionSystem.getPlugin(), new ColoredChunk(chunk));
	}
	
	public static void spawnEffect(World world, int x, int z){
		world.playEffect(new Location(world, x, world.getHighestBlockAt(x, z).getY(), z), Effect.COLOURED_DUST, 1);
		world.playEffect(new Location(world, x, world.getHighestBlockAt(x, z).getY()+1, z), Effect.COLOURED_DUST, 1);
		world.playEffect(new Location(world, x, world.getHighestBlockAt(x, z).getY()+2, z), Effect.COLOURED_DUST, 1);
	}
}
