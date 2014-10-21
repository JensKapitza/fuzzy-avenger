package de.back2heaven.fuzzy.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class ToolbarEvent extends Event {

	private static final long serialVersionUID = -8382514926798455049L;
	public static final EventType<ToolbarEvent> DISABLE = new EventType<>(ANY,
			"DISABLE");
	public static final EventType<ToolbarEvent> ENABLE = new EventType<>(ANY,
			"ENABLE");
	public static final EventType<ToolbarEvent> ROTATE = new EventType<>(ANY,
			"ROTATE");
	public static final EventType<ToolbarEvent> SAVE = new EventType<>(ANY,
			"SAVE");

	private boolean clockwise;

	public ToolbarEvent(Object source, EventType<ToolbarEvent> event, boolean b) {
		super(source, null, event);
		clockwise = b;
	}

	public ToolbarEvent(Object source, EventType<ToolbarEvent> event) {
		super(source, null, event);
	}

	public boolean isClockwise() {
		return clockwise;
	}

}
