package com.svi.objects;

public class Player {
	private static String gameid;
	private static String playerid;
	
	public Player(String playerId, String gameId) {
		this.gameid = gameId;
		this.playerid = playerId;
	}
	
	public Player(String gameIdd) {
		// TODO Auto-generated constructor stub
		this.gameid = gameIdd;
	}

	public String toString() {
		return String.format("%s,%s", playerid,gameid);
	}
	
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
