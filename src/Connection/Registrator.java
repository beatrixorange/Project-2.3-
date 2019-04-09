package Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

import Connection.Events.Event;
import Connection.Events.EventHandler;

//observer class
public class Registrator {
	
	private List<EventHandler> eventHandlers = new ArrayList<EventHandler>();
	
	// classes can register to the observer here.
	public void register(EventHandler eventHandler) {
		synchronized(eventHandlers) {
			eventHandlers.add(eventHandler);
		}
	}
	
	// function that triggers events and takes an object of type Event which is an empty interface.
	public void triggerEvent(Event event) {
		synchronized(eventHandlers) {
			for(EventHandler eventHandler : eventHandlers) {
				eventHandler.handleEvent(event);
			}
		}
	}
	// function that allows for deregistration.
	public void deRegister() {
		synchronized(eventHandlers) {
			eventHandlers.clear();
		}
	}
}
