import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
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
 * March 7, 2017
 * 
 */

public class P2_Peng_Kevin_LifeGUI_1 extends Application implements GenerationListener{
	
	private MenuItem loadMenu;
	private MenuItem saveMenu;
	private Button clearBtn;
	private Button nextGenerationBtn;
	private Label generationL;
	private Slider sizeSlider;
	private BooleanGridPane gridPane;
	private P2_Peng_Kevin_LifeModel model;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		gridPane = new BooleanGridPane();
		gridPane.setOnMousePressed(new MouseHandler());
		gridPane.setOnMouseDragged(new MouseHandler());
		model = new P2_Peng_Kevin_LifeModel(initializeArray(50, 50));
		model.addGenerationListener(this);
		gridPane.setModel(model);
		ScrollPane scrollPane = new ScrollPane(gridPane);
		
		stage.setTitle("Grid Viewer");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		
		root.setCenter(scrollPane);
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		loadMenu = new MenuItem("Load");
		loadMenu.setOnAction(new ButtonHandler());
		saveMenu = new MenuItem("Save");
		saveMenu.setOnAction(new ButtonHandler());
		menu.getItems().addAll(loadMenu, saveMenu);
		menuBar.getMenus().add(menu);
		root.setTop(menuBar);
		
		HBox botBox = new HBox();
		botBox.setPadding(new Insets(10, 10, 0, 10));
		botBox.setSpacing(10);
		generationL = new Label("Generation: 0");
		clearBtn = new Button("Clear");
		clearBtn.setOnAction(new ButtonHandler());
		nextGenerationBtn = new Button("Next Generation");
		nextGenerationBtn.setOnAction(new ButtonHandler());
		sizeSlider = new Slider(0, 100, 50);
		sizeSlider.setValue(gridPane.getTileSize());
		sizeSlider.setShowTickLabels(true);
		sizeSlider.setShowTickMarks(true);
		sizeSlider.valueProperty().addListener(new SliderHandler());
		botBox.getChildren().addAll(generationL, clearBtn, nextGenerationBtn, sizeSlider);
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
	
	private void saveModel(File file){
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(model.getNumRows() + " " + model.getNumCols() + "\r\n");
			for(int r = 0; r < model.getNumRows(); r++){
				for(int c = 0; c < model.getNumCols(); c++){
					fw.write(model.getValueAt(r, c) ? "X" : "O");
				}
				fw.write("\r\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void GenerationChanged(int oldVal, int newVal){
		generationL.setText("Generation: " + newVal);
	}
	
	/**
	 * returns a Boolean[][] with all values set to false
	 */
	private Boolean[][] initializeArray(int numRows, int numCols){
		Boolean[][] arr = new Boolean[numRows][numCols];
		for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numCols; c++){
				arr[r][c] = false;
			}
		}
		return arr;
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			Object src = e.getSource();
			if(src == loadMenu){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose a valid text file");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
				File file = fileChooser.showOpenDialog(new Stage());
				if(file != null){
					model = new P2_Peng_Kevin_LifeModel(readFile(file));
					gridPane.setModel(model);
					gridPane.setTileSize(sizeSlider.getValue());
					model.setGeneration(0);
				}
			}else if(src == saveMenu){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose where you want to save you grid");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
				File file = fileChooser.showSaveDialog(new Stage());
				if(file != null){
					saveModel(file);
				}
			}else if(src == clearBtn){
				for(int r = 0; r < model.getNumRows(); r++){
					for(int c = 0; c < model.getNumCols(); c++){
						model.setValueAt(r, c, false);
					}
				}
				model.setGeneration(0);
			}else if(src == nextGenerationBtn){
				model.nextGeneration();
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
			if(e.isPrimaryButtonDown()){
				model.setValueAt(row, col, true);
			}else if(e.isSecondaryButtonDown()){
				model.setValueAt(row, col, false);
			}
		}
		
	}

}
