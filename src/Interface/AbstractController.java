package Interface;

import javafx.stage.Stage;

import Connection.Connection;

public abstract class AbstractController
{
	protected AbstractView view = null;

	protected Connection connection;
	
	public void show(Stage stage)
	{
		stage.setScene(this.view.getScene());
		stage.setTitle(this.view.getTitle());
		stage.show();
	}
	
	public AbstractView getView()
	{
		return this.view;
	}
}
