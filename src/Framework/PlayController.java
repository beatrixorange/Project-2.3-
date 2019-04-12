package Framework;
import Connection.Connection;
import Interface.AbstractController;
import TicTacToe.TicTacToeController;
import Reversi.ReversiController;

public class PlayController extends AbstractController
{
	private boolean turn;

	protected PlayView view;

	public PlayController(Connection connection, AbstractPlayer p1, AbstractPlayer p2, String gameType, boolean startTurn)
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

		this.view = new PlayView(controller.getView(), p1, p2, hasScores);
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

	public PlayView getView()
	{
		return this.view;
	}
}
