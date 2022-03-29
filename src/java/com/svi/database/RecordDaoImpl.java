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
import java.util.concurrent.ThreadLocalRandom;

import com.mysql.jdbc.Driver;
import com.svi.objects.ConfigObjects;
import com.svi.objects.Player;
import com.svi.objects.Record;

public class RecordDaoImpl implements RecordDao {
	
	private static String connectionUrl;
	private static String username;
	private static String password;
	private static String driver;
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
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO test.records values (?,?,?,?,?,?)");
			stmt.setNull(1, Types.NULL);
			stmt.setString(2, Record.getGameid());
			stmt.setString(3, record.getPlayerid());
			stmt.setString(4, record.getLocation());
			stmt.setString(5, record.getSymbol());
			stmt.setString(6, record.getRecorddate());
	
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
			ps.setString(5, record.getGameid());
			
			int i = ps.executeUpdate();
			System.out.println(i + " records updated");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection();
		}	
	}
	
	public Record getRecords(String gameid) {
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
		} finally {
			closeDBConnection();
		}
		return null;
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
		} finally {
			closeDBConnection();
		}
	}

	public Player getGameIds(String playerId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT gameid FROM test.players WHERE playerid = ?");
			ps.setString(1, playerId);
			ResultSet rs = ps.executeQuery();
			
			List<String> gameIds = new ArrayList<String>();
			
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
		return null;
	}
}
