package Interface;

import javafx.stage.Stage;
import Connection.Connection;

import Framework.GameController;
import Framework.AbstractPlayer;
import Framework.HumanPlayer;
import Framework.RemotePlayer;

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

	public void startRemoteGame(String gameType)
	{
		System.out.println("startRemoteGame");
		System.out.println(this.connection);

		AbstractPlayer p1 = new HumanPlayer("Ik");
		AbstractPlayer p2 = new RemotePlayer(this.connection, "remote");

		AbstractController c = new GameController(this.connection, p1, p2, gameType);
		c.show(this.stage);
	}
}
