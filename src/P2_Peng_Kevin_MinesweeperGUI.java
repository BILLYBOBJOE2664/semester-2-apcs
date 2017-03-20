import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Kevin Peng
 * Period 2
 * Mar 18, 2017
 *
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
		
		Pane pane = new Pane();
		pane.setPrefSize(300, 300);
		root.setCenter(pane);
		
		stage.show();
	}
	
	
}
