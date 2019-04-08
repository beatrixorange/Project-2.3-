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
	private int[][] highlightedTiles = new int[][]{};
	private int[][] newChanges = new int[][]{};

	private Pane pane;

	public ReversiView(ClickHandler clickHandler, Board board, boolean myTurn)
	{
		this.clickHandler = clickHandler;

		this.myTurn = myTurn;

		// This is a hack.
		// Will explain later.
		this.pane = new Pane();

		this.scene = new Scene(pane);
	}

	@Override
	public String getTitle()
	{
		return "Reversi"; 
	}

	protected Node makeGridNode(Tile tile, int x, int y)
	{
		StackPane pane = new StackPane();

		Rectangle rect = new Rectangle();
		rect.setWidth(50);
		rect.setHeight(50);
		rect.setFill(this.color(Tile.EMPTY));

		pane.getChildren().add(rect);

		if (tile == Tile.EMPTY) {
			for (int i = 0; i < this.highlightedTiles.length; i++) {
				if (this.highlightedTiles[i][0] == x && this.highlightedTiles[i][1] == y) {
					Circle circ = new Circle();
					circ.setRadius(12);
					circ.setFill(Color.GRAY);
					pane.getChildren().add(circ);

					if (!this.myTurn) {
						circ = new Circle();
						circ.setRadius(10);
						circ.setFill(Color.LIGHTGRAY);
						pane.getChildren().add(circ);
					}

					break;
				}
			}

			return pane;
		}

		
		boolean highlight = false;
		for (int i = 0; i < this.newChanges.length; i++) {
			if (this.newChanges[i][0] == x && this.newChanges[i][1] == y) {
				highlight = true;
				break;
			}
		}
		if (highlight) {
			Circle bgCirc = new Circle();
			bgCirc.setRadius(22);
			bgCirc.setFill(Color.YELLOW);
			pane.getChildren().add(bgCirc);
		}
		
		Circle circ = new Circle();
		circ.setRadius(20);
		circ.setFill(this.color(tile));
		pane.getChildren().add(circ);

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
			return Color.GAINSBORO;
		}
	}

	public void setHighlightedTiles(int[][] tiles)
	{
		this.highlightedTiles = tiles;
	}

	public void setNewChanges(int[][] tiles)
	{
		this.newChanges = tiles;
	}
}
