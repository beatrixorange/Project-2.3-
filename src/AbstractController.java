import javafx.stage.Stage;

public abstract class AbstractController {
	AbstractView view = null;
	
	public void show(Stage stage)
	{
		stage.setScene(this.view.getScene());
	}
	
}
