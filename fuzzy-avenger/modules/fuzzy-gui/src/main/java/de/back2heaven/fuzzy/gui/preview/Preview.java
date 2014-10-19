package de.back2heaven.fuzzy.gui.preview;

import java.nio.file.Files;
import java.nio.file.Path;

import de.back2heaven.de.fuzzy.pdf.PDFFile;
import de.back2heaven.fuzzy.gui.events.PreviewEvent;
import de.back2heaven.fuzzy.gui.filebrowser.FileAccepter;
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
						setImage(FileAccepter.readImages(p, true).get(0));
					}

					if (FileAccepter.isPDF(p)) {
						PDFFile pfile = new PDFFile(p, null);
						setImage(FileAccepter.convertImage(pfile.getPage(0)
								.getImage()));
						pfile.close();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
	}
}
