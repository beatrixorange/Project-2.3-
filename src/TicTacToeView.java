import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TicTacToeView extends AbstractGameView {
	
	private Rectangle[][] board;
	Text text = null;
	
	public TicTacToeView(){
		//Pane pane = new Pane();
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(33);
		gridPane.getColumnConstraints().add(column1);
		gridPane.getColumnConstraints().add(column1);
		gridPane.getColumnConstraints().add(column1);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Rectangle rect = new Rectangle(x,y,270,260);
				rect.setStrokeWidth(3);
				rect.setFill(null);
				rect.setStroke(Color.BLACK);
				this.text = new Text();
				gridPane.add(rect, y, x);
				
				board[x][y] = rect;
				
			}
			
		}
		setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				  drawX()
			}
		}
		
		this.scene = new Scene(gridPane);
	}
    public String getValue() {
        return text.getText();
    }
	
	public void drawX() {
		text.setText("X");
	}
	
	public void drawY() {
		text.setText("Y");
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Tic-Tac-Toe";
	}
	
}
