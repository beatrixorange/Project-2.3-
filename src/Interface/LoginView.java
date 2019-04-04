package Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LoginView extends AbstractView {	
	private Button button = null;
	private TextField nickName = null;
	
	public LoginView()
	{
		
		VBox start = new VBox();
		start.setAlignment(Pos.CENTER);
		//VBox.setVgrow(start, Priority.ALWAYS);
		
		Label label = new Label("Choose your nickname!");
		label.setAlignment(Pos.CENTER);
		label.setContentDisplay(ContentDisplay.CENTER);
		
		this.nickName = new TextField();
		
		this.button = new Button("Submit");
		
		start.getChildren().addAll(label, this.nickName, this.button);
		
		
		this.scene = new Scene(start,1200,1200);
	}
		
	public void setOnButtonPressHandler(LoginController handler)
	{
		this.button.setOnAction((ActionEvent e) -> {
			//public void handle(ActionEvent e) {
				handler.onButtonPress(this.nickName.getText());
			//}
		});
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Login";
	}
}
