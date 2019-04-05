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
		if(checkMove(x,y)) {
		this.board.putTile(x, y, (this.turn) ? Tile.TWO : Tile.ONE);
		this.tView.reDraw(this.board);
		if(checkWin()) {
			System.out.println("Winner");
		};
		
		this.switchTurn();
		}
		else
			return;
	}

	@Override
	public boolean checkMove(int x, int y) {
		if (this.board.getTile(x, y) != Tile.EMPTY) {
			return false;
		}
		return true;
	}
	public boolean checkWin() {
		boolean victory = false;
		
		Tile[] tiles = new Tile[] {Tile.ONE, Tile.TWO};
		
		
		for (int tile = 0; tile < tiles.length; tile++) {
			Tile player = tiles[tile];
			for(int a = 0; a<3 ; a ++) {
				if(this.board.getTile(a,0) == player && this.board.getTile(a,1) == player && this.board.getTile(a,2) == player)
				{
						System.out.println("yey");
						victory = true;
						return victory;
				}
				if(this.board.getTile(0,a) == player && this.board.getTile(1,a) == player && this.board.getTile(2,a) == player)
				{
					System.out.println("yey");
					victory = true;
					return victory;
				}
			}
			if(this.board.getTile(0,0) == player && this.board.getTile(1,1) == player && this.board.getTile(2,2) == player)
			{
				System.out.println("yey");
				victory = true;
				return victory;
				
			}
			if(this.board.getTile(0,2) == player && this.board.getTile(1,1) == player && this.board.getTile(2,0) == player)
			{
				System.out.println("yey");
				victory = true;
				return victory;
			}
		}
		return victory;
	}
}