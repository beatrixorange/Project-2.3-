package Reversi;
import Framework.Board;
import Framework.BoardView;
import Framework.ClickHandler;
import Framework.Tile;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.Node;

public class ReversiView extends BoardView
{
	public int[][] highlightedTiles;

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

	protected Node makeGridNode(Tile tile, int x, int y)
	{
		Rectangle rect = new Rectangle();
		rect.setWidth(50);
		rect.setHeight(50);
		rect.setFill(this.color(Tile.EMPTY));

		if (tile == Tile.EMPTY) {
			if (this.highlightedTiles != null) {
				for (int i = 0; i < this.highlightedTiles.length; i++) {
					if (this.highlightedTiles[i][0] == x && this.highlightedTiles[i][1] == y) {
						rect.setFill(Color.YELLOW);
					}
				}
			}
			return rect;
		}

		StackPane pane = new StackPane();

		Circle circ = new Circle();
		circ.setRadius(20);
		circ.setFill(this.color(tile));

		pane.getChildren().addAll(rect, circ);

		return pane;
	}

	private Color color(Tile disk)
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
