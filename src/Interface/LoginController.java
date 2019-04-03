package Interface;
import java.io.IOException;
import java.net.UnknownHostException;

import Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class LoginController extends AbstractController
{
	
	private Connection connection;

	public LoginController(Connection connection)
	{	
		LoginView view = new LoginView();
		view.setOnButtonPressHandler(this);
		
		this.view = view;
		this.connection = connection;
		
		try {
			this.connection.connect();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onButtonPress(String nickName)
	{
		System.out.println("Hoi " + nickName);
		this.connection.login(nickName);
	}
}
