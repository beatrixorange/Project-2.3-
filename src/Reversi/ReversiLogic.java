package Reversi;

import Framework.Board;
import Framework.Tile;
import java.util.ArrayList;
import java.lang.Comparable;
import java.util.Collections;

public class ReversiLogic
{
	public boolean isValidMove(Board board, int x, int y, Tile me)
	{
		if (!board.isEmpty(x, y)) {
			return false;
		}

		return this.disksTurnedByMove(board, x, y, me).length > 0;
	}

	public int[][] makeMove(Board board, int x, int y, Tile me)
	{
		int[][] turned = this.disksTurnedByMove(board, x, y, me);
		if (turned.length == 0) {
			return new int[][]{};
		}

		for (int i = 0; i < turned.length; i++) {
			board.putTile(turned[i][0], turned[i][1], me);
		}
		board.putTile(x, y, me);

		return turned;
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

		for (int x = 0; x < board.getSizeX(); x++) {
			for (int y = 0; y < board.getSizeY(); y++) {
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

		for (int x = 0; x < board.getSizeX(); x++) {
			for (int y = 0; y < board.getSizeY(); y++) {
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

	public int[] bestMove(Board board, boolean turn)
	{
		int maxPoints = 0;
		int mX = -1;
		int mY = -1;

		Tile t = Tile.byTurn(turn);

		Board bClone = null;
		try {
			bClone = (Board)board.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		int[][] moves = determinePossibleMoves(bClone, t);

		System.out.println("possible moves: " + moves.length);
		for (int i = 0; i < moves.length; i++) {
			int[] move = moves[i];

			try {
				bClone = (Board)board.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			int[][] disks = disksTurnedByMove(board, move[0], move[1], t);
		
			int count = disks.length;

			int beta = board.getSizeX()*board.getSizeY()+4*board.getSizeX()+4+1;

			int points = this.neGaMax(bClone, t, Tile.other(t), 5, -1, beta);

			if (points > maxPoints) {
				maxPoints = points;
				mX = move[0];
				mY = move[1];
			}
		}

		System.out.println("bestMove: @@@@ " + mX + " " + mY);

		return new int[]{mX, mY};
	}

	private int neGaMax(Board board, Tile me, Tile tile, int depth, int alpha, int beta)
	{
		if (depth == 0) {
			System.out.println("depth == 0");

			int score = this.evaluateBoard(board, me);
			if (me == tile) {
				System.out.println("me is tile: " + me + " " + tile);
				return score;
			}
			System.out.println("me is not tile: " + me + " " + tile);

			return score * -1;
		}

		int[][] possibleMoves = this.determinePossibleMoves(board, me);
		if (possibleMoves.length == 0) {
			System.out.println("possible moves == 0");

			int score = this.evaluateBoard(board, me);
			if (me == tile) {
				return score;
			}

			return score * -1;
		}

		ArrayList<SortedNode> sortedNodes = this.sortedNodes(board, me);
		int bestScore = -1;

		for (SortedNode node : sortedNodes) {
			Board bCopy = node.board;

			int score = this.neGaMax(bCopy, me, Tile.other(tile), depth-1, beta*-1, alpha*-1);
			System.out.println("score: " + score);
			if (score > bestScore) {
				bestScore = score;
			}

			if (score > alpha) {
				alpha = score;
			}

			if (alpha >= beta) {
				break;
			}
		}

		System.out.println("best score: " + bestScore);

		return bestScore;
	}

	class SortedNode implements Comparable<SortedNode>
	{
		Board board;
		int turned;

		public SortedNode(Board b, int t)
		{
			this.board = b;
			this.turned = t;
		}

		public int compareTo(SortedNode n)
		{
			// Sort descending.

			return n.turned - this.turned;
		}
	}

	private ArrayList<SortedNode> sortedNodes(Board board, Tile me)
	{
		ArrayList<SortedNode> list = new ArrayList<SortedNode>();

		for (int x = 0; x < board.getSizeX(); x++) {
			for (int y = 0; y < board.getSizeY(); y++) {
				if (!this.isValidMove(board, x, y, me)) {
					continue;
				}

				Board bCopy = null;
				try {
					bCopy = (Board)board.clone();
				} catch (CloneNotSupportedException e) {
				}

				int[][] turned = this.disksTurnedByMove(board, x, y, me);

				list.add(new SortedNode(bCopy, turned.length));
			}
		
		}

		Collections.sort(list);

		return list;
	}

	private int evaluateBoard(Board board, Tile me)
	{
		int score = 0;

		for (int x = 0; x < board.getSizeX(); x++) {
			for (int y = 0; y < board.getSizeY(); y++) {
				if (board.getTile(x, y) != me) {
					continue;
				}

				boolean xSide = x == 0 || x == board.getSizeX()-1;
				boolean ySide = y == 0 || y == board.getSizeY()-1;

				if (xSide && ySide) {
					// In a corner! :D

					score += 4; // or 5, not sure
				} else if (xSide || ySide) {
					// At a side! :D
					score += 2;
				} else {
					score += 1;
				}
			}
		}

		return score;
	}
}
