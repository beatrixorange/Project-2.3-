import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ReversiView extends ReversiBoard
{
	public ReversiView(ClickHandler clickHandler)
	{
		super(8, 8);

		this.clickHandler = clickHandler;

		Pane pane = new Pane();
		pane.getChildren().addAll(this.draw());
		
		this.scene = new Scene(pane);
	}

	@Override
	public String getTitle()
	{
		return "Reversi";
	}
}
