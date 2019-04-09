package Reversi;

import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.GameController;
import Framework.Tile;
import Framework.LocalPlayer;
import Framework.AbstractPlayer;
import Framework.HumanPlayer;
import Framework.BotPlayer;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import Connection.Connection;
import javafx.application.Platform;

public class ReversiController extends AbstractGameController implements ClickHandler
{
	private ReversiLogic logic;

	public ReversiController(Connection connection, AbstractPlayer p1,
			AbstractPlayer p2, GameController parent, boolean startTurn)
	{
		super(connection, p1, p2, parent, startTurn);

		this.board = new ReversiBoard(8, 8, startTurn);

		this.logic = new ReversiLogic();

		boolean myTurn = this.getPlayerAtMove() instanceof HumanPlayer;
		this.view = new ReversiView(this, this.board, myTurn);

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, Tile.byTurn(this.turn));
		this.getView().setHighlightedTiles(possibleMoves);
				
		this.getView().reDraw(this.board);
	}

	public void onBoardClick(int x, int y)
	{
		if (!this.turn && this.player1 instanceof LocalPlayer) {
			this.makePlayerMove(turn, x, y);
		} else if(this.turn && this.player2 instanceof LocalPlayer) {
			this.makePlayerMove(turn, x, y);
		}
	}

	public void makePlayerMove(boolean turn, int x, int y)
	{
		if (!board.isEmpty(x, y)) {
			return;
		}

		int[][] turned = this.logic.disksTurnedByMove(board, x, y, Tile.byTurn(this.turn));
		if (turned.length == 0) {
			System.out.println("NIETS TURNED");
			return;
		}

		for (int i = 0; i < turned.length; i++) {
			this.board.putTile(turned[i][0], turned[i][1], Tile.byTurn(this.turn));
		}
		this.board.putTile(x, y, Tile.byTurn(this.turn));

		int move = this.board.xyToMove(x, y);
	     	System.out.println(x + " " + y);
      		System.out.println(move);
		this.connection.makeMove(move);

		this.getView().setNewChanges(turned);

		this.makeMove(turn, x, y);
	}

	protected void makeServerMove(boolean turn, int x, int y)
	{
		System.out.println("make server move: " + x + " " + y);

		int[][] turned = this.logic.disksTurnedByMove(board, x, y, Tile.byTurn(this.turn));
		System.out.println("turned: " + turned.length);
		for (int i = 0; i < turned.length; i++) {
			this.board.putTile(turned[i][0], turned[i][1], Tile.byTurn(this.turn));
		}
		this.board.putTile(x, y, Tile.byTurn(this.turn));

		this.getView().setNewChanges(turned);
		
		this.makeMove(turn, x, y);
	}

	protected void makeBotMove(boolean turn, int x, int y)
	{

		int[][] turned = this.logic.makeMove(this.board, x, y, Tile.byTurn(this.turn));
		this.getView().setNewChanges(turned);

		this.makeMove(turn, x, y);
	}

	private void makeMove(boolean turn, int x, int y)
	{
		this.switchTurn(!this.turn);

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, Tile.byTurn(this.turn));
		this.getView().setHighlightedTiles(possibleMoves);

		this.getView().reDraw(this.board);

		int[] scores = this.logic.calculateScores(this.board);
		this.parentController.setScores(scores[0], scores[1]);

		final BotPlayer bot;
		if (!this.turn && this.player1 instanceof BotPlayer) {
			bot = (BotPlayer)this.player1;
		} else if (this.turn && this.player2 instanceof BotPlayer) {
			bot = (BotPlayer)this.player2;
		} else {
			bot = null;
		}

		System.out.println("\n\n\nik switch die turn\n\n\n");

		System.out.println(this.board);

		if (bot != null) {
			final boolean botTurn = this.turn;
			(new Thread(() -> {
				try {
					// TODO: Don't do this when remote, waste of time.
					Thread.sleep(500L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Platform.runLater(() -> {
					int[] move = new int[2];

					Board bClone = null;
					try {
						bClone = this.board.clone();
					} catch (CloneNotSupportedException e) {}

					move = bot.move(bClone, botTurn);
					System.out.println(move[0] + " " + move[1]);

					this.makeBotMove(botTurn, move[0], move[1]);
					System.out.println(this.board);

				});
			})).start();
		}
	}

	@Override
	public boolean checkMove(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	public ReversiView getView()
	{
		return (ReversiView)this.view;
	}
}
