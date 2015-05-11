package me.germanubuntu.clansystem;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class Config {
	
	@Getter
	private File file;
	@Getter
	private int maxClans, maxNameLenght, maxContractionLenght, port;
	@Getter
	private boolean socketServer;
	@Getter
	private String host, salt;
	
	protected Config(File file){
		this.file = file;
	}
	
	public void load(){
		YamlConfiguration config = null;
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			config = YamlConfiguration.loadConfiguration(file);
			config.set("Clan.MaxClans", 999999999);
			config.set("Clan.MaxNameLenght", 8);
			config.set("Clan.MaxContractionLenght", 4);
			config.set("SocketServer", false);
			config.set("SocketServer.Port", 56571);
			config.set("SocketServer.Host", "*");
			Random r = new SecureRandom();
			byte[] salt = new byte[60];
			r.nextBytes(salt);
			config.set("SocketServer.Salt", DatatypeConverter.printBase64Binary(salt));
			
			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
		port = config.getInt("SocketServer.Port");
		host = config.getString("SocketServer.Host");
		socketServer = config.getBoolean("SocketServer");
		salt = config.getString("SocketServer.Salt");
		maxClans = config.getInt("Clan.MaxClans");
		maxNameLenght = config.getInt("Clan.MaxNameLenght");
		maxContractionLenght = config.getInt("Clan.MaxContractionLenght");
	}
	
}
