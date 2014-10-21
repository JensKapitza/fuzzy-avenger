package de.back2heaven.fuzzy.gui;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import de.back2heaven.fuzzy.gui.filebrowser.SimpleFileBrowser;
import de.back2heaven.fuzzy.gui.menu.MyMenu;
import de.back2heaven.fuzzy.gui.preview.Preview;
import de.back2heaven.fuzzy.gui.targetview.TargetToolbar;
import de.back2heaven.fuzzy.gui.targetview.TargetView;

public class FXFuzzyStart extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setTop(new MyMenu());
		Preview p = new Preview();
		root.setCenter(p);

		root.setLeft(new SimpleFileBrowser(Paths.get("/home/jens")));
		TargetView tv = new TargetView();
		VBox box = new VBox(tv, new TargetToolbar());
		root.setRight(box);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
