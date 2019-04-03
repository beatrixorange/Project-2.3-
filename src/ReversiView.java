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
import javafx.scene.Node;

public class ReversiView extends BoardView
{
	public ReversiView(ClickHandler clickHandler, Board board)
	{
		this.clickHandler = clickHandler;

		// This is a hack.
		// Will explain later.
		Pane pane = new Pane();
		pane.getChildren().addAll(this.draw(board));
		
		this.scene = new Scene(pane);
	}

	@Override
	public String getTitle()
	{
		return "Reversi";
	}

	public Node makeGridNode(Disk disk)
	{
		Rectangle rect = new Rectangle();
		rect.setWidth(50);
		rect.setHeight(50);
		rect.setFill(this.color(disk));

		return rect;
	}

	private Color color(Disk disk)
	{
		switch (disk) {
		case ONE:
			return Color.BLACK;
		case TWO:
			return Color.WHITE;
		default:
			return Color.GRAY;
		}
	}
}
