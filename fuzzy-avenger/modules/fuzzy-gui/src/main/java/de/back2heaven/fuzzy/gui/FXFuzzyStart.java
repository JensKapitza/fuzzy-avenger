package de.back2heaven.fuzzy.gui;

import java.nio.file.Paths;

import de.back2heaven.fuzzy.gui.filebrowser.PathListCell;
import de.back2heaven.fuzzy.gui.filebrowser.SimpleFileBrowser;
import de.back2heaven.fuzzy.gui.menu.MyMenu;
import de.back2heaven.fuzzy.gui.preview.Preview;
import de.back2heaven.fuzzy.gui.targetview.PageItem;
import de.back2heaven.fuzzy.gui.targetview.TargetView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXFuzzyStart extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setTop(new MyMenu());
		VBox box = new VBox();
		Preview p = new Preview();
		box.getChildren().add(p);
		root.setCenter(box);

		root.setLeft(new SimpleFileBrowser(Paths.get("/home/jens"),p));
		TargetView tv = new TargetView();
		tv.getItems()
				.addAll(new PageItem("demo", PathListCell.class
						.getResource("/icons/framework/res/folder_32.png")),
						new PageItem("demo2", null),
						new PageItem("demo3", null));
		root.setRight(tv);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
