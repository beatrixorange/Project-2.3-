package Connection;

public class TurnEvent implements Event {
	public String player;
	public int move;
	
	public TurnEvent(String player, int move) {
		this.player = player;
		this.move = move;
	}
	
	
}
