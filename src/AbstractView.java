import javafx.scene.Scene;

public abstract class AbstractView {
	protected Scene scene = null;
	
	public Scene getScene() {
		return this.scene;
	}
	
	abstract public String getTitle();
}

