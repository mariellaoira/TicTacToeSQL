package com.svi.database;

import java.sql.SQLException;
import com.svi.objects.Record;

public class CheckDatabase {
	public static void main(String[] args) throws SQLException {
		
		RecordDao record = new RecordDaoImpl();
		record.getRecords("Hello");
		System.out.println("Connected With the database successfully");
	}
}
