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
	ReversiView rView = null;

	ReversiLogic logic;

	public ReversiController(GameController parent)
	{
		this.board = new Board(8, 8);

		this.logic = new ReversiLogic();

		this.parentController = parent;

		rView = new ReversiView(this, this.board);

		this.view = rView;
	}

	public void onBoardClick(int posX, int posY)
	{
		if (!this.logic.allowMove(this.board, posX, posY, this.turnsTile())) {
			return;
		}

		System.out.println("Wat ga jij clicken op " + posX + " en " + posY);

		this.board.putTile(posX, posY, (this.turn) ? Tile.TWO : Tile.ONE);

		this.switchTurn();

		this.determineHighlightedTiles();

		this.rView.reDraw(this.board);

	}

	public void determineHighlightedTiles()
	{
		ArrayList<int[]> list = new ArrayList<int[]>();

		Tile turnsTile = this.turnsTile();

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Tile t = this.board.getTile(x, y);
				if (t != Tile.EMPTY) {
					continue;
				}

				if (this.logic.isValidMove(this.board, x, y, turnsTile)) {
					list.add(new int[]{x, y});
				}
			}
		}

		this.rView.highlightedTiles = list.toArray(new int[list.size()][]);
	}
}
