import java.io.File;
import java.net.MalformedURLException;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * Mar 18, 2017
 * Spent 50 minutes
 * 
 * The problem I'm facing is that I can't detect which node got clicked. Everything else is fine I think.
 * 
 * HW 5
 * Mar 26, 2017
 * Spent 1 hour 30 minutes
 * The main problem was that I had to do research on how to use classes like input dialog and webview. I didn't realize that input dialog had a waitAndShow()
 * method at first so I tried to make a really convoluted system to try and build it myself. I also had to change the way the incorrect bomb was displayed to function
 * with quick-reveal. Originally, the last revealed location was passed in as a parameter but I changed it so that it detects if a cell is a mine and revealed
 * Everything else was pretty straightforward.
 *
 */


public class P2_Peng_Kevin_MinesweeperApp extends Application implements P2_Peng_Kevin_MSModelListener{
	
	private Stage stage;
	private BorderPane root;
	private boolean isRunning;
	private int width;
	private int height;
	private int numMines;
	
	private Label minesLeft;
	private Label time;
	
	private AnimationTimer timer;
	private long startTime; //when the timer started ticking
	
	private P2_Peng_Kevin_MinesweeperModel grid;
	private P2_Peng_Kevin_MinesweeperGridPane gridPane;
	private ImageView face;
	
	private Image blank;
	private Image bombDeath;
	private Image bombFlagged;
	private Image bombRevealed;
	private Image bombWrong;
	private Image faceDead;
	private Image faceOoh;
	private Image faceSmile;
	private Image faceWin;
	private Image num0;
	private Image num1;
	private Image num2;
	private Image num3;
	private Image num4;
	private Image num5;
	private Image num6;
	private Image num7;
	private Image num8;
	
	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage s) throws Exception {
		stage = s;
		loadImages();
		
		width = 8;
		height = 8;
		numMines = 10;
		isRunning = false;
		
		grid = new P2_Peng_Kevin_MinesweeperModel(width, height, numMines);
		grid.addListeners(this);
		

		makeGUI();
		
		resetTimer();
		timer = new AnimationTimer(){

			public void handle(long now) {
				if(startTime == -1){
					startTime = now;
				}
				time.setText("Time: " + (now - startTime)/1000000000);
			}
			
		};
		
		gridPane = new P2_Peng_Kevin_MinesweeperGridPane();
		updateAll();
		gridPane.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				if(isRunning){
					face.setImage(faceOoh);
				}
			}
			
		});
		gridPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent e) {
				if(isRunning){
					timer.start();
					
					face.setImage(faceSmile);
					ImageView node = gridPane.getNodeFromXY(e.getX(), e.getY());
					if(node != null){
						int row = GridPane.getRowIndex(node);
						int col = GridPane.getColumnIndex(node);
						if(e.getButton() == MouseButton.PRIMARY && !grid.isFlagged(row, col)){
							if(!grid.isRevealed(row, col)){
								grid.reveal(row, col);
							}else{
								grid.quickReveal(row, col);
							}
						}else if(e.getButton() == MouseButton.SECONDARY && !grid.isRevealed(row, col)){
							grid.setFlagged(row, col, !grid.isFlagged(row, col));
						}
						if(grid.hasWon()){
							displayWon();
							isRunning = false;
						}else if(grid.hasLost()){
							displayLost();
							isRunning = false;
							
						}
					}
				}
			}
			
		});
		gridPane.maxWidthProperty().set(gridPane.prefWidth(-1));
		VBox vbox = new VBox(face, gridPane);
		gridPane.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		root.setCenter(vbox);
		
		play(width, height, numMines);
		stage.show();
	}
	
	private void play(int width, int height, int numMines){
		timer.stop();
		face.setImage(faceSmile);
		grid.createGrid(width, height, numMines);
		isRunning = true;
	}
	
	/**
	 * Displays all unrevealed mines, shows all incorrect flags, and sets the color of the user revealed mine to bombDeath
	 */
	private void displayLost(){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				if(grid.isMine(r, c) && grid.isRevealed(r, c)){
					gridPane.setImageRowCol(r, c, bombDeath);
				}else if(grid.isMine(r, c) && !grid.isRevealed(r, c) && !grid.isFlagged(r, c)){
					gridPane.setImageRowCol(r, c, bombRevealed);
				}else if(!grid.isMine(r, c) && grid.isFlagged(r, c)){
					gridPane.setImageRowCol(r, c, bombWrong);
				}
			}
		}
		face.setImage(faceDead);
		timer.stop();
	}
	
	/**
	 * Flag all unflagged mines
	 */
	private void displayWon(){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				if(grid.isMine(r, c) && !grid.isFlagged(r, c)){
					grid.setFlagged(r, c, true);
				}
			}
		}
		face.setImage(faceWin);
		timer.stop();
	}
	
	private void resetTimer(){
		startTime = -1;
		time.setText("Time: 0");
	}
	
	private void updateAll(){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				updateNode(r, c);
			}
		}
	}
	
	private void updateNode(int row, int col){
		if(grid.isMine(row, col) && grid.isRevealed(row, col)){
			gridPane.setImageRowCol(row, col, bombDeath);
		}else if(grid.isFlagged(row, col)){
			gridPane.setImageRowCol(row, col, bombFlagged);
		}else if(!grid.isRevealed(row, col)){
			gridPane.setImageRowCol(row, col, blank);
		}else if(grid.isMine(row, col)){
			gridPane.setImageRowCol(row, col, bombRevealed);
		}else if(grid.getNumNeighborMines(row, col) == 0){
			gridPane.setImageRowCol(row, col, num0);
		}else if(grid.getNumNeighborMines(row, col) == 1){
			gridPane.setImageRowCol(row, col, num1);
		}else if(grid.getNumNeighborMines(row, col) == 2){
			gridPane.setImageRowCol(row, col, num2);
		}else if(grid.getNumNeighborMines(row, col) == 3){
			gridPane.setImageRowCol(row, col, num3);
		}else if(grid.getNumNeighborMines(row, col) == 4){
			gridPane.setImageRowCol(row, col, num4);
		}else if(grid.getNumNeighborMines(row, col) == 5){
			gridPane.setImageRowCol(row, col, num5);
		}else if(grid.getNumNeighborMines(row, col) == 6){
			gridPane.setImageRowCol(row, col, num6);
		}else if(grid.getNumNeighborMines(row, col) == 7){
			gridPane.setImageRowCol(row, col, num7);
		}else if(grid.getNumNeighborMines(row, col) == 8){
			gridPane.setImageRowCol(row, col, num8);
		}
	}
	
	private void makeGUI(){
		stage.setTitle("Minesweeper");
		root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		minesLeft = new Label("Mines Left: " + grid.getNumMinesLeft());
		time = new Label("Time: 0");
		HBox labelBox = new HBox(minesLeft, time);
		labelBox.setSpacing(10);
		labelBox.setPadding(new Insets(5));
		
		MenuBar menubar = new MenuBar();
		
		Menu gameMenu = new Menu("Game");
		MenuItem newGame = new MenuItem("New Game");
		newGame.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				play(width, height, numMines);
			}
			
		});
		MenuItem beginnerGame = new MenuItem("New Beginner Game");
		beginnerGame.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				width = 8;
				height = 8;
				numMines = 10;
				play(width, height, numMines);
			}
			
		});
		MenuItem intermediateGame = new MenuItem("New Intermediate Game");
		intermediateGame.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				width = 16;
				height = 16;
				numMines = 40;
				play(width, height, numMines);
			}
			
		});
		MenuItem expertGame = new MenuItem("New Expert Game");
		expertGame.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				width = 31;
				height = 16;
				numMines = 99;
				play(width, height, numMines);
			}
			
		});
		MenuItem customGame = new MenuItem("New Custom Game");
		customGame.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				int newWidth = getValueFromUser("Set Width", "Width");
				int newHeight = getValueFromUser("Set Height", "Height");
				int newNumMines = getValueFromUser("Set Number of Mines", "Number of mines");
				if(newNumMines < 0 || newNumMines > newWidth*newHeight){
					Alert alert = new Alert(AlertType.ERROR, "Invalid Parameter", ButtonType.OK);
					alert.showAndWait();
				}else{
					width = newWidth;
					height = newHeight;
					numMines = newNumMines;
					play(width, height, numMines);
				}
			}
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				Platform.exit();
			}
		});
		gameMenu.getItems().addAll(newGame, beginnerGame, intermediateGame, expertGame, customGame, exit);
		
		Menu optionMenu = new Menu("Options");
		MenuItem setMines = new MenuItem("Set Number of Mines");
		setMines.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				int newNumMines = getValueFromUser("Set Number of Mines", "Number of Mines");
				if(newNumMines < 0 || newNumMines > width*height){
					Alert alert = new Alert(AlertType.ERROR, "Invalid Parameter", ButtonType.OK);
					alert.showAndWait();
				}else{
					numMines = newNumMines;
					play(width, height, numMines);
				}
			}
			
		});
		MenuItem gridSize = new MenuItem("Set Grid Size");
		gridSize.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				int newWidth = getValueFromUser("Set Width","Width");
				int newHeight = getValueFromUser("Set Height", "Height");
				if(newWidth < 0 || newHeight < 0){
					Alert alert = new Alert(AlertType.ERROR, "Invalid Parameter", ButtonType.OK);
					alert.showAndWait();
				}else{
					width = newWidth;
					height = newHeight;
					play(width, height, numMines);
				}
			}
		});
		
		optionMenu.getItems().addAll(setMines, gridSize);
		
		Menu helpMenu = new Menu("Help");
		MenuItem instructions = new MenuItem("How To Play");
		instructions.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				Stage stage = new Stage();
				stage.setTitle("How To Play");
				WebView webView = new WebView();
				ScrollPane scrollPane = new ScrollPane(webView);
				scrollPane.setFitToHeight(true);
				scrollPane.setFitToWidth(true);
				webView.getEngine().load("file:///" + new File("minesweeper/P2_Peng_Kevin_MinesweeperInstructions.html").getAbsolutePath());
				stage.setScene(new Scene(scrollPane));
				stage.show();
			}
		});
		MenuItem about = new MenuItem("About");
		about.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent e){
				Stage stage = new Stage();
				stage.setTitle("About");
				WebView webView = new WebView();
				ScrollPane scrollPane = new ScrollPane(webView);
				scrollPane.setFitToHeight(true);
				scrollPane.setFitToWidth(true);
				webView.getEngine().load("file:///" + new File("minesweeper/P2_Peng_Kevin_MinesweeperAbout.html").getAbsolutePath());
				stage.setScene(new Scene(scrollPane));
				stage.show();
			}
			
		});
		helpMenu.getItems().addAll(instructions, about);
		
		menubar.getMenus().addAll(gameMenu, optionMenu, helpMenu);
		
		VBox topBox = new VBox(menubar, labelBox);
		root.setTop(topBox);
		
		face = new ImageView(faceSmile);
		face.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				play(width, height, numMines);
			}
			
		});
	}
	
	private int getValueFromUser(String title, String prompt){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(null);
		dialog.setContentText(prompt);
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			try{
				return Integer.parseInt(result.get());
			}catch(NumberFormatException e){
				return -1;
			}
		}
		return -1;
		
	}
	
	
	private void loadImages(){
		blank = new Image("file:minesweeper/images/blank.gif");
		bombDeath = new Image("file:minesweeper/images/bomb_death.gif");
		bombFlagged = new Image("file:minesweeper/images/bomb_flagged.gif");
		bombRevealed = new Image("file:minesweeper/images/bomb_revealed.gif");
		bombWrong = new Image("file:minesweeper/images/bomb_wrong.gif");
		faceDead = new Image("file:minesweeper/images/face_dead.gif");
		faceOoh = new Image("file:minesweeper/images/face_ooh.gif");
		faceSmile = new Image("file:minesweeper/images/face_smile.gif");
		faceWin = new Image("file:minesweeper/images/face_win.gif");
		num0 = new Image("file:minesweeper/images/num_0.gif");
		num1 = new Image("file:minesweeper/images/num_1.gif");
		num2 = new Image("file:minesweeper/images/num_2.gif");
		num3 = new Image("file:minesweeper/images/num_3.gif");
		num4 = new Image("file:minesweeper/images/num_4.gif");
		num5 = new Image("file:minesweeper/images/num_5.gif");
		num6 = new Image("file:minesweeper/images/num_6.gif");
		num7 = new Image("file:minesweeper/images/num_7.gif");
		num8 = new Image("file:minesweeper/images/num_8.gif");
	}
	

	@Override
	public void cellChanged(int row, int col) {
		updateNode(row, col);
		minesLeft.setText("Mines Left: " + grid.getNumMinesLeft());
	}

	@Override
	public void modelChanged() {
		minesLeft.setText("Mines Left: " + grid.getNumMinesLeft());
		resetTimer();
		gridPane.clear();
		updateAll();
		stage.sizeToScene();
	}
	
	
}
