package Reversi;

import Framework.Board;
import Framework.Tile;

public class ReversiLogic
{
	private boolean initialised;

	public void setInitialised()
	{
		this.initialised = true;
	}

	public boolean allowMove(Board board, int x, int y, Tile me)
	{
		if (!this.initialised) {
			return true;
		}

		return this.isValidMove(board, x, y, me);
	}

	public boolean isValidMove(Board board, int x, int y, Tile me)
	{
		if (!board.isEmpty(x, y)) {
			return false;
		}

		int[][] directions = new int[][]{
			{ 0,  1}, { 1,  1},
			{ 1,  0}, { 1, -1},
			{ 0, -1}, {-1, -1},
			{-1,  0}, {-1,  1}
		};

		Tile other;
		if (me == Tile.ONE) {
			other = Tile.TWO;
		} else {
			other = Tile.ONE;
		}

		for (int i = 0; i < directions.length; i++) {
			int xC = x + directions[i][0];
			int yC = y + directions[i][1];
			// xC -> xCopy, yC -> yCopy
			
			if (!board.isValid(xC, yC)) {
				// Out of board :( 
				continue;
			}

			if (board.getTile(xC, yC) != other) {
				continue;
			}

			// There is one piece of the other player next to ours.
			//
			xC += directions[i][0];
			yC += directions[i][1];

			if (!board.isValid(xC, yC)) {
				continue;
			}

			// Go in direction until none-other tile is found.
			// Or xC/yC is out of board.
			while (board.getTile(xC, yC) == other) {
				xC += directions[i][0];
				yC += directions[i][1];

				if (!board.isValid(xC, yC)) {
					break;
				}
			}
			if (!board.isValid(xC, yC)) {
				// Out of board :( 
				continue;
			}

			if (board.getTile(xC, yC) != me) {
				// Not our tile :(
				continue;
			}

			return true;
		}

		return false;
	}
}
