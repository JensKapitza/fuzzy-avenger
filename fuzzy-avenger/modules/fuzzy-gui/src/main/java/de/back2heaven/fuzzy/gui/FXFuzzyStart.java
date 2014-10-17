package de.back2heaven.fuzzy.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXFuzzyStart extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setTop(new MyMenu());
		root.setLeft(new SimpleFileBrowser());
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
