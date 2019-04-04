package Interface;

import javafx.stage.Stage;
import Connection.Connection;

public class Router
{
	private static Router instance = null;

	private Stage stage;
	private Connection connection;

	public Router(Stage stage, Connection connectoin)
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
		AbstractController c = new LobbyController(connection);
		c.show(this.stage);
	}
}
