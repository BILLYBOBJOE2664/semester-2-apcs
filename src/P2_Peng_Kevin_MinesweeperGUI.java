import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * Mar 18, 2017
 * Spent 50 minutes
 * 
 * The problem I'm facing is that I can't detect which node got clicked. Everything else is fine I think.
 *
 */


public class P2_Peng_Kevin_MinesweeperGUI extends Application implements P2_Peng_Kevin_MSModelListener{
	
	private boolean isRunning;
	
	private Label minesLeft;
	private Label timeLeft;
	
	private P2_Peng_Kevin_MinesweeperModel grid;
	P2_Peng_Kevin_MinesweeperGridPane gridPane;
	
	private Image blank;
	private Image bombDeath;
	private Image bombFlagged;
	private Image bombRevealed;
	private Image bombWrong;
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
	public void start(Stage stage) throws Exception {
		loadImages();
		
		stage.setTitle("Minesweeper");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		makeGUI(root);
		
		
		grid = new P2_Peng_Kevin_MinesweeperModel(10, 10, 10);
		grid.addListeners(this);
		gridPane = new P2_Peng_Kevin_MinesweeperGridPane(grid.getNumRows(), grid.getNumCols());
		updateAll();
		isRunning = true;
		gridPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent e) {
				if(isRunning){
					ImageView node = gridPane.getNodeFromXY(e.getX(), e.getY());
					if(node != null){
						int row = GridPane.getRowIndex(node);
						int col = GridPane.getColumnIndex(node);
						if(e.getButton() == MouseButton.PRIMARY && !grid.isFlagged(row, col)){
							grid.reveal(row, col);
						}else if(e.getButton() == MouseButton.SECONDARY && !grid.isRevealed(row, col)){
							grid.setFlagged(row, col, !grid.isFlagged(row, col));
						}
						if(grid.hasWon()){
							System.out.println("You Win!");
							displayWon();
							isRunning = false;
						}else if(grid.hasLost()){
							System.out.println("You Lose!");
							displayLost(row, col);
							isRunning = false;
							
						}
					}
				}
			}
			
		});
		root.setCenter(gridPane);
		
		stage.show();
	}
	
	/**
	 * Displays all unrevealed mines, shows all incorrect flags, and sets the color of the user revealed mine to bombDeath
	 * @param row The row the of the mine that was revealed
	 * @param col The column of the mine that was revealed
	 */
	public void displayLost(int row, int col){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				if(grid.isMine(r, c) && !grid.isRevealed(r, c) && !grid.isFlagged(r, c)){
					gridPane.setImageRowCol(r, c, bombRevealed);
				}else if(!grid.isMine(r, c) && grid.isFlagged(r, c)){
					gridPane.setImageRowCol(r, c, bombWrong);
				}
			}
		}
		gridPane.setImageRowCol(row, col, bombDeath);
	}
	
	/**
	 * Flag all unflagged mines
	 */
	public void displayWon(){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				if(grid.isMine(r, c) && !grid.isFlagged(r, c)){
					grid.setFlagged(r, c, true);
				}
			}
		}
	}
	
	public void updateAll(){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				updateNode(r, c);
			}
		}
	}
	
	public void updateNode(int row, int col){
		if(grid.isFlagged(row, col)){
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
	
	private void makeGUI(BorderPane root){
		minesLeft = new Label("Mines Left: 0");
		timeLeft = new Label("Time Left: 0");
		HBox labelBox = new HBox(minesLeft, timeLeft);
		labelBox.setSpacing(10);
		labelBox.setPadding(new Insets(5));
		
		MenuBar menubar = new MenuBar();
		
		Menu gameMenu = new Menu("Game");
		MenuItem newGame = new MenuItem("New Game");
		MenuItem beginnerGame = new MenuItem("New Beginner Game");
		MenuItem intermediateGame = new MenuItem("New Intermediate Game");
		MenuItem expertGame = new MenuItem("New Expert Game");
		gameMenu.getItems().addAll(newGame, beginnerGame, intermediateGame, expertGame);
		
		Menu optionMenu = new Menu("Options");
		MenuItem setMines = new MenuItem("Set Number of Mines");
		MenuItem gridSize = new MenuItem("Set Grid Size");
		MenuItem exit = new MenuItem("Exit");
		optionMenu.getItems().addAll(setMines, gridSize, exit);
		
		Menu helpMenu = new Menu("Help");
		MenuItem instructions = new MenuItem("How To Play");
		MenuItem about = new MenuItem("About");
		helpMenu.getItems().addAll(instructions, about);
		
		menubar.getMenus().addAll(gameMenu, optionMenu, helpMenu);
		
		VBox topBox = new VBox(menubar, labelBox);
		root.setTop(topBox);
	}
	
	private void loadImages(){
		blank = new Image("file:minesweeper/images/blank.gif");
		bombDeath = new Image("file:minesweeper/images/bomb_death.gif");
		bombFlagged = new Image("file:minesweeper/images/bomb_flagged.gif");
		bombRevealed = new Image("file:minesweeper/images/bomb_revealed.gif");
		bombWrong = new Image("file:minesweeper/images/bomb_wrong.gif");
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
	}

	@Override
	public void modelChanged() {
		gridPane.makeGrid(grid.getNumRows(), grid.getNumCols());
	}
	
	
}
