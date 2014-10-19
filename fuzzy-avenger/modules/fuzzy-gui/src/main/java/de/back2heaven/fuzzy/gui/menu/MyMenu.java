package de.back2heaven.fuzzy.gui.menu;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class MyMenu extends MenuBar {
	private Menu file = new Menu("File");
	private Menu edit = new Menu("Edit");
	private Menu help = new Menu("Help");

	public MyMenu() {
		file.getItems().addAll(getFileItems());
		getMenus().addAll(file, edit, help);
	}

	private MenuItem[] getFileItems() {
		return new MenuItem[] { new MenuItem("Duplicate Window") {
			{
				setOnAction(e -> {
					Stage stage = new Stage();
					stage.titleProperty().bind(
							((Stage) getScene().getWindow()).titleProperty());
					BorderPane root = new BorderPane();
					BorderPane from = (BorderPane) getScene().getRoot();
					root.setCenter(from.getCenter());
					root.setLeft(from.getLeft());
					root.setRight(from.getRight());
					root.setBottom(from.getBottom());
					stage.setScene(new Scene(root));
					stage.sizeToScene();
					stage.centerOnScreen();
					stage.show();

				});
			}
		}, new MenuItem("Close") {
			{
				setOnAction(e -> {
					Window w = getScene().getWindow();
					w.fireEvent(new WindowEvent(w,
							WindowEvent.WINDOW_CLOSE_REQUEST));
				});
			}
		} };
	}

}
