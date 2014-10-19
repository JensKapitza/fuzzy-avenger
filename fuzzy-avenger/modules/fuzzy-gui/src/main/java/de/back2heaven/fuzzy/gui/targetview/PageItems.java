package de.back2heaven.fuzzy.gui.targetview;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.ObservableList;

public class PageItems extends ArrayList<PageItem> {
	public PageItems(ObservableList<PageItem> pi) {
		super(pi);
	}

	public PageItems(Path pi) {
		super(Arrays.asList(new PageItem(pi.getFileName().toString(), null, pi
				.toUri())));
	}

	private static final long serialVersionUID = 856918456143627867L;
}