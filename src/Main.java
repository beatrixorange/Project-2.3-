import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	Stage stage = null;
	
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Login");
		primaryStage.show();
		this.stage = primaryStage;
	}
	
	public void switchScene(AbstractView view) {	
		// change scene
		this.stage.setScene(view.getScene());
	}
	
	public static void main(String[] args) {
		
		Application.launch(args);
	}
	
}
