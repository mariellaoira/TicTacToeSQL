package com.svi.database;

import com.svi.objects.Record;

public class Main {
	public static void main(String[] args) {
		try {
			getRecords();
			updateRecords();
			getRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getRecords() {		
		RecordDao recordDao = new RecordDaoImpl();
		recordDao.getRecords(Record.getGameid());
	}
	
	private static void updateRecords() {	
		
		Record record = new Record();
		record.setGameid("RandomID");
		record.setPlayerid("Narc");
		record.setLocation("10");
		record.setSymbol("O");
		record.setRecorddate("2022-3-23 20:40:52");
		
		RecordDao recordDao = new RecordDaoImpl();
		recordDao.updateRecords(record);
		}
	
	private static void insertRecords() {
		Record record = new Record();
		record.setGameid("RandomID");
		record.setPlayerid("Player 123/Player 234");
		record.setLocation("9");
		record.setSymbol("X");
		record.setRecorddate("2022-3-23 20:40:52");
		
		RecordDao recordDao = new RecordDaoImpl();
		recordDao.insertRecords(record);
	}
	
	/*(private static Boolean initializeConfig() {
		try {
			ConfigInitiator.initialize("config/config.ini");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CONFIG FILE NOT FOUND");
			return false;
		}
	}*/
}
