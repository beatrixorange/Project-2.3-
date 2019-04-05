package Framework;

import Interface.AbstractController;
import Framework.AbstractPlayer;

import Connection.Events.*;
import Connection.Connection;

import javafx.application.Platform;

public abstract class AbstractGameController extends AbstractController
{
	protected Board board;

	protected AbstractPlayer player1, player2;

	protected boolean turn = false;

	protected int turnSwitches = 0;

	protected GameController parentController;

	protected Connection connection;

	public AbstractGameController(Connection connection, AbstractPlayer p1,
			AbstractPlayer p2, GameController parent)
	{
		System.out.println("AbstractGameController");
		System.out.println(connection);
		this.connection = connection;
		this.player1 = p1;
		this.player2 = p2;
		this.parentController = parent;

		this.connection.register(event -> {
			System.out.println(event);
			Platform.runLater(() -> {
				if (event instanceof TurnEvent) {
					TurnEvent e = (TurnEvent) event;

					int[] xy = this.board.moveToXY(e.move);
					
					if (e.player == this.player1.getName()) {
						this.makeServerMove(false, xy[0], xy[1]);
					} else if (e.player == this.player1.getName()) {
						this.makeServerMove(true, xy[0], xy[1]);
					} else {
						System.out.println("WAATTT? " + e.player + " - " + e.move);
					}
				} else if (event instanceof YourMoveEvent) {
					if (this.player1 instanceof LocalPlayer) {
						this.switchTurn(false);
					} else if (this.player2 instanceof LocalPlayer) {
						this.switchTurn(true);
					} else {
						System.out.println("this shouldn't happen m8");
					}
				} else if (event instanceof MatchWonEvent) {
					System.out.println("You won The game!");
				} else if (event instanceof MatchTiedEvent) {
					System.out.println("You tied The Game!");
				} else if (event instanceof MatchLostEvent) {
					System.out.println("You lost The Game!");
				} else if (event instanceof OpponentDisconnectedEvent) {
					System.out.println("Your opponent is a noob.");
				} else if (event instanceof ForfeitEvent) {
					System.out.println("Your opponent is a noob. x2");
				}
			});
		});
	}

	abstract protected void makeServerMove(boolean turn, int x, int y);

	protected void switchTurn(boolean newTurn)
	{
		this.turn = newTurn;

		this.turnSwitches++;

		if (this.parentController != null) {
			this.parentController.switchedTurn(this.turn);
		}
	}

	abstract public boolean checkMove(int x, int y);
}
