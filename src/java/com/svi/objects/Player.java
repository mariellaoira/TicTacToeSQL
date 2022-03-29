package com.svi.objects;

public class Player {
	private static String gameid;
	private String playerid;
	/**
	 * @return the gameid
	 */
	public static String getGameid() {
		return gameid;
	}
	/**
	 * @param gameid the gameid to set
	 */
	public static void setGameid(String gameid) {
		Player.gameid = gameid;
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

}
