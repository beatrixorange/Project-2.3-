package Reversi;

import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.PlayController;
import Framework.Tile;
import Framework.LocalPlayer;
import Framework.AbstractPlayer;
import Framework.HumanPlayer;
import Framework.BotPlayer;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import Connection.Connection;
import javafx.application.Platform;
import Interface.Popup;

/**
 * ReversiController is a AbstractGameController for reversi.
 */
public class ReversiController extends AbstractGameController implements ClickHandler
{
	private ReversiLogic logic;

	/**
	 * @inheritDoc
	 */
	public ReversiController(Connection connection, AbstractPlayer p1,
			AbstractPlayer p2, PlayController parent, boolean startTurn)
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

	/**
	 * onBoardClick satisfies ClickHandler.
	 */
	public void onBoardClick(int x, int y)
	{
		if (!this.turn && this.player1 instanceof HumanPlayer) {
			this.makePlayerMove(false, x, y);
		} else if(this.turn && this.player2 instanceof HumanPlayer) {
			this.makePlayerMove(true, x, y);
		}
	}

	/**
	 * makePlayerMove tries to make the given move for a humanplayer identified
	 * by the given turn.
	 *
	 * @param turn The player to move a move for.
	 * @param x
	 * @param y
	 */
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

		int move = this.board.xyToMove(x, y);
	     	System.out.println(x + " " + y);
      		System.out.println(move);
		this.connection.makeMove(move);

		this.makeMove(turn, x, y);

		this.switchTurn(!turn);

		this.getView().reDraw(this.board);
	}

	/**
	 * makeServerMove tries to make the given move for a remoteplayer identified
	 * by the given turn.
	 *
	 * @param turn The player to make a move for.
	 * @param x
	 * @param y
	 */
	protected void makeServerMove(boolean turn, int x, int y)
	{
		this.makeMove(turn, x, y);

		this.getView().reDraw(this.board);
	}

	/**
	 * makeBotMove tries to make the given move for a botplayer identified
	 * by the given turn.
	 *
	 * @param turn The player to make a move for.
	 * @param bot The bot instance making the move.
	 */
	protected void makeBotMove(boolean turn, BotPlayer bot)
	{
		Board bClone2 = null;
		try {
			bClone2 = this.board.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Board bClone = bClone2;

		// Run AI in a different thread, so the user interface is not being
		// stalled by it.

		Thread t = new Thread(() -> {
			int[] move = bot.move(bClone, turn);
			if (move[0] == -1 || move[1] == -1) {
				try {
					throw new Exception("Je kan ja geen zet meer doen ja");
				} catch (Exception e) {
					e.printStackTrace();
				}

				return;
			}

			int oldTurnSwitches = this.turnSwitches;

			Platform.runLater(() -> {
				if (this.turnSwitches != oldTurnSwitches) {
					try {
						throw new Exception("WAAAT? the game has moved on without us?");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				this.connection.makeMove(this.board.xyToMove(move[0], move[1]));

				this.makeMove(turn, move[0], move[1]);

				this.switchTurn(!turn);

				this.getView().reDraw(this.board);
			});
		});
		t.start();
	}

	private void makeMove(boolean turn, int x, int y)
	{
		int[][] turned = this.logic.makeMove(this.board, x, y, Tile.byTurn(turn));

		this.getView().setNewChanges(turned);

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, Tile.byTurn(!turn));
		this.getView().setHighlightedTiles(possibleMoves);

		int[] scores = this.logic.calculateScores(this.board);
		this.parentController.setScores(scores[0], scores[1]);
	}

	public ReversiView getView()
	{
		return (ReversiView)this.view;
	}
}
