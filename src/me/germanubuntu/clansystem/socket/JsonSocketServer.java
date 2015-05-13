package me.germanubuntu.clansystem.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

import me.germanubuntu.clansystem.socket.event.JsonServerMessageEvent;
import me.germanubuntu.clansystem.util.JsonUtil;

public class JsonSocketServer extends Thread{
	
	private ServerSocket server;
	
	public JsonSocketServer(){
		try {
			server = new ServerSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bukkit.getLogger().info("ClanSystemCosaXNetwork starting...");
	}
	
	public void startServer(String host, int port) throws IOException{
		if(!host.equalsIgnoreCase("*")){
			InetSocketAddress addr = new InetSocketAddress(host, port);
			server.bind(addr);
		}else{
			InetSocketAddress addr = new InetSocketAddress(port);
			server.bind(addr);
		}
		this.start();
	}
	
	public void stopServer() throws IOException{
		server.close();
		this.stop();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket client = server.accept();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				
				String readed = reader.readLine();
				JSONObject jsonObject = JsonUtil.getJSONObject(readed);
				if(jsonObject != null){
					JsonServerMessageEvent event = new JsonServerMessageEvent(jsonObject, client, writer, reader);
				
					Bukkit.getPluginManager().callEvent(event);
				
					writer.write(event.getReturnMessage()+"\n");
					writer.flush();
				}
				
				writer.close();
				reader.close();
				client.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}
