package com.svi.objects;

public class Record {

	private static String gameid;
	private String playerid;
	private String location;
	private String symbol;
	private String recorddate;
	
	public static String getGameid() {
		return gameid;
	}
	
	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	
	/**
	 * @return the playerid
	 */
	public String getPlayerid() {
		return playerid;
	}
	/**
	 * @param playerid the playerid to set
	 */
	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	/**
	 * @return the recorddate
	 */
	public String getRecorddate() {
		return recorddate;
	}
	/**
	 * @param recorddate the recorddate to set
	 */
	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}
}
