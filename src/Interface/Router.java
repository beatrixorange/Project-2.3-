package Interface;

import javafx.stage.Stage;
import Connection.Connection;

import Framework.PlayController;
import Framework.AbstractPlayer;
import Framework.HumanPlayer;
import Framework.RemotePlayer;
import Framework.BotPlayer;
import Framework.GameAI;
import Framework.AIHardness;
import Reversi.ReversiLogic;
import TicTacToe.TicTacToeLogic;

public class Router
{
	private static Router instance = null;

	private Stage stage;
	private Connection connection;

	public Router(Stage stage, Connection connection)
	{
		this.stage = stage;
		this.connection = connection;

		System.out.println("new Router");
		System.out.println(this.connection);

		instance = this;
	}

	public static Router get()
	{
		return instance;
	}

	public void toLobby()
	{
		AbstractController c = new LobbyController(this.connection);
		c.show(this.stage);
	}

	public void startRemoteGame(String gameType, boolean startTurn, String opponent, boolean player, AIHardness hardness)
	{
		System.out.println("startRemoteGame");
		System.out.println(this.connection);

		AbstractPlayer p1;	

		if(player) {
			p1 = new HumanPlayer(this.connection.getUsername());
		}
		else {
			GameAI ai = null;
			switch (gameType) {
			case "Reversi":
				ai = new ReversiLogic(hardness);
				break;
			case "Tic-tac-toe":
				ai = new TicTacToeLogic();
				break;
			default:
				System.out.println("Wat gameType? " + gameType);
				break;
			}
			p1 = new BotPlayer(this.connection.getUsername(), ai);
		}

		GameAI ai = null;

		//AbstractPlayer p1;
		//p1 = new BotPlayer(this.connection.getUsername(), ai);
		//p1 = new HumanPlayer(this.connection.getUsername());
		AbstractPlayer p2 = new RemotePlayer(this.connection, opponent);

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ \\/");
		PlayController c = new PlayController(this.connection, p1, p2, gameType, startTurn);
		System.out.println("View:");
		System.out.println(c.getView());

		c.show(this.stage);
	}
}
