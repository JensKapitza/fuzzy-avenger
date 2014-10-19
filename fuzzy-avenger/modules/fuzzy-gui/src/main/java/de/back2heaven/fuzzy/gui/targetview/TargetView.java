package de.back2heaven.fuzzy.gui.targetview;

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
			Dragboard dragBoard = startDragAndDrop(TransferMode.COPY_OR_MOVE);

			ClipboardContent content = new ClipboardContent();
			ObservableList<PageItem> pi = getSelectionModel()
					.getSelectedItems();
			content.put(pageItemDataFormat, new PageItems(pi));
//			getItems().removeAll(pi);
			dragBoard.setContent(content);
			getSelectionModel().clearSelection();
		});

		setOnDragOver(dragEvent -> {
			dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
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
			int index = -1;
			System.out.println(dragEvent.getTransferMode());
			if (dragEvent.getTransferMode() == TransferMode.MOVE){
				System.out.println("---");
				getItems().removeAll(item);
			} else {
				ArrayList<PageItem> itemsx = new ArrayList<>(getItems());
				itemsx.addAll(item);
				// copy should change name #1
				item.forEach(e -> {
					e.countUp(itemsx);
				});
			}
			
			if (p instanceof PageItemListCell) {
				index = getItems().indexOf(((PageItemListCell) p).getItem());
			}
			if (index != -1) {
				getItems().addAll(index, item);
			} else {
				getItems().addAll(item);

			}

			dragEvent.setDropCompleted(true);

		});

	}
}
