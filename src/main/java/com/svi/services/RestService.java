package com.svi.services;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.svi.data.Game;
import com.svi.data.SaveGame;
import com.svi.objects.Player;

@Path("/test")
public class RestService {
	JSONObject out_json = null; 
	
	@GET
	@Path("/listgames/{playerid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(@PathParam("playerid") String playerid) throws Exception {
	        // get PlayerID from request
	        try {
	        	out_json = new JSONObject ();          
	            JSONArray list = new JSONArray();
	            
	            //Get Game IDs of a player and save it to a list
	            List<String> games = SaveGame.getPlayerGames(playerid);
	            
	            //Add each game ID to Json array
	            for(String id : games) {
	            	JSONObject idObject = new JSONObject();
	            	idObject.put("id", id);
	            	list.put(idObject);
	            }
	            out_json.put("list", list);

	        } catch(Exception e) {
	        	
	        	if(e.getMessage().equals("File does not exist.")) {
	        		 out_json.put("msg", "error");
		        	 return Response.status(500).entity(out_json.toString()).build();
	        	}
	        	
	        	 out_json.put("msg", "Record not found (" + e + ")");
	        	 return Response.status(402).entity(out_json.toString()).build();
	        }
		return Response.ok().entity(out_json.toString()).build();
	}
	
	@GET
	@Path("/getgame/{gameid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGame(@PathParam("gameid") String gameid) throws Exception {
	    try {
	       out_json = new JSONObject();
	       JSONArray list = new JSONArray();
	       
	       //Get all game moves of a game ID and save it to a list
	       List<Game> games = SaveGame.getGames(gameid);
	       //Read log file
	       SaveGame.readLog();
	      
	       //Add each game move to Json array
	       for(Game g : games) {
	    	   JSONObject gameObject = new JSONObject();
	    	   gameObject.put("gameid", g.getGameId());
	    	   gameObject.put("playerid", g.getPlayerId());
	    	   gameObject.put("symbol", g.getSymbol());
	    	   gameObject.put("location", g.getLocation());
	           gameObject.put("datesaved", g.getCreatedDate());
	           list.put(gameObject);
	       }
	       
	       out_json.put("list", list);
	            
	        } catch(Exception e) {
	        	 out_json.put("msg", "Record not found (" + e + ")");
	        	 return Response.status(402).entity(out_json.toString()).build();
	        }

	        out_json.put("msg", "Record found!");
	        return Response.ok().entity(out_json.toString()).build();
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testPost(JsonObject jsonData) throws Exception {
		
		String gameID = jsonData.getString("gameid");
		String symbol = jsonData.getString("symbol");
		int location =  jsonData.getInt("location");
		String playerID = jsonData.getString("playerid");
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateSaved = dateTime.format(formatter); 
		
		StringBuilder sb = new StringBuilder();
		
        JSONObject out_json = null;
          
        try {
            out_json = new JSONObject();
            
            // get data from jsonData to create a GameObject
            Game game = new Game(gameID, playerID, symbol, location, dateSaved);
            Player player = new Player(playerID,gameID);
            
            player.setPlayerid(playerID);
            Player.setGameid(gameID);
            
            //save game, player to db and log file
            SaveGame.savePlayer(player);
            SaveGame.saveGame(game); 
            SaveGame.saveLog(game);
            
        } catch(Exception e) {
        	 out_json.put("msg", "Record could not be saved (" + e + ")");
        	 return Response.status(401).entity(out_json.toString()).build();
        }  
        System.out.println(sb.toString());
        out_json.put("msg", "Record saved");
        
        return Response.ok().entity(out_json.toString()).build();
    }
}
