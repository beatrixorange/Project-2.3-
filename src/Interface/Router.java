package Interface;

import javafx.stage.Stage;
import Connection.Connection;

import Framework.GameController;
import Framework.AbstractPlayer;
import Framework.Player;
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
		AbstractPlayer p1 = new Player("Ik");
		AbstractPlayer p2 = new RemotePlayer(this.connection, "remote");

		AbstractController c = new GameController(p1, p2, gameType);
		c.show(this.stage);
	}
}
