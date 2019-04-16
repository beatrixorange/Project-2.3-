package Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
		//set background image
		BackgroundImage myBI= new BackgroundImage(new Image("Login.jpg",985,750,false,true),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		//then you set to your node
		start.setBackground(new Background(myBI));
		
		// add textfields to Interface
		Label label = new Label("Choose your nickname!");
		label.setTextFill(Color.ALICEBLUE);
		Label label1 = new Label("Choose the IP Adress");
		label1.setTextFill(Color.ALICEBLUE);
		Label label2 = new Label("Choose the Port Number");
		label2.setTextFill(Color.ALICEBLUE);
		label.setAlignment(Pos.CENTER);
		label.setContentDisplay(ContentDisplay.CENTER);


		this.nickName = new TextField();
		nickName.setMaxWidth(450);
		
		this.host = new TextField();
		host.setMaxWidth(450);
		this.host.setText(defaultHost);
		
		this.port = new TextField();
		port.setMaxWidth(450);
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
