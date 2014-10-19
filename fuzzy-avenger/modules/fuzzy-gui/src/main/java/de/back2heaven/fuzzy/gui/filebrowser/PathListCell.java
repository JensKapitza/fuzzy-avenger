package de.back2heaven.fuzzy.gui.filebrowser;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PathListCell extends ListCell<Path> {

	private static final Image DIR_IMG = new Image(
			PathListCell.class
					.getResourceAsStream("/icons/framework/res/folder_32.png"));
	private static final Image FILE_IMG = new Image(
			PathListCell.class
					.getResourceAsStream("/icons/framework/res/templates_32.png"));

	@Override
	protected void updateItem(Path item, boolean empty) {
		super.updateItem(item, empty);
		setGraphic(null);
		setText(null);
		if (item != null) {
			if (item.getFileName() != null) {
				setText(item.getFileName().toString());
			} else {
				setText("ROOT");
			}
			boolean dir = Files.isDirectory(item);
			Image img = dir ? DIR_IMG : FILE_IMG;

			if (!empty) {
				setGraphic(new ImageView(img));
			}
		}
	}
}
