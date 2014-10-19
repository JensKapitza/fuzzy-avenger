package de.back2heaven.fuzzy.gui.preview;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import de.back2heaven.de.fuzzy.pdf.PDFFile;
import de.back2heaven.fuzzy.gui.events.PreviewEvent;
import de.back2heaven.fuzzy.gui.filebrowser.FileAccepter;
import de.back2heaven.fuzzy.gui.targetview.PageItem;
import de.back2heaven.fuzzy.gui.targetview.PageItemListCell;
import de.back2heaven.fuzzy.gui.targetview.PageItems;
import de.back2heaven.fuzzy.gui.targetview.TargetView;

public class Preview extends VBox {
	private ImageView imageView = new ImageView();
	private ListView<PageItem> pages = new ListView<>();

	public Preview() {
		setId("preview");
		pages.setCellFactory(e -> {
			return new PageItemListCell();
		});

		getChildren().add(pages);
		// getChildren().add(imageView);
		VBox.setVgrow(imageView, Priority.ALWAYS);

		// export pages
		pages.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		pages.setOnDragDetected(de -> {
			Dragboard dragBoard = startDragAndDrop(TransferMode.COPY);

			ClipboardContent content = new ClipboardContent();
			ObservableList<PageItem> pi = pages.getSelectionModel()
					.getSelectedItems();

			content.put(TargetView.PAGE_ITEM_FORMAT, new PageItems(pi));
			dragBoard.setContent(content);
		});

		// pages.getSelectionModel()
		// .selectedItemProperty()
		// .addListener(
		// (a, b, c) -> {
		// if (c != null) {
		// Object o = c.getCache();
		// if (o != null) {
		// if (o instanceof Image) {
		// imageView.setImage((Image) o);
		// } else if (o instanceof BufferedImage) {
		// imageView.setImage(FileAccepter
		// .convertImage((BufferedImage) o));
		// }
		// }
		// }
		// });

		imageView.setPreserveRatio(true);
		imageView.setFitHeight(400);
		imageView.setFitWidth(400);
		addEventHandler(
				PreviewEvent.TYPE,
				e -> {
					pages.getItems().clear();
					Path p = e.getPath();
					if (!Files.isDirectory(p)) {
						try {
							if (FileAccepter.isImage(p)) {

								List<Image> imgs = FileAccepter.readImages(p,
										false);

								for (int i = 0; i < imgs.size(); i++) {
									PageItem pi = new PageItem(p.getFileName()
											.toString() + " #" + (i + 1), null,
											p.toUri(), i);
									// pi.setCache(imgs.get(i));

									pages.getItems().add(pi);
								}

								// imageView.setImage(imgs.get(0));
							}

							if (FileAccepter.isPDF(p)) {
								PDFFile pfile = new PDFFile(p, null);
								for (int i = 0; i < pfile.getPages(); i++) {
									PageItem pi = new PageItem(p.getFileName()
											.toString() + " #" + (i + 1), null,
											p.toUri(), i);
									// pi.setCache(pfile.getPage(i).getImage());

									pages.getItems().add(pi);
								}

								// imageView.setImage(FileAccepter.convertImage(pfile
								// .getPage(0).getImage()));
								pfile.close();
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

				});
	}
}
