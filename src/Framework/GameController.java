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
		boolean reversi = true;

		if (reversi) {
			AbstractGameController controller = new ReversiController(this);
			this.gView = new GameView(controller.getView(), p1, p2, true);
		} else {

			AbstractGameController controller = new TicTacToeController();
			this.gView = new GameView(controller.getView(), p1, p2);
		}
		
		this.view = gView;
		this.connection = connection;
	}

	public void switchedTurn(boolean newTurn)
	{
		this.turn = newTurn;
		this.gView.switchTurn(newTurn);
	}

	public void setScores(int player1, int player2)
	{
		this.gView.setScores(player1, player2);
	}
}
