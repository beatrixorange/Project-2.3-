import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GameView extends AbstractView{
	
	public GameView(AbstractView gameView, AbstractPlayer p1, AbstractPlayer p2) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);
		
		Label name1 = new Label(p1.getName());
		Label name2 = new Label(p2.getName());
		Region st00fs = new Region();
		
		HBox hbox = new HBox();
		hbox.setHgrow(st00fs, Priority.ALWAYS);
		hbox.getChildren().addAll(name1, st00fs, name2);
		hbox.setAlignment(Pos.TOP_LEFT);
		
		vbox.getChildren().addAll(hbox, gameView.getScene().getRoot());
		
		
		this.scene = new Scene(vbox,800,800);
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Game";
	}
}