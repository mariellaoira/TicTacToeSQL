package com.svi.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

import com.svi.objects.ConfigObjects;
public class Config {
	
	String result = "";
	InputStream inputStream;
 
	public String getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			String dbUrl = prop.getProperty("DB_URL");
			ConfigObjects.setDbUrl(dbUrl);
			String user = prop.getProperty("USER");
			ConfigObjects.setUser(user);
			String pass = prop.getProperty("PASS");
			ConfigObjects.setPass(pass);
			String driver = prop.getProperty("DRIVER");
			ConfigObjects.setDriver(driver);
			
			result = "DB URL = " + dbUrl + "\n USERNAME: " + user + "\n PASSWORD: " + pass + "\n DRIVER: " + driver;
			System.out.println(result + "\nProgram Ran on " + time + " by user = " + user + "\n " + result + "\n \n");
		
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
	
	

}
