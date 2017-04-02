/**
 * Kevin Peng
 * Period 2
 * April 2, 2017
 * Took 3 hours
 * 
 * Creates a new BallWorld so that I can actually test my code.
 */
package p2_Peng_Kevin_Demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BallWorldApp extends Application {
	
	public static void main(String[] args){
		launch();
	}
	@Override
	public void start(Stage stage) throws Exception {
		BallWorld w = new BallWorld();
		Scene scene = new Scene(w);
		w.setPrefWidth(500);
		w.setPrefHeight(500);
		stage.setScene(scene);
		stage.show();
		w.start();
	}

}
