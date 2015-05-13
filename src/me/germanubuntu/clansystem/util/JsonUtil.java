package me.germanubuntu.clansystem.util;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtil {
	
	public static JSONObject getJSONObject(String s){
        JSONParser jsonParser = new JSONParser();
        
        JSONObject jsonObject = null;
        
        try {
			jsonObject = (JSONObject) jsonParser.parse(s);
		} catch (ParseException e) {
			Bukkit.getLogger().warning("Client send No JSON Message!");
		}
        return jsonObject;
	}
}
