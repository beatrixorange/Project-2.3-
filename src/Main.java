import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Login");
		primaryStage.show();
	}
	
	public void switchScene() {	
		// change scene
	}
	
	public static void main(String[] args) {
		
		Application.launch(args);
	}
	
}
