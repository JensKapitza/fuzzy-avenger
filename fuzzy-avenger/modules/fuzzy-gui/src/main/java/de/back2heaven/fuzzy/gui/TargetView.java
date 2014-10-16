package de.back2heaven.fuzzy.gui;

import javafx.scene.control.ListView;

public class TargetView extends ListView<PageItem> {

	public TargetView() {
		setCellFactory(e -> {
			return new PageItemListCell();
		});
	}
}
