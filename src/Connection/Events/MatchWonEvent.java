package Connection.Events;

public class MatchWonEvent implements Event {
	private int playerOneScore;
	private int playerTwoScore;
	
	public MatchWonEvent(int playerOneScore, int playerTwoScore) {
		this.playerOneScore = playerOneScore;
		this.playerTwoScore = playerTwoScore;
	}
	
	public int getPlayerOneScore() {
		return playerOneScore;
	}
	
	public int getPlayerTwoScore() {
		return playerTwoScore;
	}
}
