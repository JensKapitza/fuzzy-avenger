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
		root.setRight(new TargetView());
		Scene scene = new  Scene(root );
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
