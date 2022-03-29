package com.svi.database;
import com.svi.objects.Record;
import com.svi.objects.Player;

public interface RecordDao {
	public void deleteRecords();
	
	public void insertRecords(Record record);
	
	public void updateRecords(Record record);

	public Record getRecords(String string);
	
	public void insertPlayers(Player player);
	
	public Player getGameIds(String string);
}
