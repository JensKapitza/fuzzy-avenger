package de.back2heaven.fuzzy.gui.targetview;

import de.back2heaven.fuzzy.gui.events.ToolbarEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TargetToolbar extends HBox {

	public TargetToolbar() {
		setId("targettoolbar");

		Button rotateLeft = new Button("Rotate Left");
		Button rotateRight = new Button("Rotate Right");

		Button save = new Button("Save PDF");

		getChildren().addAll(rotateLeft, rotateRight, save);
		setDisable(true);
		addEventHandler(ToolbarEvent.DISABLE, e -> {
			setDisable(true);
		});
		addEventHandler(ToolbarEvent.ENABLE, e -> {
			setDisable(false);
		});

		save.setOnAction(e -> {

			ToolbarEvent ev = new ToolbarEvent(this, ToolbarEvent.SAVE);
			getScene().lookup("#targetview").fireEvent(ev);

		});

		rotateLeft
				.setOnAction(e -> {

					ToolbarEvent ev = new ToolbarEvent(this,
							ToolbarEvent.ROTATE, false);
					getScene().lookup("#targetview").fireEvent(ev);

				});
		rotateRight
				.setOnAction(e -> {

					ToolbarEvent ev = new ToolbarEvent(this,
							ToolbarEvent.ROTATE, true);
					getScene().lookup("#targetview").fireEvent(ev);

				});

	}

}
