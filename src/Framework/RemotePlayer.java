package Framework;

import Connection.Connection;

import Connection.Events.*;

public class RemotePlayer extends AbstractPlayer
{
	public RemotePlayer(Connection connection, String name)
	{
		this.name = name;

		connection.register(event -> {
			System.out.println(event);

			       if (event instanceof YourMoveEvent) {
			} else if (event instanceof MatchWonEvent) {
			} else if (event instanceof MatchTiedEvent) {
			} else if (event instanceof MatchLostEvent) {
			} else if (event instanceof OpponentDisconnectedEvent) {
			} else if (event instanceof ForfeitEvent) {
			}
		});
	}
}
