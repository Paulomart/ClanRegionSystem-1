package me.germanubuntu.clansystem.socket.event;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.germanubuntu.clansystem.ClanSystem;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.json.simple.JSONObject;

public class JsonServerMessageEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
	
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket client;
    private JSONObject jsonObject;
    private String returnMessage;
    
    public JsonServerMessageEvent(JSONObject jsonObject, Socket client, PrintWriter writer, BufferedReader reader){
    	this.jsonObject = jsonObject;
    	this.reader = reader;
    	this.writer = writer;
    	this.client = client;
    }
    
    public Socket getClient(){
    	return client;
    }
    
    public JSONObject getJSONObject(){
    	return jsonObject;
    }
    
    public BufferedReader getReader(){
    	return reader;
    }
    
    public PrintWriter getWriter(){
    	return writer;
    }
    
    public void setReturnMessage(String returnMessage){
    	this.returnMessage = returnMessage;
    }
    
    public String getReturnMessage(){
    	return returnMessage;
    }
    
    public boolean saltIsRight(){
    	if(jsonObject.containsKey("salt")){
    		if(ClanSystem.getPluginConfig().getSalt().equalsIgnoreCase((String) jsonObject.get("salt"))){
    			return true;
    		}
    	}
    	return false;
    }
    
	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
