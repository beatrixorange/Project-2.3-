import Connection.Connection;
import Connection.Events.EventHandler;
import Connection.Events.Event;
import Connection.Events.LoginSuccesEvent;
import Framework.AbstractPlayer;
import Framework.PlayController;
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

	private static String host = "145.33.225.170";

	public void start(Stage primaryStage) throws Exception {

		this.connection = new Connection();
		this.stage = primaryStage;
		this.stage.setWidth(1000);
		this.stage.setHeight(800);
		
		
		new Router(this.stage, this.connection);

		stage.setTitle("Login");
		this.stage.centerOnScreen();
		
		AbstractController c = new LoginController(connection);

		c.show(primaryStage);

		try {
			if (host.startsWith("reversitest")) {
				this.connection.connect("localhost");
				this.connection.login(host);
			} else {
				this.connection.connect(host);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() throws Exception
	{
		System.out.println("STOP@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		connection.logout();
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
