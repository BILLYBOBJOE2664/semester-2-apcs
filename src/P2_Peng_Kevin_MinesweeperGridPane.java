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
	
	
	public P2_Peng_Kevin_MinesweeperGridPane(){
	}
	
	/**
	 * Sets the image at a row col location. If the row col does not contain a node, create it
	 * @param row The row index of the image to set
	 * @param col The column index of the image to set
	 * @param image The image the node will be set to
	 */
	public void setImageRowCol(int row, int col, Image image){
		ImageView node = getNodeFromRowCol(row, col);
		if(node == null){
			add(new ImageView(image), col, row);
		}else{
			node.setImage(image);
		}
	}
	
	/**
	 * Sets the image at the x y location. If the node there does not exist, do nothing
	 * @param x The x location of the node
	 * @param y The y location of the node
	 * @param image The image the node will be set to
	 */
	public void setImageXY(int x, int y, Image image){
		ImageView node = getNodeFromXY(x, y);
		if(node != null){
			node.setImage(image);
		}
		
	}
	
	public ImageView getNodeFromRowCol(int row, int col){
		for(Node node : getChildren()){
			if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col){
				return (ImageView) node;
			}
		}
		return null;
	}
	
	public ImageView getNodeFromXY(double x, double y){
		for(Node node : getChildren()){
			if(node.getBoundsInParent().contains(x, y)){
				return (ImageView) node;
			}
		}
		return null;
	}
	
	public void clear(){
		getChildren().clear();
	}

}
