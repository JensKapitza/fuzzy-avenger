package de.back2heaven.fuzzy.gui.filebrowser;

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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import de.back2heaven.fuzzy.gui.events.EventError;
import de.back2heaven.fuzzy.gui.events.PreviewEvent;
import de.back2heaven.fuzzy.gui.targetview.PageItems;
import de.back2heaven.fuzzy.gui.targetview.TargetView;

public class SimpleFileBrowser extends ListView<Path> {

	public SimpleFileBrowser(Path base) throws IOException {
		setCellFactory(c -> {
			ListCell<Path> cell = new PathListCell();
			return cell;
		});

		getSelectionModel().selectedItemProperty().addListener(
				(c, old, newO) -> {
					if (newO != null) {
						PreviewEvent pe = new PreviewEvent(this, newO);
						getScene().lookup("#preview").fireEvent(pe);
					}

				});
		// go now for files
		walk(base == null ? Paths.get(".").toAbsolutePath() : base
				.toAbsolutePath());

		setOnMouseClicked(e -> {
			if (e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY) {
				Path p = getSelectionModel().getSelectedItem();
				if (Files.isDirectory(p)) {

					getItems().clear();
					try {
						walk(p);
					} catch (Exception e1) {
						getScene().getWindow().fireEvent(
								new EventError(this, e1));
					}
				}
			}

		});

		setOnDragDetected(de -> {
			Dragboard dragBoard = startDragAndDrop(TransferMode.COPY);

			ClipboardContent content = new ClipboardContent();
			Path pi = getSelectionModel().getSelectedItem();

			content.put(TargetView.PAGE_ITEM_FORMAT, new PageItems(pi));
			dragBoard.setContent(content);
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
						if (Files.isDirectory(file)
								|| FileAccepter.accepts(file)) {
							getItems().add(file);
						}
						return super.visitFile(file, attrs);
					}

				});
	}

}
