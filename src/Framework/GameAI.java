package Framework;

public interface GameAI
{
	public int[] bestMove(Board board, boolean turn, int timeout);
}
