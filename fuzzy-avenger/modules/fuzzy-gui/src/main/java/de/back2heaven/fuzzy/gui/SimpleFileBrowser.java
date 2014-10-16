package de.back2heaven.fuzzy.gui;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

public class SimpleFileBrowser extends ListView<Path> {

	public SimpleFileBrowser() throws IOException {
		setCellFactory(c -> {
			ListCell<Path> cell = new PathListCell();
			return cell;
		});

		// go now for files
		Path base = Paths.get(".").toAbsolutePath();
		walk(base);

		setOnMouseClicked(e -> {
			if (e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY) {
				Path p = getSelectionModel().getSelectedItem();
				getItems().clear();
				try {
					walk(p);
				} catch (Exception e1) {
					getScene().getWindow().fireEvent(new EventError(this, e1));
				}
			}

		});

	}

	public void walk(Path base) throws IOException {
		// current dir.
		setUserData(base);
		
		Path parent = base.getParent();

		if (parent != null) {
			getItems().add(parent);
		}

		Files.walkFileTree(base, EnumSet.noneOf(FileVisitOption.class), 1,
				new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file,
							BasicFileAttributes attrs) throws IOException {
						getItems().add(file);
						return super.visitFile(file, attrs);
					}

				});
	}

}
