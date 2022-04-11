package com.svi.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import com.svi.data.Game;
import com.svi.objects.ConfigObjects;
import com.svi.objects.Player;
import com.svi.objects.Record;

public class RecordDaoImpl implements RecordDao {
	
	private static Connection conn;
	String connn;
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * Initialize openDBConnection() upon loading this class
	 */
	public RecordDaoImpl() {
		openDBConnection();
	}
	
	/**
	 * Delete all records in Table Records (Database) **not used 
	 */
	public void deleteRecords() {
		try {
			// TODO Auto-generated method stub
			PreparedStatement ps = conn.prepareStatement("DELETE * FROM test.records");
			int i = ps.executeUpdate();
			System.out.println(i + " records deleted");
		} catch (SQLException e) {
			
		} finally {
			closeDBConnection();
		}
	}
	
	/**
	 * Insert each player move to Table Records (Database)
	 */
	public void insertRecords(Record record) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO training.tblOiraRecords (gameID, playerID, location, symbol, recorddate) values (?,?,?,?,?)");
			stmt.setString(1, Record.getGameid());
			stmt.setString(2, record.getPlayerid());
			stmt.setString(3, record.getLocation());
			stmt.setString(4, record.getSymbol());
			stmt.setString(5, record.getRecorddate());
	
			int i = stmt.executeUpdate();
			System.out.println(i + " records inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection();
		}
	}
	
	/**
	 * Update a record in Table Records (Database) **not used
	 * @return 
	 */
	public String updateRecords(Record record) {	
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE training.tblOiraRecords SET playerID = ?, location = ?, symbol = ?, recorddate = ? WHERE gameID = ?");
			ps.setString(1, record.getPlayerid());
			ps.setString(2, record.getLocation());
			ps.setString(3, record.getSymbol());
			ps.setString(4, record.getRecorddate());
			ps.setString(5, Record.getGameid());
			
			int i = ps.executeUpdate();
			System.out.println(i + " records updated");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Success"; 
		
	}
	
	/**
	 * Get player's records from Table Records (Database)
	 * @param gameid - search this Game ID in database 
	 * @return gameIds - list of Game IDs
	 */
	public List<Game> getRecords(String gameid) {
		List<Game> gameIds = new ArrayList<Game>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM training.tblOiraRecords WHERE gameID = ?");
			ps.setString(1, gameid);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String gameId = rs.getString("gameID");
				String playerId = rs.getString("playerID");
				String location = rs.getString("location");
				String symbol = rs.getString("symbol");
				String recordDate = rs.getString("recorddate");
				
				gameIds.add(new Game(gameid,playerId,symbol,Integer.valueOf(location),recordDate));
				System.out.println("---------------------------");
				System.out.println("Game ID: " + gameId);
				System.out.println("Player ID: " + playerId);
				System.out.println("Location: " + location);
				System.out.println("Symbol: " + symbol);
				System.out.println("Record Date: " + recordDate);
				System.out.println("---------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return gameIds;
	}
	
	/**
	 * Get player's Game IDs from Table Players (Database)
	 * @param playerId - search this Player ID in database
	 * @return gameIds - return all Game IDs of the player
	 */
	public List<Player> getGameIds(String playerId) {
		List<Player> gameIds = new ArrayList<Player>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM test.players WHERE playerid = ?");
			ps.setString(1, playerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String gameIdd = rs.getString("gameid");
				System.out.println("Game ID: " + gameIdd);
				gameIds.add(new Player(gameIdd));
				System.out.println("---------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameIds;
	}
	
	/**
	 * Insert Player ID with Game ID in Table Players in database
	 * @param player - includes Player ID with Game ID
	 */
	public void insertPlayers(Player player) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO test.players values (?,?,?)");
			stmt.setNull(1, Types.NULL);
			stmt.setString(2, player.getPlayerid());
			stmt.setString(3, Player.getGameid());
	
			int i = stmt.executeUpdate();
			System.out.println(i + " records inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if Player ID with same Game ID already exists in the Database
	 * @param playerId, gameId - Count results from database where playerid and gameid match these params
	 * @return count - result/number of rows found in the database
	 */
	public int checkPlayer(String playerId, String gameId) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM test.players WHERE playerid = ? AND gameid = ?");
			ps.setString(1, playerId);
			ps.setString(2, gameId);
			ResultSet rs = ps.executeQuery();
			//Retrieving the result
			rs.next();
			count = rs.getInt(1);    
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * Open Database Connection using Config and ConfigObject classes
	 */
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
			Class.forName("org.mariadb.jdbc.Driver"); 
			conn = (Connection) DriverManager.getConnection(ConfigObjects.getDbUrl(), ConfigObjects.getUser(), ConfigObjects.getPass());//Establishing connection
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close Database Connection
	 */
	private void closeDBConnection() {
		// TODO Auto-generated method stub
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create new table in database for JUnit
	 * @return new database table - will store gameid, playerid, symbol, location, etc.
	 */
	public void createTable() {
		// TODO Auto-generated method stub
		String column1 = "gameID";
		String column1Type = "varchar(30)";
		String column2 = "playerID";
		String column2Type = "varchar(30)";
		String column3 = "symbol";
		String column3Type = "varchar(30)";
		String column4 = "location";
		String column4Type = "varchar(30)";
		String column5 = "recorddate";
		String column5Type = "varchar(30)";
		String tableName = "tblOiraRecords";
		
	      try {
	    	  Statement stmt = conn.createStatement();
	          String sql = "CREATE TABLE training." + tableName + " (" + 
	        		  column1 + " " + column1Type + ", " +
	                  column2 + " " + column2Type + ", " + 
	                  column3 + " " + column3Type + ", " + 
	                  column4 + " " + column4Type + ", " + 
	                  column5 + " " + column5Type + 
	                  ")"; 
	         stmt.executeUpdate(sql);	  
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } 
	}
	
	/**
	 * Drop the created table in database for JUnit after JUnit testing
	 */
	@Override
	public void dropTable() {
		// TODO Auto-generated method stub
		try {
	    	  Statement stmt = conn.createStatement();
	          String sql = "DROP TABLE training.tblOiraRecords"; 
	         stmt.executeUpdate(sql);  	  
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	}
}
