import Connection.Connection;
import Connection.Events.EventHandler;
import Connection.Events.Event;
import Connection.Events.LoginSuccesEvent;
import Framework.AbstractPlayer;
import Framework.GameController;
import Interface.AbstractController;
import Interface.LoginController;
import Interface.LobbyController;
import Interface.AbstractView;
import Interface.Router;
import Interface.LobbyController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	private Stage stage = null;
	private static Connection connection = null;

	private static String host = "localhost";

	public void start(Stage primaryStage) throws Exception {

		this.connection = new Connection();
		this.stage = primaryStage;

		new Router(this.stage, this.connection);

		this.stage.setTitle("Login");
		this.stage.centerOnScreen();
		
		AbstractController c = new LobbyController(connection);

		c.show(primaryStage);

		try {
			this.connection.connect(host);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.connection.login("pietdevries");
	}
	
	public void switchScene(AbstractView view) {	
		// change scene
		this.stage.setScene(view.getScene());
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			host = args[0];
		}

		Application.launch(args);
	}
}
