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
	
	public SaveGame() {
		openDBConnection();
	}
	
	int countGameIds = 0;
	private static Connection conn;
	
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
	public static void savePlayer(Game game) throws IOException {
		  //Save to txt files
	      FileWriter myWriter = new FileWriter("record/" + game.getPlayerId()+".txt",true);
	      myWriter.write(game.getGameId() + "\n");
	      System.out.println("ok ok ok\n");
	      myWriter.close();    
	      
	      //Save to database
	      Player player = new Player();
	      player.setPlayerid(game.getPlayerId());
	      Player.setGameid(game.getGameId());  
		  RecordDao recordDao = new RecordDaoImpl();
		  recordDao.insertPlayers(player);
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
		
		//Get from database
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT gameid FROM test.players WHERE playerid = ?");
			ps.setString(1, playerId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String gameid = rs.getString("gameid");
				System.out.println("Game ID: " + gameid);
				gameIds.add(gameid);
				System.out.println("---------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection();
		}
		
		return gameIds;
	}
	
	//Get details from Game ID.txt
	public static List<Game> getGames(String gameId) throws Exception{
		List<Game> gameIds = new ArrayList<Game>();
		try {
			Scanner scanner = new Scanner(new File("record/" + gameId +".txt"));
			while (scanner.hasNextLine()) {
			   String line = scanner.nextLine().trim();
			   if(!line.isEmpty()) {
				   String[] data = line.split(",");
				   gameIds.add(new Game(data[0],data[1],data[2],Integer.valueOf(data[3]),data[4]));
			   }
			}
		} catch(Exception e) {
			
		}
		
		//Get from database
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM test.records WHERE gameid = ?");
			ps.setString(1, gameId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String gameid = rs.getString("gameid");
				String playerId = rs.getString("playerid");
				String location = rs.getString("location");
				String symbol = rs.getString("symbol");
				String recordDate = rs.getString("recorddate");
				
				gameIds.add(new Game(gameid,playerId,symbol,Integer.valueOf(location),recordDate));
				
				/*System.out.println("ID: " + id);
				System.out.println("Game ID: " + gameid);
				System.out.println("Player ID: " + playerId);
				System.out.println("Location: " + location);
				System.out.println("Symbol: " + symbol);
				System.out.println("Record Date: " + recordDate);
				System.out.println("---------------------------");*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection();
		}
		return gameIds;
	}
	
	private void openDBConnection() {
		// TODO Auto-generated method stub
		try {
			Config properties = new Config();
			try {
				properties.getPropValues();			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = (Connection) DriverManager.getConnection(ConfigObjects.getDbUrl(), ConfigObjects.getUser(), ConfigObjects.getPass());//Establishing connection
;		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void closeDBConnection() {
		// TODO Auto-generated method stub
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
