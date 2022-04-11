package com.svi.data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;   
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.svi.database.Config;
import com.svi.database.RecordDao;
import com.svi.database.RecordDaoImpl;
import com.svi.objects.ConfigObjects;
import com.svi.objects.Player;
import com.svi.objects.Record;

public class SaveGame {
	
	int countGameIds = 0;
	
	/**
	 * Save Game to Game ID.txt and Table Records (Database)
	 * @param game - Game Object includes Game ID, Player ID, Location, Symbol, and Record Date
	 * @throws IOException
	 */
	public static void saveGame(Game game) throws IOException {
		  //Save to text files
	      FileWriter myWriter = new FileWriter("record/" + game.getGameId()+".txt",true);
	      myWriter.write(game + "\n");
	      System.out.println("ok ok ok\n");
	      myWriter.close();
	      
	      //Save to database
	      Record record = new Record();
	      record.setGameid(game.getGameId());
		  record.setPlayerid(game.getPlayerId());
		  record.setLocation(Integer.toString(game.getLocation()));
		  record.setSymbol(game.getSymbol());
		  record.setRecorddate(game.getCreatedDate());		
		  
		  RecordDao recordDao = new RecordDaoImpl();
		  recordDao.insertRecords(record);
	}
	
	/**
	 * Save Game ID to Player ID.txt and Table Players (Database)
	 * @param player - Player Object includes Player ID and its Game ID
	 * @throws IOException
	 */
	public static void savePlayer(Player player) throws IOException {
		
		  //Save to text files
	      FileWriter myWriter = new FileWriter("record/" + player.getPlayerid()+".txt",true);
	      myWriter.write(Player.getGameid() + "\n");
	      System.out.println("ok ok ok\n");
	      myWriter.close();
	      
	      //Save to database
	      player = new Player(player.getPlayerid(), Player.getGameid());
		  RecordDao recordDao = new RecordDaoImpl();
		  
		  //Check if same Player ID with same Game ID exists in DB
		  int count = recordDao.checkPlayer(player.getPlayerid(),Player.getGameid());
		  if (count >= 1) {
			  System.out.println("Player ID is already saved in the DB");
		  } else {
			  recordDao.insertPlayers(player);
		  }
	}
	
	/**
	 * Get all Player's games and list them
	 * @param playerId - used to find all game IDs associated with this Player ID
	 * @return gameIds - list of Game IDs that the player used
	 * @throws Exception
	 */
	public static List<String> getPlayerGames(String playerId) throws Exception {
		//Get the player's Game IDs from .txt files
		File f = new File("record/" + playerId +".txt");
		
		if(!f.exists() && !f.isDirectory()) { 
			throw new Exception("File does not exist.");
		}
			
		List<String> gameIds = new ArrayList<String>();
			
		try {
			Scanner scanner = new Scanner(new File("record/" + playerId +".txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if(!line.isEmpty()) {
					gameIds.add(line);
				}	  
			}
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
			
		if(gameIds.size() == 0) {
			throw new Exception("Record not found.");
		}
		return gameIds;
		}
	
	/**Get all game moves of a particular Game ID from Table Records (Database)
	 * 
	 * @param gameId - used to get all game moves from database
	 * @return gameIds - list of game moves of the Game ID
	 * @throws Exception
	 */
	public static List<Game> getGames(String gameId) throws Exception {
		List<Game> gameIds = new ArrayList<Game>();
		
		RecordDao recordDao = new RecordDaoImpl();
		gameIds = recordDao.getRecords(gameId);
		
		return gameIds;
	}
	
	/**
	 * Log Game Object to Game.log
	 * @param game - includes all details that will be saved to Game.log
	 * @throws IOException
	 */
	public static void saveLog(Game game) throws IOException {
		boolean append = true;
        FileHandler handler = new FileHandler("record/logs/Game.log", append);
		Logger logger = Logger.getLogger("com.svi.services");
		logger.addHandler(handler);
		logger.info("New record inserted. " + game.getGameId() + ", " + game.getPlayerId() + ", " + game.getSymbol() + ", " + game.getLocation());
	}
	
	/**
	 * Reads all logs in Game.log
	 */
	public static void readLog() {
		try {
			FileInputStream fstream = new FileInputStream("record/logs/Game.log");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			/* read log line by line */
			while ((strLine = br.readLine()) != null)   {
				/* parse strLine to obtain what you want */
				System.out.println(strLine);
			}
			fstream.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
