package com.svi.database;
import com.svi.objects.Record;

import java.util.List;

import com.svi.data.Game;
import com.svi.objects.Player;

public interface RecordDao {
	
	public void deleteRecords();
	
	public void insertRecords(Record record);
	
	public String updateRecords(Record record);

	public List<Game> getRecords(String string);
	
	public void insertPlayers(Player player);
	
	public int checkPlayer(String playerId, String gameId);
	
	public List<Player> getGameIds(String string);
	
	public void createTable();
	
	public void dropTable();
}
