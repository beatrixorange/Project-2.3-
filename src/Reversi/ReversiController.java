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
import Interface.Popup;

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

		this.makeMove(turn, x, y);

		this.switchTurn(!turn);
	}

	protected void makeServerMove(boolean turn, int x, int y)
	{
		this.makeMove(turn, x, y);
	}

	protected void makeBotMove(boolean turn, BotPlayer bot)
	{
		Board bClone = null;
		try {
			bClone = this.board.clone();
		} catch (CloneNotSupportedException e) {}


		int[] move = bot.move(bClone, turn);
		if (move[0] == -1 || move[1] == -1) {
			try {
				throw new Exception("Je kan ja geen zet meer doen ja");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return;
		}

		this.connection.makeMove(this.board.xyToMove(move[0], move[1]));

		this.makeMove(turn, move[0], move[1]);

		this.switchTurn(!turn);
	}

	private void makeMove(boolean turn, int x, int y)
	{
		int[][] turned = this.logic.makeMove(this.board, x, y, Tile.byTurn(turn));

		this.getView().setNewChanges(turned);

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, Tile.byTurn(this.turn));
		this.getView().setHighlightedTiles(possibleMoves);

		this.getView().reDraw(this.board);

		int[] scores = this.logic.calculateScores(this.board);
		this.parentController.setScores(scores[0], scores[1]);
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
