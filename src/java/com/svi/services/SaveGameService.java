package com.svi.services;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.svi.data.Game;
import com.svi.data.SaveGame;

@Path("/save")
public class SaveGameService {
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response testPost(JSONObject in_json) throws Exception {
		JSONObject out_json = null;
		StringBuilder sb = new StringBuilder();   
        try {
        	in_json = new JSONObject (sb.toString());
            out_json = new JSONObject();
            
            // get data from in_json to create a GameObject
            Game game = new Game(
            	in_json.getString("gameid"),
            	in_json.getString("playerid"),
            	in_json.getString("symbol"),
            	in_json.getInt("location"),
            	in_json.getString("datesaved")
            );
            
            // Getting the file by creating object of File class
            String fileName = "C:\\Users\\Ayeeh\\Downloads\\tictac\\payara5\\domains\\domain22\\config\\record\\" + game.getPlayerId() + ".txt";
            File f = new File(fileName);
            if (f.exists()) {
            	
            } else {
            	 SaveGame.savePlayer(game);
            }
            // save game, player to file
            SaveGame.saveGame(game); 
            
        } catch(Exception e) {
        	 out_json.put("msg", "Record could not be saved (" + e + ")");
        	 return Response.status(401).entity(out_json.toString()).build();
        }  
        System.out.println(sb.toString());
        out_json.put("msg", "Record saved");
        return Response.ok().entity(out_json.toString()).build();
    }
}
