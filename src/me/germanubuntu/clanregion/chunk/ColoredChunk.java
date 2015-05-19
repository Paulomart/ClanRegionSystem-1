package me.germanubuntu.clanregion.chunk;

import org.bukkit.Chunk;

public class ColoredChunk implements Runnable{
	
	private Chunk chunk;
	private int cX, cZ;
	
	public ColoredChunk(Chunk chunk){
		this.chunk = chunk;
		cX = chunk.getX()*16;
		cZ = chunk.getZ()*16;
	}
	
	@Override
	public void run() {
		
		for(int x = 0; x <= 64; x++){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spawn();
		}
	}
	
	private void spawn(){
		for(int x = 0; x <= 16; x++){
			ChunkHelper.spawnEffect(chunk.getWorld(), cX+x, cZ);
		}
		for(int z = 0; z <= 16; z++){
			ChunkHelper.spawnEffect(chunk.getWorld(), cX, cZ+z);
		}
		for(int x = 0; x <= 16; x++){
			ChunkHelper.spawnEffect(chunk.getWorld(), cX+x, cZ+16);
		}
		for(int z = 0; z <= 16; z++){
			ChunkHelper.spawnEffect(chunk.getWorld(), cX+16, cZ+z);
		}
	}

}
