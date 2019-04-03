package Framework;
import Interface.AbstractView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GameView extends AbstractView
{
	private Label turnLabel = null;

	public GameView(AbstractView gameView, AbstractPlayer p1, AbstractPlayer p2)
	{
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);
		
		Label name1 = new Label(p1.getName());
		Label name2 = new Label(p2.getName());
		this.turnLabel = new Label("-");

		Region st00fs = new Region();
		Region st00fs2 = new Region();

		HBox hbox = new HBox();

		hbox.setHgrow(st00fs, Priority.ALWAYS);
		hbox.setHgrow(st00fs2, Priority.ALWAYS);

		hbox.getChildren().addAll(name1, st00fs, this.turnLabel, st00fs2, name2);
		hbox.setAlignment(Pos.TOP_LEFT);
		
		vbox.getChildren().addAll(hbox, gameView.getScene().getRoot());
		
		this.scene = new Scene(vbox,800,800);
	}

	public void switchTurn(boolean newTurn)
	{
		System.out.println("Switch turn in view: " + newTurn);
		this.turnLabel.setText((newTurn) ? "<--" : "-->");
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Game";
	}
}
