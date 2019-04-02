import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class GameView extends AbstractView{
	
	public GameView() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		
		this.scene = new Scene(vbox);
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Game";
	}
}
