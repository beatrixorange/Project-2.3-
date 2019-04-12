package Reversi;

import Framework.Board;
import Framework.Tile;
import Framework.GameAI;

import java.util.ArrayList;
import java.lang.Comparable;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ReversiLogic implements GameAI
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

		this.makeMove(board, turned, me);

		return turned;
	}

	public void makeMove(Board board, int[][] turned, Tile me)
	{
		for (int i = 0; i < turned.length; i++) {
			board.putTile(turned[i][0], turned[i][1], me);
		}
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

	public int[] bestMove(final Board board, boolean turn, int timeout)
	{
		Tile t = Tile.byTurn(turn);

		BlockingQueue<int[]> q = new ArrayBlockingQueue<int[]>(8);

		int[][] moves = determinePossibleMoves(board, t);

		Thread threads[] = new Thread[moves.length];
		
		for (int i = 0; i < moves.length; i++) {
			int[] move = moves[i];

			
			final int beta = 2147483646; //board.getSizeX()*board.getSizeY() + 4*board.getSizeX() + 4 + 1;

			final int alpha = -2147483646;

			threads[i] = new Thread(() -> {
				Board bClone = null;
				try {
					bClone = (Board)board.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				this.makeMove(bClone, move[0], move[1], t);

				int points = this.neGaMax(bClone, t, 5, alpha, beta, true);

				boolean success = false;

				while (!success) {
					try {
						q.put(new int[]{points, move[0], move[1]});
						success = true;
					} catch (InterruptedException e) {
						System.out.println("InterruptedException");
					}
				}

				/*if (points > maxPoints) {
					maxPoints = points;
					bestX = move[0];
					bestY = move[1];
				}*/
			});
			threads[i].start();
		}

		Thread interruptThread = new Thread(() -> {
			try {
				Thread.sleep(timeout*100L);
			} catch (InterruptedException e) {
				return;
			}

			for (Thread thread : threads) {
				thread.interrupt();
			}

			System.out.println((new Date()) + ": Interrupted threads!");
		});
		interruptThread.start();

		int maxPoints = -2147483646;
		int bestX = -1;
		int bestY = -1;
		
		for (int i = 0; i < moves.length; i++) {
			try {
				int[] data = q.take();

				int points = data[0];

				System.out.println((new Date()) + ": Got points: " + points + ", " + data[1] + " " + data[2]);

				if (points > maxPoints) {
					maxPoints = points;
					bestX = data[1];
					bestY = data[2];
				}
			} catch (InterruptedException e) {
				break;
			}
		}

		interruptThread.interrupt();

		return new int[]{bestX, bestY};
	}

	private int neGaMax(Board board, Tile me, int depth, int alpha, int beta, boolean isUs)
	{
		if (Thread.currentThread().isInterrupted()) {
			this.interrupted();

			return this.color(isUs) * this.evaluateBoard(board, me);
		}

		if (depth == 0) {
			return this.color(isUs) * this.evaluateBoard(board, me);
		}

		int[][] possibleMoves = this.determinePossibleMoves(board, me);
		if (possibleMoves.length == 0) {
			return this.color(isUs) * this.evaluateBoard(board, me);
		}

		int bestScore = -2147483646;

		for (int i = 0; i < possibleMoves.length; i++) {
			Board bClone = null;
			try {
				bClone = (Board)board.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}

			this.makeMove(bClone, possibleMoves[i][0], possibleMoves[i][1], me);

			int score = -this.neGaMax(bClone, me, depth-1, -alpha, -beta, !isUs);
			if (score > bestScore) {
				bestScore = score;
			}

			/*if (score > alpha) {
				alpha = score;
			}

			if (alpha >= beta) {
				return alpha;
			}*/
		}

		if (bestScore == 1) {
			System.out.println("bestScore 1 :< : " + bestScore);
		}

		return bestScore;
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

	/**
	 * interrupted allows making it visible in the profiler when a thread
	 * was interrupted. Call it when interrupted to make it show up.
	 */
	private void interrupted()
	{
		System.out.println("Interupted");
	}

	private int color(boolean isUs)
	{
		if (isUs) {
			return 1;
		}

		return -1;
	}
}
