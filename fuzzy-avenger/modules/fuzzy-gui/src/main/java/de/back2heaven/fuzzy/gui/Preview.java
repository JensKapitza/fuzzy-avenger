package de.back2heaven.fuzzy.gui;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.image.ImageView;

public class Preview extends ImageView {
	public Preview() {
		setPreserveRatio(true);
		setFitHeight(400);
		setFitWidth(400);
		addEventHandler(PreviewEvent.TYPE, e -> {

			Path p = e.getPath();
			if (!Files.isDirectory(p)) {
				try {
					if (FileAccepter.isImage(p)) {
						setImage(FileAccepter.readImages(p,true).get(0));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
	}
}
