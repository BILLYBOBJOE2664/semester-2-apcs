import java.util.Random;

/**
 * Kevin Peng
 * Period 2
 * Mar 16, 2017
 *
 * I didn't have to deal with bugs but the way I organized the model and interface made the whole thing awkward to code and redundant. If we jut had a reveal()
 * method instead of setReveal(), things would've been simpler. In the end, it still works so it doesn't really matter. The code to check all eight squares
 * around a given cell was also really tedious to writer.
 */

public class P2_Peng_Kevin_MinesweeperModel implements P2_Peng_Kevin_MSModel {
	private Cell[][] grid;
	private int numMines;
	private boolean isFirstReveal;
	private int numFlags;
	
	public P2_Peng_Kevin_MinesweeperModel(int width, int height, int numMines){
		createGrid(width, height, numMines);
	}
	
	public void createGrid(int width, int height, int numMines) {
		grid = new Cell[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				grid[r][c] = new Cell(false);
			}
		}
		this.numMines = numMines;
		isFirstReveal = true;
		numFlags = 0;
	}

	private void generateMines(int numMines) {
		Random rand = new Random();
		for(int i = 0; i < numMines; i++){
			while(true){
				int r = rand.nextInt(grid.length);
				int c = rand.nextInt(grid[0].length);
				if(!grid[r][c].isMine() && !grid[r][c].isRevealed()){
					grid[r][c].setIsMine(true);
					break;
				}
			}
		}
	}

	public int getNumNeighborMines(int row, int col){
		int sum = 0;
		//top row
		if(row > 0){
			//left
			if(col > 0 && grid[row - 1][col - 1].isMine()) sum++;
			//center
			if(grid[row - 1][col].isMine()) sum++;
			//right
			if(col < grid[0].length - 1 && grid[row - 1][col + 1].isMine()) sum++;
		}
		//center row
		//left
		if(col > 0 && grid[row][col - 1].isMine()) sum++;
		//right
		if(col < grid[0].length - 1 && grid[row][col + 1].isMine()) sum++;
		//bottom row
		if(row < grid.length - 1){
			//left
			if(col > 0 && grid[row + 1][col - 1].isMine()) sum++;
			//center
			if(grid[row + 1][col].isMine()) sum++;
			//right
			if(col < grid[0].length - 1 && grid[row + 1][col + 1].isMine()) sum++;
		}
		return sum;
	}

	public boolean isMine(int row, int col) {
		return grid[row][col].isMine();
	}

	public boolean isRevealed(int row, int col) {
		return grid[row][col].isRevealed();
	}

	public void setRevealed(int row, int col, boolean isRevealed) {
		if(isRevealed && !grid[row][col].isRevealed()){
			grid[row][col].setRevealed(isRevealed);
			if(isFirstReveal){
				generateMines(numMines);
				isFirstReveal = false;
			}
			//recursively reveal
			if(!isMine(row, col) && getNumNeighborMines(row, col) == 0){
				//top row
				if(row > 0){
					//left
					if(col > 0) setRevealed(row - 1, col - 1, true);
					//center
					setRevealed(row - 1, col, true);
					//right
					if(col < grid[0].length - 1) setRevealed(row - 1, col + 1, true);
				}
				//center row
				//left
				if(col > 0) setRevealed(row, col - 1, true);
				//right
				if(col < grid[0].length - 1) setRevealed(row, col + 1, true);
				//bottom row
				if(row < grid.length - 1){
					//left
					if(col > 0) setRevealed(row + 1, col - 1, true);
					//center
					setRevealed(row + 1, col, true);
					//right
					if(col < grid[0].length - 1) setRevealed(row + 1, col + 1, true);
				}
			}
		}else{
			grid[row][col].setRevealed(isRevealed);
		}
	}

	public boolean isFlagged(int row, int col) {
		return grid[row][col].isFlagged();
	}

	public void setFlagged(int row, int col, boolean isFlagged) {
		if(!grid[row][col].isFlagged() && isFlagged){
			numFlags++;
		}else if(grid[row][col].isFlagged() && !isFlagged){
			numFlags--;
		}
		grid[row][col].setFlagged(isFlagged);
	}

	public int getNumRows() {
		return grid.length;
	}

	public int getNumCols() {
		return grid[0].length;
	}

	public int getNumMinesLeft() {
		return numMines - numFlags;
	}

	public boolean hasWon() {
		if(numFlags == numMines){
			for(int r = 0; r < getNumRows(); r++){
				for(int c = 0; c < getNumCols(); c++){
					if(grid[r][c].isMine() && !grid[r][c].isFlagged()){
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean hasLost() {
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(grid[r][c].isMine() && grid[r][c].isRevealed()){
					return true;
				}
			}
		}
		return false;
	}
	
	private class Cell{
		private static final int DEFAULT = 0;
		private static final int REVEALED = 1;
		private static final int FLAGGED = 2;
		
		private int state;
		private boolean isMine;
		
		public Cell(boolean isMine){
			state = DEFAULT;
			this.isMine = isMine;
		}
		
		public boolean isMine() {
			return isMine;
		}
		
		public void setIsMine(boolean isMine){
			this.isMine = isMine;
		}

		public boolean isRevealed() {
			return state == REVEALED;
		}

		public void setRevealed(boolean isRevealed) {
			if(isRevealed){
				state = REVEALED;
			}else if(state == REVEALED){
				state = DEFAULT;
			}
		}

		public boolean isFlagged() {
			return state == FLAGGED;
		}

		public void setFlagged(boolean isFlagged) {
			if(isFlagged){
				state = FLAGGED;
			}else if(state == FLAGGED){
				state = DEFAULT;
			}
		}
	}

	
}
