package Framework;

import Connection.Connection;

import Connection.Events.*;

/**
 * RemotePlayer is an AbstractPlayer which communicates with us by the gameserver
 * on the network.
 */
public class RemotePlayer extends AbstractPlayer
{
	public RemotePlayer(Connection connection, String name)
	{
		super(name);
	}
}
