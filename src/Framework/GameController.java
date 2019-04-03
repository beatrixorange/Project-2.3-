package Framework;
import Connection.Connection;
import Interface.AbstractController;
import TicTacToe.TicTacToeController;
import Reversi.ReversiController;

public class GameController extends AbstractController
{
	private Connection connection;

	private boolean turn;

	private GameView gView;
	
	public GameController(Connection connection, AbstractPlayer p1, AbstractPlayer p2)
	{
		// Beeld je in dat dit een super mooie factory is die deze instance maakt.
		//AbstractGameController controller = new ReversiController(this);
		AbstractGameController controller = new TicTacToeController();


		this.gView = new GameView(controller.getView(), p1, p2);
		
		this.view = gView;
		this.connection = connection;
	}

	public void switchedTurn(boolean newTurn)
	{
		this.turn = newTurn;
		this.gView.switchTurn(newTurn);
	}
}
