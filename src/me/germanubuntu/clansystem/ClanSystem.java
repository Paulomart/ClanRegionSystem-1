package me.germanubuntu.clansystem;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import me.germanubuntu.clansystem.clan.ClanManager;
import me.germanubuntu.clansystem.commands.BasicCommandListener;
import me.germanubuntu.clansystem.socket.BasicSocketHandler;
import me.germanubuntu.clansystem.socket.JsonSocketServer;

import org.bukkit.plugin.java.JavaPlugin;

public class ClanSystem extends JavaPlugin {

	@Getter
	private static ClanManager clanManager;
	@Getter
	private static Messages messages;
	@Getter
	private static BasicCommandListener commandListener;
	@Getter
	private static Config pluginConfig;
	@Getter
	private static JsonSocketServer jsonSocketServer;

	@Override
	public void onEnable() {

		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdirs();
		}

		pluginConfig = new Config(new File(this.getDataFolder()
				.getAbsolutePath() + "/config.yml"));
		pluginConfig.load();

		clanManager = ClanManager.getClanManager(this);
		clanManager.loadAllClans();

		messages = new Messages();
		messages.loadConfig(new File(this.getDataFolder().getAbsolutePath() + "/messages.yml"));

		commandListener = new BasicCommandListener();

		getCommand("clan").setExecutor(commandListener);
		getCommandListener().registerListener(new BasicArguments());
		getCommandListener().registerListener(new ClanArguments());

		this.getServer().getPluginManager().registerEvents(new EventListener(), this);

		if (getPluginConfig().isSocketServer()) {
			jsonSocketServer = new JsonSocketServer();
			try {
				jsonSocketServer.startServer(getPluginConfig().getHost(), getPluginConfig().getPort());
				this.getServer().getPluginManager().registerEvents(new BasicSocketHandler(), this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDisable() {
		if (jsonSocketServer != null) {
			try {
				jsonSocketServer.stopServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clanManager.saveAllClans();
	}

}
