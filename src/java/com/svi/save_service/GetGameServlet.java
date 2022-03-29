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

import com.svi.data.Game;
import com.svi.data.SaveGame;

public class GetGameServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
	    	
	    res.setContentType("application/json");
	    res.setHeader("Access-Control-Allow-Origin", "*");
	    res.setHeader("Cache-Control", "no-cache");
	         
	    PrintWriter out = res.getWriter();
	    JSONObject out_json = null;
	        
	    String reqGameId = req.getParameter("gameid");
	    try {
	       out_json = new JSONObject ();
	            
	       JSONArray list = new JSONArray();
	            
	       List<Game> games = SaveGame.getGames(reqGameId);
	            
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
	        	 res.setStatus(402);
	        	 out.print(out_json.toString());
	             return;
	        }

	        out_json.put("msg", "Record found!");
	        out.print(out_json.toString());
	    }
	
}
