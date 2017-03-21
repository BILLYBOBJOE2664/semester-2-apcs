import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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


public class P2_Peng_Kevin_MinesweeperGUI extends Application{
	
	Label minesLeft;
	Label timeLeft;
	
	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Minesweeper");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
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
		
		P2_Peng_Kevin_MinesweeperModel grid = new P2_Peng_Kevin_MinesweeperModel(10, 10, 10);
		P2_Peng_Kevin_MinesweeperGridPane gridPane = new P2_Peng_Kevin_MinesweeperGridPane(grid);
		grid.addListener(gridPane);
		gridPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
			private boolean isRunning = true;
			
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
							isRunning = false;
						}else if(grid.hasLost()){
							System.out.println("You Lose!");
							isRunning = false;
							
						}
					}
				}
			}
			
		});
		root.setCenter(gridPane);
		
		stage.show();
	}
	
	
}
