package de.back2heaven.fuzzy.gui;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PathListCell extends ListCell<Path> {

	@Override
	protected void updateItem(Path item, boolean empty) {
		super.updateItem(item, empty);
		setGraphic(null);
		setText(null);
		if (item != null) {
			setText(item.getFileName().toString());
			boolean dir = Files.isDirectory(item);
			Image img;
			if (dir) {
				img = new Image(getClass().getResourceAsStream(
						"/icons/framework/res/folder_32.png"));
			} else {
				img = new Image(getClass().getResourceAsStream(
						"/icons/framework/res/templates_32.png"));
			}
			if (!empty) {
				setGraphic(new ImageView(img));
			}
		}
	}
}
