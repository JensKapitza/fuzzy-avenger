package de.back2heaven.fuzzy.gui;

import javafx.event.Event;

public class EventError extends Event {

	private static final long serialVersionUID = -8166384703256427652L;
	private Exception exception;

	public EventError(Object source, Exception e) {
		super(source, null, Event.ANY);
		this.exception = e;
	}

	public Exception getException() {
		return exception;
	}

}
