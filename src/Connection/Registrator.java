package Connection;
import java.util.ArrayList;
import java.util.List;

import Connection.Events.Event;
import Connection.Events.EventHandler;

public class Registrator {
	
	private final List <EventHandler> eventHandlers = ListModDebug.getProxy(new ArrayList());
	
	public void register(EventHandler eventHandler) {
		synchronized(eventHandlers) {
			eventHandlers.add(eventHandler);
		}
	}
	
	public void triggerEvent(Event event) {
		synchronized(eventHandlers) {
			for(EventHandler eventHandler : eventHandlers) {
				eventHandler.handleEvent(event);
			}
		}
	}

}
