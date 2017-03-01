import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * Feb 28, 2017
 * took 20 minutes
 * 
 * I spent some time researching how to do anything before starting. I didn't really understand is insets overalpped at first but they don't. I couldn't find the right node type to take up space
 * in the group initially.
 */

public class P2_Peng_Kevin_JavaFXGUI extends Application {

	public void start(Stage stage) throws Exception {
		stage.setTitle("GUI");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20, 20, 0, 20));
		hBox.setSpacing(10);
		Label label = new Label("Hello");
		Button button = new Button("Button");
		CheckBox checkbox = new CheckBox("I have reaad and agree to the terms");
		hBox.getChildren().addAll(label, button, checkbox);
		root.setTop(hBox);
		
		Slider slider = new Slider(0, 100, 50);
		slider.setPadding(new Insets(10));
		root.setBottom(slider);
		
		Group group = new Group();
		Region region = new Region();
		region.setMinSize(100, 100);
		group.getChildren().add(region);
		root.setCenter(group);
		
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
