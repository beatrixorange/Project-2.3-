package Framework;

import Reversi.ReversiLogic;

public class BotPlayer extends LocalPlayer
{
	protected GameAI ai;

	public BotPlayer(String name, GameAI ai)
	{
		super(name);

		this.ai = ai;
	}

	public int[] move(Board board, boolean turn)
	{
		return this.ai.bestMove(board, turn, 85);
	}
}
