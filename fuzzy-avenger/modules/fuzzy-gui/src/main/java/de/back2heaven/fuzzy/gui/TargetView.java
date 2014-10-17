package de.back2heaven.fuzzy.gui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class TargetView extends ListView<PageItem> {

	private DataFormat pageItemDataFormat = new DataFormat("pageItemDataFormat");

	private static class PageItems extends ArrayList<PageItem> {
		public PageItems(ObservableList<PageItem> pi) {
			super(pi);
		}

		private static final long serialVersionUID = 856918456143627867L;
	}

	public TargetView() {
		setCellFactory(e -> {
			return new PageItemListCell();
		});

		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		setOnDragDetected(de -> {
			Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);

			ClipboardContent content = new ClipboardContent();
			ObservableList<PageItem> pi = getSelectionModel()
					.getSelectedItems();
			content.put(pageItemDataFormat, new PageItems(pi));
			getItems().removeAll(pi);
			dragBoard.setContent(content);
			getSelectionModel().clearSelection();
		});

		setOnDragOver(dragEvent -> {
			dragEvent.acceptTransferModes(TransferMode.MOVE);
		});

		setOnDragDropped(dragEvent -> {
			PageItems item = (PageItems) dragEvent.getDragboard().getContent(
					pageItemDataFormat);

			EventTarget target = dragEvent.getTarget();

			Node p = null;
			if (target instanceof Node) {
				p = (Node) target;
			}

			while (!(p instanceof PageItemListCell) && p != null) {
				p = p.getParent();
			}

			if (p instanceof PageItemListCell) {
				int index = getItems()
						.indexOf(((PageItemListCell) p).getItem());
				getItems().addAll(index, item);
			} else {
				getItems().addAll(item);

			}

			dragEvent.setDropCompleted(true);

		});

	}
}
