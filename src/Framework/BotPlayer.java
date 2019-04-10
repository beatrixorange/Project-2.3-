package Framework;

import Reversi.ReversiLogic;

public class BotPlayer extends LocalPlayer
{
	protected ReversiLogic logic;

	public BotPlayer(String name)
	{
		super(name);

		this.logic = new ReversiLogic();
	}

	public int[] move(Board board, boolean turn)
	{
		return this.logic.bestMove(board, turn, 2);
	}
}
