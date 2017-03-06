import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * March 6, 2017
 * 
 */

public class P2_Peng_Kevin_GridViewer extends Application{
	
	Button loadBtn;
	Button clearBtn;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Grid Viewer");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		HBox botBox = new HBox();
		botBox.setPadding(new Insets(10, 10, 0, 10));
		botBox.setSpacing(10);
		loadBtn = new Button("Load");
		clearBtn = new Button("Clear");
		Slider sizeSlider = new Slider(0, 100, 50);
		sizeSlider.setShowTickLabels(true);
		sizeSlider.setShowTickMarks(true);
		botBox.getChildren().addAll(loadBtn, clearBtn, sizeSlider);
		root.setBottom(botBox);
		
		stage.show();
		
		
	}
	
	private boolean[][] readFile(String fileName){
		return null;
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			Object src = e.getSource();
			if(src == loadBtn){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose a valid text file");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
			}
		}
		
	}

}
