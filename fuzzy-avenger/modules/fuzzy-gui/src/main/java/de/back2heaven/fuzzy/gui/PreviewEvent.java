package de.back2heaven.fuzzy.gui;

import java.nio.file.Path;

import javafx.event.Event;
import javafx.event.EventType;

public class PreviewEvent extends Event {

	private static final long serialVersionUID = -8382514926798455049L;
	public static final EventType<PreviewEvent> TYPE = new EventType<>(ANY,"PREV");
	private Path path;

	public PreviewEvent(Object source, Path path) {
		super(source, null, TYPE);
		this.path = path;
	}

	public Path getPath() {
		return path;
	}
}
