package Interface;

import javafx.stage.Stage;
import Connection.Connection;

import Framework.GameController;
import Framework.AbstractPlayer;
import Framework.HumanPlayer;
import Framework.RemotePlayer;
import Framework.BotPlayer;

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

	public void startRemoteGame(String gameType, boolean startTurn, String opponent, boolean player)
	{
		System.out.println("startRemoteGame");
		System.out.println(this.connection);
		System.out.println(player);
		AbstractPlayer p1;
		if(player) {
			p1 = new HumanPlayer(this.connection.getUsername());
		}
		else {
			p1 = new BotPlayer(this.connection.getUsername());
		}
		//p1 = new HumanPlayer(this.connection.getUsername());
		AbstractPlayer p2 = new RemotePlayer(this.connection, opponent);

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ \\/");
		GameController c = new GameController(this.connection, p1, p2, gameType, startTurn);
		System.out.println("View:");
		System.out.println(c.getView());

		c.show(this.stage);
	}
}
