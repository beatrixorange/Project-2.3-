package Connection.Events;

public class ChallengedEvent implements Event {
	private String challenger;
	private String gameType;
	private int challengeNum;
	
	public ChallengedEvent(String challenger, String gameType, int challengeNum) {
		this.challenger = challenger;
		this.gameType = gameType;
		this.challengeNum = challengeNum;
				
	}
	
	public String getChallenger() {
		return challenger;
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public int getChallengeNum() {
		return challengeNum;
	}

}
