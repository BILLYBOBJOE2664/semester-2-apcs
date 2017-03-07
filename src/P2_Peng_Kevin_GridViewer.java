import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * March 6, 2017
 * took 50 minutes
 * 
 * The hardest parts were figuring out how to use the fileChooser and MouseEvent. I used the api to figure it out.
 * I also had trouble with the fact that you couldn't access the model through the grid pane so I made a new variable to store it.
 * I also made some typos that made some really annoying bug. For example, I typed row instead of r in the mouse event giving me the wrong result.
 */

public class P2_Peng_Kevin_GridViewer extends Application{
	
	private Button loadBtn;
	private Button clearBtn;
	private Slider sizeSlider;
	private BooleanGridPane gridPane;
	private GridModel<Boolean> model;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		gridPane = new BooleanGridPane();
		gridPane.setOnMouseClicked(new MouseHandler());
		
		stage.setTitle("Grid Viewer");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setWidth(500);
		stage.setHeight(500);
		
		
		root.setCenter(gridPane);
		
		HBox botBox = new HBox();
		botBox.setPadding(new Insets(10, 10, 0, 10));
		botBox.setSpacing(10);
		loadBtn = new Button("Load");
		loadBtn.setOnAction(new ButtonHandler());
		clearBtn = new Button("Clear");
		clearBtn.setOnAction(new ButtonHandler());
		sizeSlider = new Slider(0, 100, 50);
		sizeSlider.setShowTickLabels(true);
		sizeSlider.setShowTickMarks(true);
		sizeSlider.valueProperty().addListener(new SliderHandler());
		botBox.getChildren().addAll(loadBtn, clearBtn, sizeSlider);
		root.setBottom(botBox);
		
		stage.show();
		
		
	}
	
	private Boolean[][] readFile(File file){
		Scanner fs;
		try {
			fs = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		int numRows = fs.nextInt();
		int numCols = fs.nextInt();
		fs.nextLine();
		Boolean[][] grid = new Boolean[numRows][numCols];
		for(int r = 0; r < numRows; r++){
			String row = fs.nextLine();
			for(int c = 0; c < numCols; c++){
				grid[r][c] = row.charAt(c) == 'X' ? true : (row.charAt(c) == 'O' ? false : null);
			}
		}
		fs.close();
		return grid;
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			Object src = e.getSource();
			if(src == loadBtn){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose a valid text file");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
				File file = fileChooser.showOpenDialog(new Stage());
				if(file != null){
					model = new GridModel<Boolean>(readFile(file));
					gridPane.setModel(model);
					gridPane.setTileSize(sizeSlider.getValue());
				}
			}else if(src == clearBtn){
				for(int r = 0; r < model.getNumRows(); r++){
					for(int c = 0; c < model.getNumCols(); c++){
						model.setValueAt(r, c, false);
					}
				}
			}
		}
	}
	
	private class SliderHandler implements ChangeListener<Number>{

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
			gridPane.setTileSize((Double)newVal);
		}
		
	}
	
	private class MouseHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent e) {
			int row = gridPane.rowForYPos(e.getY());
			int col = gridPane.colForXPos(e.getX());
			for(int r = Math.max(row - 1, 0); r <= Math.min(row + 1, model.getNumRows() - 1); r++){
				for(int c = Math.max(col - 1, 0); c <= Math.min(col + 1, model.getNumCols() - 1); c++){
					if(!(r == row && c == col)){
						model.setValueAt(r, c, !model.getValueAt(r, c));
					}
				}
			}
		}
		
	}

}
