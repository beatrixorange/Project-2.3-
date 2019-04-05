package Reversi;

import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.GameController;
import Framework.Tile;
import Framework.LocalPlayer;
import Framework.AbstractPlayer;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import Connection.Connection;

public class ReversiController extends AbstractGameController implements ClickHandler
{
	private ReversiView rView = null;

	private ReversiLogic logic;

	public ReversiController(Connection connection, AbstractPlayer p1,
			AbstractPlayer p2, GameController parent)
	{
		super(connection, p1, p2, parent);

		this.board = new Board(8, 8);

		this.logic = new ReversiLogic();

		rView = new ReversiView(this, this.board);

		this.view = rView;

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, this.turnSwitches < 4, Tile.byTurn(this.turn));
		this.rView.setHighlightedTiles(possibleMoves);
				
		this.rView.reDraw(this.board);
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
		if (this.turnSwitches >= 4 && turned.length == 0) {
			return;
		}

		for (int i = 0; i < turned.length; i++) {
			this.board.putTile(turned[i][0], turned[i][1], Tile.byTurn(this.turn));
		}
		this.board.putTile(x, y, Tile.byTurn(this.turn));

		this.connection.makeMove(this.board.xyToMove(x, y));

		int[][] newChanges = turned;
		if (this.turnSwitches < 4) {
			newChanges = new int[][]{{x, y}};
		}
		this.rView.setNewChanges(newChanges);

		this.makeMove(turn, x, y);
	}

	protected void makeServerMove(boolean turn, int x, int y)
	{
		int[][] turned = this.logic.disksTurnedByMove(board, x, y, Tile.byTurn(this.turn));
		for (int i = 0; i < turned.length; i++) {
			this.board.putTile(turned[i][0], turned[i][1], Tile.byTurn(this.turn));
		}
		this.board.putTile(x, y, Tile.byTurn(this.turn));

		int[][] newChanges = turned;
		if (this.turnSwitches < 4) {
			newChanges = new int[][]{{x, y}};
		}
		this.rView.setNewChanges(newChanges);
		
		this.makeMove(turn, x, y);
	}

	private void makeMove(boolean turn, int x, int y)
	{
		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, this.turnSwitches < 4, Tile.byTurn(this.turn));
		this.rView.setHighlightedTiles(possibleMoves);

		this.rView.reDraw(this.board);

		int[] scores = this.logic.calculateScores(this.board);
		this.parentController.setScores(scores[0], scores[1]);

		/*int[] move;
		if (!this.turn) {
			move = this.player1.requestMove();
		} else {
			move = this.player2.requestMove();
		}

		if (move.length == 2) {
			this.makeMove(turn, move[0], move[1]);
		}*/
	}

	@Override
	public boolean checkMove(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
