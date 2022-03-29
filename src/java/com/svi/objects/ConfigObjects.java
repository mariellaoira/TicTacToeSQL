package com.svi.objects;

public class ConfigObjects {

	private static String dbUrl;
	private static String user;
	private static String pass;
	private static String driver;
	/**
	 * @return the dbUrl
	 */
	public static String getDbUrl() {
		return dbUrl;
	}
	/**
	 * @param dbUrl the dbUrl to set
	 */
	public static void setDbUrl(String dbUrl) {
		ConfigObjects.dbUrl = dbUrl;
	}
	/**
	 * @return the user
	 */
	public static String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public static void setUser(String user) {
		ConfigObjects.user = user;
	}
	/**
	 * @return the pass
	 */
	public static String getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public static void setPass(String pass) {
		ConfigObjects.pass = pass;
	}
	/**
	 * @return the driver
	 */
	public static String getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public static void setDriver(String driver) {
		ConfigObjects.driver = driver;
	}
}
