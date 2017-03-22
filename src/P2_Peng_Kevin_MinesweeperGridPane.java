import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
	private ImageView[][] nodes;
	
	
	public P2_Peng_Kevin_MinesweeperGridPane(int numRows, int numCols){
		makeGrid(numRows, numCols);
	}
	
	public void makeGrid(int numRows, int numCols){
		getChildren().clear();
		nodes = new ImageView[numRows][numCols];
		for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numCols; c++){
				nodes[r][c] = new ImageView();
				add(nodes[r][c], c, r);
			}
		}
	}
	
	public void setImageRowCol(int row, int col, Image image){
		getNodeFromRowCol(row, col).setImage(image);
	}
	
	public void setImageXY(int x, int y, Image image){
		getNodeFromXY(x, y).setImage(image);
	}
	
	public ImageView getNodeFromRowCol(int row, int col){
		return nodes[row][col];
	}
	
	public ImageView getNodeFromXY(double x, double y){
		for(Node node : getChildren()){
			if(node.getBoundsInParent().contains(x, y)){
				return (ImageView) node;
			}
		}
		return null;
	}

}
