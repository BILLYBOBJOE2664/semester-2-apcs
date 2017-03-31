package p2_Peng_Kevin_GraphicsEngine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		P2_Peng_Kevin_BallWorld w = new P2_Peng_Kevin_BallWorld();
		stage.setScene(new Scene(w));
		stage.show();
		w.start();
	}

}
