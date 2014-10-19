package de.back2heaven.fuzzy.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class EventError extends Event {

	public static final EventType<EventError> ERROR = new EventType<>(ANY, "ERROR");
	private static final long serialVersionUID = -8166384703256427652L;
	private Exception exception;

	public EventError(Object source, Exception e) {
		super(source, null, ERROR);
		this.exception = e;
	}

	public Exception getException() {
		return exception;
	}

}
