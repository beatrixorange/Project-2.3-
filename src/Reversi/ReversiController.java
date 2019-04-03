package Reversi;
import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.GameController;
import Framework.Tile;
import javafx.scene.layout.VBox;

public class ReversiController extends AbstractGameController implements ClickHandler
{
	ReversiView rView = null;

	public ReversiController(GameController parent)
	{
		this.board = new Board(8, 8);

		this.parentController = parent;

		rView = new ReversiView(this, this.board);

		this.view = rView;
	}

	public void onBoardClick(int posX, int posY)
	{
		if (this.board.getTile(posX, posY) != Tile.EMPTY) {
			return;
		}

		System.out.println("Wat ga jij clicken op " + posX + " en " + posY);

		this.board.putTile(posX, posY, (this.turn) ? Tile.TWO : Tile.ONE);
		this.rView.reDraw(this.board);

		this.switchTurn();
	}

	public boolean isValidMove(int x, int y)
	{
		return false;
	}
}
