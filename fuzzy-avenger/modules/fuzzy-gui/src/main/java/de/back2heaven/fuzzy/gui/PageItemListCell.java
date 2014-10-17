package de.back2heaven.fuzzy.gui;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class PageItemListCell extends ListCell<PageItem> {

	@Override
	protected void updateItem(PageItem item, boolean empty) {
		super.updateItem(item, empty);
		setGraphic(null);
		setText(null);
		if (item != null) {
			setText(item.getName());
			if (!empty && item.getIcon() != null) {
				ImageView iv = new ImageView(item.getIcon());
				iv.setFitWidth(16);
				iv.setPreserveRatio(true);
				setGraphic(iv);
			}
		}
	}
}
