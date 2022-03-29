package com.svi.data;

public class Game {
	private String gameId;
	private String playerId;
	private String symbol;
	private int location;
	private String createdDate;
	
	public Game(String gameId, String playerId, String symbol,int location, String createdDate) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.symbol = symbol;
		this.location = location;
		this.createdDate = createdDate;
		
	}
	
	public String toString() {
		return String.format("%s,%s,%s,%d,%s", gameId,playerId,symbol,location,createdDate);
	}
	
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}



	
}
