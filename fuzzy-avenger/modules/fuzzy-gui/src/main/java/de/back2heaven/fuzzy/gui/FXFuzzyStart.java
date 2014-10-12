package de.back2heaven.fuzzy.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class FXFuzzyStart extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(400);
		primaryStage.setHeight(400);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
