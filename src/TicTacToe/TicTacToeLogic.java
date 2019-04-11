package TicTacToe;

import Framework.Tile;
import Framework.Board;
import Framework.GameAI;

public class TicTacToeLogic implements GameAI
{
	public int[] bestMove(Board board, boolean turn, int timeout)
	{
		for (int x = 0; x < board.getSizeX(); x++) {
			for (int y = 0; y < board.getSizeY(); y++) {
				if (board.getTile(x, y) != Tile.EMPTY) {
					continue;
				}

				return new int[]{x, y};
			}
		}

		return new int[]{-1, -1};
	}
}
