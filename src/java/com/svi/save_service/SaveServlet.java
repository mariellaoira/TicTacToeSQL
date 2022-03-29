package com.svi.save_service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.svi.data.Game;
import com.svi.data.SaveGame;

public class SaveServlet extends HttpServlet {
	
    public SaveServlet() {
    	
    }
   
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	//res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Cache-Control", "no-cache");
    	
        // read json from request
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        
        PrintWriter out = res.getWriter();
        // create json object
        JSONObject in_json = null;
        JSONObject out_json = null;
          
        try {
        	in_json = new JSONObject(sb.toString());
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
            	FileReader fr = new FileReader(f);  //Creation of File Reader object
                BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                String s = br.readLine();
                if (s != game.getGameId()) {   //Search for the given word 
                	SaveGame.savePlayer(game);
                } 
            } else {
            	SaveGame.savePlayer(game);
            }
            // save game, player to file
            SaveGame.saveGame(game); 
            
        } catch(Exception e) {
        	 out_json.put("msg", "Record could not be saved (" + e + ")");
        	 res.setStatus(401);
        	 out.print(out_json.toString());
             return;
        }
        
        System.out.println(sb.toString());

        out_json.put("msg", "Record saved");
        
        out.print(out_json.toString());
    }
    
    public static boolean find(File f, String searchString) {
        boolean result = false;
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(f));
            while(in.hasNextLine() && !result) {
                result = in.nextLine().indexOf(searchString) >= 0;
            }
        }
        catch(IOException e) {
            e.printStackTrace();      
        }
        finally {
            try { in.close() ; } catch(Exception e) { /* ignore */ }  
        }
        return result;
    }
}