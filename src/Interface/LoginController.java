package Interface;

import java.io.IOException;
import java.net.UnknownHostException;

import Connection.Connection;
import Connection.Events.Event;
import Connection.Events.EventHandler;
import Connection.Events.LoginSuccesEvent;

import javafx.application.Platform;


public class LoginController extends AbstractController
{
	private Connection connection;

	public LoginController(Connection connection, String defaultHost, int defaultPort)
	{	
		LoginView view = new LoginView(defaultHost, defaultPort);
		view.setOnButtonPressHandler(this);
		view.keyPressed(this);
		
		this.view = view;
		this.connection = connection;

		System.out.println("yoo dit is wel goed ja");
		this.connection.register(event -> {
			if (!(event instanceof LoginSuccesEvent)) {
				return;
			}

			System.out.println("log in");

			Platform.runLater(() -> {
				Router.get().toLobby();
			});
		});
	}
	
	public void onButtonPress(String nickName,String host, int port)
	{
		try {
			if (host.startsWith("reversitest")) {
				this.connection.connect("localhost", 7789);
				this.connection.login(host);
			} else {
				this.connection.connect(host, port);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Hoi " + nickName);
		this.connection.login(nickName);
	}
}
