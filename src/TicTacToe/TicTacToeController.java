package TicTacToe;
import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.Tile;
import javafx.scene.layout.VBox;

public class TicTacToeController extends AbstractGameController implements ClickHandler{
	TicTacToeView  tView = null;
	public TicTacToeController() {
		this.board = new Board(3,3);
		
		
		this.tView = new TicTacToeView(this, this.board);
		

		this.view = tView;
	}

	@Override
	public void onBoardClick(int x, int y) {
		this.board.putDisk(x, y, (this.turn) ? Tile.TWO : Tile.ONE);
		this.tView.reDraw(this.board);
		System.out.println("hoi");
		
		this.switchTurn();
		
	}
}
