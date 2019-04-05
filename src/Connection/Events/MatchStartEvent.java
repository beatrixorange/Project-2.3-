package Connection.Events;

public class MatchStartEvent implements Event {
	private String playerToMove;
	private String gameType;
	private String opponent;
	
	public MatchStartEvent(String playerToMove, String gameType, String opponent) {
		this.playerToMove = playerToMove;
		this.gameType = gameType;
		this.opponent = opponent;
	}
	
	public String getPlayerToMove() {
		return playerToMove;
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public String getOpponent() {
		return opponent;
	}

}
