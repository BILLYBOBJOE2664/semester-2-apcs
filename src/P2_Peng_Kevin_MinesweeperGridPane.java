import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	public P2_Peng_Kevin_MinesweeperGridPane(P2_Peng_Kevin_MinesweeperModel model){
		this.model = model;
		loadImages();
		resetCells();
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
	
	public void resetCells(){
		for(int r = 0; r < model.getNumRows(); r++){
			for(int c = 0; c < model.getNumCols(); c++){
				if(model.isFlagged(r, c)){
					add(new ImageView(bombFlagged), c, r);
				}else if(!model.isRevealed(r, c)){
					add(new ImageView(blank),c, r);
				}else if(model.isMine(r, c)){
					add(new ImageView(bombRevealed), c, r);
				}else if(model.getNumNeighborMines(r, c) == 0){
					add(new ImageView(num0), c, r);
				}else if(model.getNumNeighborMines(r, c) == 1){
					add(new ImageView(num1), c, r);
				}else if(model.getNumNeighborMines(r, c) == 2){
					add(new ImageView(num2), c, r);
				}else if(model.getNumNeighborMines(r, c) == 3){
					add(new ImageView(num3), c, r);
				}else if(model.getNumNeighborMines(r, c) == 4){
					add(new ImageView(num4), c, r);
				}else if(model.getNumNeighborMines(r, c) == 5){
					add(new ImageView(num5), c, r);
				}else if(model.getNumNeighborMines(r, c) == 6){
					add(new ImageView(num6), c, r);
				}else if(model.getNumNeighborMines(r, c) == 7){
					add(new ImageView(num7), c, r);
				}else if(model.getNumNeighborMines(r, c) == 8){
					add(new ImageView(num8), c, r);
				}
			}
		}
	}
}
