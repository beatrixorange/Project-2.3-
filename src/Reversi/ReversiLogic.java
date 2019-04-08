package Reversi;

import Framework.Board;
import Framework.Tile;
import java.util.ArrayList;

public class ReversiLogic
{
	public boolean isValidMove(Board board, int x, int y, Tile me)
	{
		if (!board.isEmpty(x, y)) {
			return false;
		}

		return this.disksTurnedByMove(board, x, y, me).length > 0;
	}

	public int[][] disksTurnedByMove(Board board, int x, int y, Tile me)
	{
		ArrayList<int[]> list = new ArrayList<int[]>();

		Tile other = (me == Tile.TWO) ? Tile.ONE : Tile.TWO;

		int[][] directions = new int[][]{
			{ 0,  1}, { 1,  1},
			{ 1,  0}, { 1, -1},
			{ 0, -1}, {-1, -1},
			{-1,  0}, {-1,  1}
		};

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

			list.add(new int[]{xC, yC});

			while (true) {
				xC -= directions[i][0];
				yC -= directions[i][1];

                    		list.add(new int[]{xC, yC});
				
				if (xC == x && yC == y) {
					break;
				}
			}

		}

		return list.toArray(new int[list.size()][]);
	}

	public int[][] determinePossibleMoves(Board board, Tile turnsTile)
	{
		ArrayList<int[]> list = new ArrayList<int[]>();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Tile t = board.getTile(x, y);
				if (t != Tile.EMPTY) {
					continue;
				}

				if (this.isValidMove(board, x, y, turnsTile)) {
					list.add(new int[]{x, y});
				}
			}
		}

		return list.toArray(new int[list.size()][]);
	}

	public int[] calculateScores(Board board)
	{
		int p1 = 0;
		int p2 = 0;

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Tile t = board.getTile(x, y);
				if (t == Tile.ONE) {
					p1++;
				} else if (t == Tile.TWO) {
					p2++;
				}
			}
		}

		return new int[]{p1, p2};
	}
}
