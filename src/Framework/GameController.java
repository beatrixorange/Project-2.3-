package Framework;
import Connection.Connection;
import Interface.AbstractController;
import TicTacToe.TicTacToeController;
import Reversi.ReversiController;

public class GameController extends AbstractController
{
	private boolean turn;

	protected GameView view;

	public GameController(Connection connection, AbstractPlayer p1, AbstractPlayer p2, String gameType, boolean startTurn)
	{
		this.connection = connection;

		boolean hasScores;

		AbstractGameController controller;
		if (gameType.equalsIgnoreCase("reversi")) {
			controller = new ReversiController(this.connection, p1, p2, this, startTurn);
			hasScores = true;
		} else {
			controller = new TicTacToeController(this.connection, p1, p2, this, startTurn);
			hasScores = false;
		}

		this.view = new GameView(controller.getView(), p1, p2, hasScores);
		System.out.println("this.view : ");
		System.out.println(this.view);

		this.updateTurn(startTurn);
	}

	public void updateTurn(boolean newTurn)
	{
		System.out.println("update turn " + newTurn);
		this.turn = newTurn;
		this.getView().updateTurn(newTurn);
	}

	public void setScores(int player1, int player2)
	{
		this.getView().setScores(player1, player2);
	}

	public GameView getView()
	{
		return this.view;
	}
}
