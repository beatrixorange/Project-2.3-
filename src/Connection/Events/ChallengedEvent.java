package Connection.Events;

public class ChallengedEvent implements Event {
	private static String challenger;
	private String gameType;
	private static int challengeNum;
	
	public ChallengedEvent(String challenger, String gameType, int challengeNum) {
		this.challenger = challenger;
		this.gameType = gameType;
		this.challengeNum = challengeNum;
				
	}
	
	public static String getChallenger() {
		return challenger;
	}
	
	public String getGameType() {
		return gameType;
	}
	
	public static int getChallengeNum() {
		return challengeNum;
	}

}
