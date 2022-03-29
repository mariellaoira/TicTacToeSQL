package com.svi.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public RecordDaoImpl() {
		openDBConnection();
	}
	
	public void deleteRecords() {
		try {
			// TODO Auto-generated method stub
			PreparedStatement ps = conn.prepareStatement("DELETE FROM test.records");
			int i = ps.executeUpdate();
			System.out.println(i + " records deleted");
		} catch (SQLException e) {
			
		} finally {
			closeDBConnection();
		}
	}
	
	public void insertRecords(Record record) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO test.records (gameid, playerid, location, symbol, recorddate) values (?,?,?,?,?)");
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
	
	public void updateRecords(Record record) {	
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE test.records SET playerid = ?, location = ?, symbol = ?, recorddate = ? WHERE gameid = ?");
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
	}
	
	public List<Game> getRecords(String gameid) {
		List<Game> gameIds = new ArrayList<Game>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM test.records WHERE gameid = ?");
			ps.setString(1, gameid);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String gameId = rs.getString("gameid");
				String playerId = rs.getString("playerid");
				String location = rs.getString("location");
				String symbol = rs.getString("symbol");
				String recordDate = rs.getString("recorddate");
				
				gameIds.add(new Game(gameid,playerId,symbol,Integer.valueOf(location),recordDate));
				
				System.out.println("ID: " + id);
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
			Class.forName("com.mysql.jdbc.Driver"); 
			connn = ConfigObjects.getDbUrl();
			conn = (Connection) DriverManager.getConnection(ConfigObjects.getDbUrl(), ConfigObjects.getUser(), ConfigObjects.getPass());//Establishing connection
;		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeDBConnection() {
		// TODO Auto-generated method stub
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
}
