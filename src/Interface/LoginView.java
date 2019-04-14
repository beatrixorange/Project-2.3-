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
	private TextField host = null;
	private TextField port = null;
	
	public LoginView(String defaultHost, int defaultPort)
	{
		VBox start = new VBox();
		start.setAlignment(Pos.CENTER);
		//VBox.setVgrow(start, Priority.ALWAYS);
		
		Label label = new Label("Choose your nickname!");
		Label label1 = new Label("Choose the IP Adress");
		Label label2 = new Label("Choose the Port Number");
		label.setAlignment(Pos.CENTER);
		label.setContentDisplay(ContentDisplay.CENTER);
		
		this.nickName = new TextField();
		
		this.host = new TextField();
		this.host.setText(defaultHost);
		
		this.port = new TextField();
		this.port.setText("" + defaultPort);
		
		this.button = new Button("Submit");
		
		start.getChildren().addAll(label, this.nickName, label1, this.host, label2, this.port, this.button);
		
		
		this.scene = new Scene(start,1200,1200);
	}
	
	public void keyPressed(LoginController handler) {
		this.nickName.setOnAction((ActionEvent e) -> {
			handler.onButtonPress(this.nickName.getText(), this.host.getText(), Integer.parseInt(this.port.getText()));
		});
		this.host.setOnAction((ActionEvent e) -> {
			handler.onButtonPress(this.nickName.getText(), this.host.getText(), Integer.parseInt(this.port.getText()));
		});
		this.port.setOnAction((ActionEvent e) -> {
			handler.onButtonPress(this.nickName.getText(), this.host.getText(), Integer.parseInt(this.port.getText()));
		});
	}
	
	public void setOnButtonPressHandler(LoginController handler)
	{
		this.button.setOnAction((ActionEvent e) -> {
			//public void handle(ActionEvent e) {
				handler.onButtonPress(this.nickName.getText(), this.host.getText(), Integer.parseInt(this.port.getText()));
			//}
		});
	}
	


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Login";
	}
}
