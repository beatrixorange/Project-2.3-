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

		this.rView.setHighlightedTiles(this.determineHighlightedTiles());

		this.rView.reDraw(this.board);
	}

	public void onBoardClick(int posX, int posY)
	{
		if (this.turnSwitches >= 4 &&
			!this.logic.isValidMove(this.board, posX, posY, this.turnsTile()))
		{
			return;
		}

		System.out.println("Wat ga jij clicken op " + posX + " en " + posY);

		this.board.putTile(posX, posY, (this.turn) ? Tile.TWO : Tile.ONE);

		this.switchTurn();

		this.rView.setHighlightedTiles(this.determineHighlightedTiles());

		this.rView.reDraw(this.board);
	}

	public int[][] determineHighlightedTiles()
	{
		ArrayList<int[]> list = new ArrayList<int[]>();

		Tile turnsTile = this.turnsTile();

		if (this.turnSwitches < 4) {
			for (int x = 3; x < 5; x++) {
				for (int y = 3; y < 5; y++) {
					list.add(new int[]{x, y});
				}
			}
		} else {
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
		}

		return list.toArray(new int[list.size()][]);
	}
}
