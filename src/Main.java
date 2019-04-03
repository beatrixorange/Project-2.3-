import Connection.Connection;
import Framework.AbstractPlayer;
import Framework.GameController;
import Framework.Player;
import Interface.AbstractController;
import Interface.AbstractView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	Stage stage = null;
	private static Connection connection = null;
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Login");

		this.stage = primaryStage;
		primaryStage.centerOnScreen();
		
		AbstractPlayer p1 = new Player("Pietje");
		AbstractPlayer p2 = new Player("Frank");
		AbstractController c = new GameController(connection, p1, p2);
		c.show(primaryStage);
	}
	
	public void switchScene(AbstractView view) {	
		// change scene
		this.stage.setScene(view.getScene());
	}
	
	public static void main(String[] args) {
		
		connection = new Connection();
		
		Application.launch(args);
	}
	
}
