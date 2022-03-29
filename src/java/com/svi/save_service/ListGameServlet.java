package com.svi.save_service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.svi.data.SaveGame;

public class ListGameServlet  extends HttpServlet {

	// /listgames/{playerId}
	  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		  res.setContentType("application/json");
	      res.setHeader("Access-Control-Allow-Origin", "*");
	      res.setHeader("Cache-Control", "no-cache");
	      
	      PrintWriter out = res.getWriter();
	        JSONObject out_json = null;
	        
	        // get PlayerID from request
	        String reqPlayerId = req.getParameter("playerid");
	        try {
	        	out_json = new JSONObject ();
	            
	            JSONArray list = new JSONArray();
	            
	            // load game from file
	            List<String> games = SaveGame.getPlayerGames(reqPlayerId);
	            
	            // Add game id to Json array
	            for(String id : games) {
	            	JSONObject idObject = new JSONObject();
	            	idObject.put("id", id);
	            	list.put(idObject);
	            }
	            out_json.put("list", list);
	            
	        } catch(Exception e) {
	        	
	        	if(e.getMessage().equals("File does not exist.")) {
	        		 out_json.put("msg", "error");
	        		 res.setStatus(500);
		        	 out.print(out_json.toString());
		        	 return;
	        	}
	        	
	        	 out_json.put("msg", "Record not found (" + e + ")");
	        	 res.setStatus(402);
	        	 out.print(out_json.toString());
	             return;
	        }
	        out.print(out_json.toString());
	    }
	
}
