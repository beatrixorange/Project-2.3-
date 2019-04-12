package Framework;
import Connection.Connection;
import Interface.AbstractController;
import TicTacToe.TicTacToeController;
import Reversi.ReversiController;

public class PlayController extends AbstractController {
/**
 * PlayController is a controller that initialised and wraps a game.
 * It can be considered to have factory like functionality.
 */
	private boolean turn;

	protected PlayView view;

	/**
	 * PlayController initialises the controller and the game that should run
	 * inside it.
	 * 
	 * @param connection
	 * @param p1
	 * @param p2
	 * @param gameType Name of the game to initialise.
	 * @param startTurn Which player has the first turn. True for p2.
	 */
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

	/**
	 * updateTurn lets the view know the inner game has changed turns.
	 * The view can show that information.
	 *
	 * @param newTurn
	 */
	public void updateTurn(boolean newTurn)
	{
		System.out.println("update turn " + newTurn);
		this.turn = newTurn;
		this.getView().updateTurn(newTurn);
	}

	/**
	 * setScores lets the view know the scores of both players in the inner game.
	 * The view can show that information.
	 *
	 * @param player1Score
	 * @param player2Score
	 */
	public void setScores(int player1Score, int player2Score)
	{
		this.getView().setScores(player1Score, player2Score);
	}

	/**
	 * getView gives the current PlayView.
	 *
	 * @return PlayView
	 */
	public PlayView getView()
	{
		return this.view;
	}
}
