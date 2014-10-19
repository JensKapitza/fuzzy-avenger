package de.back2heaven.fuzzy.gui.targetview;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

public class PageItems extends ArrayList<PageItem> {
	public PageItems(ObservableList<PageItem> pi) {
		super(pi);
	}

	public PageItems(List<Path> pi) {
		for (Path p : pi) {
			add(new PageItem(p.getFileName().toString(), null, p.toUri()));
		}

	}

	private static final long serialVersionUID = 856918456143627867L;
}