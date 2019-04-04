import Connection.Connection;
import Connection.Events.EventHandler;
import Connection.Events.Event;
import Connection.Events.LoginSuccesEvent;
import Framework.AbstractPlayer;
import Framework.GameController;
import Framework.Player;
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

	public void start(Stage primaryStage) throws Exception {

		this.connection = new Connection();
		this.stage = primaryStage;

		new Router(stage, connection);

		this.stage.setTitle("Login");
		this.stage.centerOnScreen();
		
		/*AbstractPlayer 	p1 = new Player("Zwart");
		AbstractPlayer p2 = new Player("Wit");*/
		//AbstractController c = new GameController(connection, p1, p2);
		//AbstractController c = new LoginController(connection);
		AbstractController c = new LobbyController(connection);

		c.show(primaryStage);
	}
	
	public void switchScene(AbstractView view) {	
		// change scene
		this.stage.setScene(view.getScene());
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
