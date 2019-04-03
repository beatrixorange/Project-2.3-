package Interface;
import javafx.stage.Stage;

public abstract class AbstractController {
	protected AbstractView view = null;
	
	public void show(Stage stage)
	{
		stage.setScene(this.view.getScene());
		stage.setTitle(this.view.getTitle());
		stage.show();
	}
}
