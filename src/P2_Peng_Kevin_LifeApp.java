import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * March 12, 2017
 * took 1 hour 30 minutes
 * 
 * One problem I ran into was that I loaded a new grid by creating a new model and gridpane. This meant that my listeners were removed. I solved this
 * by re adding the listeners. I also had the problem that the scrollpane scrollbars took up space so the model didn't completely fill the screen but
 * I didn't fix this. I also had to do some research to figure out how to detect doubleclicks.
 */

public class P2_Peng_Kevin_LifeApp extends Application implements GenerationListener{
	
	private MenuItem loadMenu;
	private MenuItem saveMenu;
	private Button clearBtn;
	private Button nextGenerationBtn;
	private ToggleButton runToggle;
	private Label generationL;
	private Slider sizeSlider;
	private Slider speedSlider;
	private ScrollPane scrollPane;
	private BooleanGridPane gridPane;
	private P2_Peng_Kevin_LifeModel model;
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		AnimationTimer runLife = new AnimationTimer(){
			long lastTick = 0;
			@Override
			public void handle(long now) {
				if(now - lastTick >= speedSlider.getValue()*1000000){
					lastTick = now;
					model.nextGeneration();
				}
			}
		};
		
		stage.setTitle("Grid Viewer");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		gridPane = new BooleanGridPane();
		gridPane.setOnMousePressed(new MouseHandler());
		gridPane.setOnMouseDragged(new MouseHandler());
		model = new P2_Peng_Kevin_LifeModel(initializeArray(50, 50));
		model.addGenerationListener(this);
		gridPane.setModel(model);
		scrollPane = new ScrollPane(gridPane);
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
		
		VBox botBox = new VBox();
		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(5, 10, 0, 10));
		buttonBox.setSpacing(10);

		generationL = new Label("Generation: 0");
		
		clearBtn = new Button("Clear");
		clearBtn.setOnAction(new ButtonHandler());
		
		nextGenerationBtn = new Button("Next Generation");
		nextGenerationBtn.setOnAction(new ButtonHandler());
		
		runToggle = new ToggleButton("Run");
		runToggle.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldVal, Boolean newVal) {
				if(newVal){
					runLife.start();
				}else{
					runLife.stop();
				}
			}
		});
		
		buttonBox.getChildren().addAll(generationL, clearBtn, nextGenerationBtn, runToggle);
				
		HBox sliderBox = new HBox();
		sliderBox.setPadding(new Insets(5, 10, 0, 10));
		sliderBox.setSpacing(10);
		sizeSlider = new Slider(0, 100, 50);
		sizeSlider.setValue(gridPane.getTileSize());
		sizeSlider.setShowTickLabels(true);
		sizeSlider.valueProperty().addListener(new SliderHandler());
		
		speedSlider = new Slider(0, 1000, 0);
		speedSlider.setShowTickLabels(true);
		sliderBox.getChildren().addAll(new Label("Size"), sizeSlider, new Label("Speed"), speedSlider);
		
		botBox.getChildren().addAll(buttonBox, sliderBox);
		root.setBottom(botBox);
		
		stage.widthProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> obervable, Number oldVal, Number newVal) {
				sizeGridPaneToScene();
			}
		});
		stage.heightProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> obervable, Number oldVal, Number newVal) {
				sizeGridPaneToScene();
			}
		});
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
	
	private void loadGrid(Boolean[][] grid){
		model = new P2_Peng_Kevin_LifeModel(grid);
		model.addGenerationListener(this);
		gridPane.setModel(model);
		generationL.setText("Generation: 0");
		sizeGridPaneToScene();
	}
	/**
	 * Changes the tileSize so the model fills all available space
	 */
	private void sizeGridPaneToScene(){
		double width = scrollPane.getWidth();
		double height = scrollPane.getHeight();
		double tileWidth = width/model.getNumCols();
		double tileHeight = height/model.getNumRows();
		gridPane.setTileSize(Math.min(tileWidth, tileHeight));
		sizeSlider.setValue(gridPane.getTileSize());
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
					loadGrid(readFile(file));
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
			if(row >= 0 && row < model.getNumRows() && col >= 0 && col < model.getNumCols()){
				if(e.isPrimaryButtonDown()){
					if(e.getClickCount() == 1){
						model.setValueAt(row, col, true);
					}else if(e.getClickCount() == 2){
						erase(row, col);
					}
				}else if(e.isSecondaryButtonDown()){
					model.setValueAt(row, col, false);
				}
			}
		}
		
	}
	
	/**
	 * recursively deletes the block of live cells connected by an edge
	 * @param r the row to start at
	 * @param c the column to start at
	 */
	private void erase(int r, int c){
		if(r >= 0 && r < model.getNumRows() && c >= 0 && c < model.getNumCols() && model.getValueAt(r, c)){
			model.setValueAt(r, c, false);
			erase(r - 1, c);
			erase(r + 1, c);
			erase(r, c - 1);
			erase(r, c + 1);
		}
	}

}
