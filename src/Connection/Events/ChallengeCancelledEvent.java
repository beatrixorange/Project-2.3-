package Connection.Events;

public class ChallengeCancelledEvent implements Event {
	private int challengeNumC;
	
	public ChallengeCancelledEvent(int challengeNumC) {
		this.challengeNumC = challengeNumC;
	}
	
	public int getChallengeNumC() {
		return challengeNumC;
	}

}
