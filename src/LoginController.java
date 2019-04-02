import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoginController extends AbstractController
{

	public LoginController()
	{	
		LoginView view = new LoginView();
		view.setOnButtonPressHandler(this);
		
		this.view = view;
	}
	
	public void onButtonPress(String nickName)
	{
		System.out.println("Hoi " + nickName);
		
		// Tell network to login.
		// Als we een "Ja :D " krijgen van server:
		//  	sluit deze controller/view en zo, en start de lobby.
	}
}
