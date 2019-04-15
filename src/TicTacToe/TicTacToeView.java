package TicTacToe;
import java.util.ArrayList;
import java.util.List;

import Framework.Board;
import Framework.BoardView;
import Framework.ClickHandler;
import Framework.Tile;
import Reversi.ReversiView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TicTacToeView extends BoardView {
	
	private Rectangle[][] board;
	
	public TicTacToeView(ClickHandler clickHandler, Board board, boolean myTurn){
		// create a view for tictactoe
		this.clickHandler = clickHandler;
		this.myTurn = myTurn;
				
		Pane pane = new Pane();

		//draw a board on with gridpane from the board class
		pane.getChildren().addAll(this.draw(board));
		
		this.scene = new Scene(pane);	
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Tic-Tac-Toe";
	}
	@Override
	protected Node makeGridNode(Tile tile, int x, int y) {
		// make a field of rectangles with text so when clicked on a field it creates a "x" or "o"
		StackPane pane = new StackPane();
		Rectangle rect = new Rectangle(270,260);
		rect.setStrokeWidth(3);
		rect.setFill(null);
		rect.setStroke(Color.BLACK);
		
		//Text text = new Text();
		//text.setFont(Font.font(200));
		if (tile == Tile.ONE) {
			Image xImg = new Image("X.png");
			
			rect.setFill(new ImagePattern(xImg));
			ImageView imageView = new ImageView(xImg);
			pane.getChildren().add(imageView);
			//text.setText("X");
		}else if(tile == Tile.TWO) {
			//text.setText("O");
			Image oImg = new Image("O.jpg");
			rect.setFill(new ImagePattern(oImg));
			ImageView imageView = new ImageView(oImg);
			pane.getChildren().add(imageView);
		}
		pane.getChildren().addAll(rect);
		return pane;
	}
	
}
