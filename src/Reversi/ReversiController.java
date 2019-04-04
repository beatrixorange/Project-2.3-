package Reversi;
import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.GameController;
import Framework.Tile;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class ReversiController extends AbstractGameController implements ClickHandler
{
	private ReversiView rView = null;

	private ReversiLogic logic;

	public ReversiController(GameController parent)
	{
		this.board = new Board(8, 8);

		this.logic = new ReversiLogic();

		this.parentController = parent;

		rView = new ReversiView(this, this.board);

		this.view = rView;

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, this.turnSwitches < 4, this.turnsTile());
		this.rView.setHighlightedTiles(possibleMoves);
				
		this.rView.reDraw(this.board);
	}

	public void onBoardClick(int x, int y)
	{
		this.makeMove(turn, x, y);
	}

	public void makeMove(boolean turn, int x, int y)
	{
		if (!board.isEmpty(x, y)) {
			return;
		}

		int[][] turned = this.logic.disksTurnedByMove(board, x, y, this.turnsTile());
		if (this.turnSwitches >= 4 && turned.length == 0) {
			return;
		}

		for (int i = 0; i < turned.length; i++) {
			this.board.putTile(turned[i][0], turned[i][1], this.turnsTile());
		}
		this.board.putTile(x, y, this.turnsTile());

		int[][] newChanges = turned;
		if (this.turnSwitches < 4) {
			newChanges = new int[][]{{x, y}};
		}
		this.rView.setNewChanges(newChanges);

		this.switchTurn();

		int[][] possibleMoves = this.logic.determinePossibleMoves(
			board, this.turnSwitches < 4, this.turnsTile());
		this.rView.setHighlightedTiles(possibleMoves);

		this.rView.reDraw(this.board);

		int[] scores = this.logic.calculateScores(this.board);
		this.parentController.setScores(scores[0], scores[1]);
	}

	@Override
	public boolean checkMove(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
