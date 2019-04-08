package Interface;

import javafx.stage.Stage;

import Connection.Connection;

public abstract class AbstractController
{
	protected AbstractView view = null;

	protected Connection connection;
	
	public void show(Stage stage)
	{
		System.out.println("View:");
		System.out.println(this.view);
		stage.setScene(this.getView().getScene());
		stage.setTitle(this.getView().getTitle());
		stage.show();
	}
	
	public AbstractView getView()
	{
		return this.view;
	}
}
