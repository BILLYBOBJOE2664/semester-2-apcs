import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * Kevin Peng
 * Period 2
 * Mar 20, 2017
 * Took
 * 
 */

/**
 * @author kpeng356
 *
 */
public class P2_Peng_Kevin_MinesweeperGridPane extends GridPane{
	private P2_Peng_Kevin_MinesweeperModel model;
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
	
	private void loadImages(){
		blank = new Image("minesweeper/images/blank.gif");
		bombDeath = new Image("minesweeper/images/bomb_death.gif");
		bombFlagged = new Image("minesweeper/images/bomb_flagged.gif");
		bombRevealed = new Image("minesweeper/images/bomb_revealed.gif");
		bombWrong = new Image("minesweeper/images/bomb_wrong.gif");
		num0 = new Image("minesweeper/images/num_0.gif");
	}
}
