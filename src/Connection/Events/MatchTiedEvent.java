package Connection.Events;

public class MatchTiedEvent implements Event {
	private int playerOneScore;
	private int playerTwoScore;
	
	public MatchTiedEvent(int playerOneScore, int playerTwoScore) {
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


