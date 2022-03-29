package com.svi.data;
import java.io.File;
import java.io.FileWriter;   
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.svi.database.Config;
import com.svi.database.RecordDao;
import com.svi.database.RecordDaoImpl;
import com.svi.objects.ConfigObjects;
import com.svi.objects.Player;
import com.svi.objects.Record;

public class SaveGame {
	
	int countGameIds = 0;
	
	//Save Game to Game ID.txt and Table Records (Database)
	public static void saveGame(Game game) throws IOException {
		  //Save to txt file
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
	
	//Save Game ID to Player ID.txt and Table Players (Database)
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
	
	//Get details from Player ID.txt
		public static List<String> getPlayerGames(String playerId) throws Exception {
			//Get from txt files
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
	
	//Get details from Records Database
	public static List<Game> getGames(String gameId) throws Exception {
		List<Game> gameIds = new ArrayList<Game>();
		
		//Get from database
		RecordDao recordDao = new RecordDaoImpl();
		gameIds = recordDao.getRecords(gameId);
		
		return gameIds;
	}
}
