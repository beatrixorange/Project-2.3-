package Framework;

/**
 * GameAI determines the best next move in a game.
 */
public interface GameAI
{
	public int[] bestMove(Board board, boolean turn, int timeout);
}
