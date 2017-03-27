import java.util.ArrayList;
import java.util.Random;

/**
 * Kevin Peng
 * Period 2
 * Mar 16, 2017
 * Took 30 minutes
 * I didn't have to deal with bugs but the way I organized the model and interface made the whole thing awkward to code and redundant. If we jut had a reveal()
 * method instead of setReveal(), things would've been simpler. In the end, it still works so it doesn't really matter. The code to check all eight squares
 * around a given cell was also really tedious to writer. I initially coded hasWon() to check if all the mines have been flagged and that there were no extra
 * mines but I later found out that it was supposed to check if all non mines have been revealed so I changed it.
 */

public class P2_Peng_Kevin_MinesweeperModel implements P2_Peng_Kevin_MSModel {
	private Cell[][] grid;
	private int numMines;
	private boolean isFirstReveal;
	private ArrayList<P2_Peng_Kevin_MSModelListener> listeners;
	
	public P2_Peng_Kevin_MinesweeperModel(int width, int height, int numMines){
		listeners = new ArrayList<>();
		createGrid(width, height, numMines);
	}
	
	public void addListeners(P2_Peng_Kevin_MSModelListener l){
		if(!listeners.contains(l)){
			listeners.add(l);
		}
	}
	
	public void removeListeners(P2_Peng_Kevin_MSModelListener l){
		listeners.remove(l);
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
		for(P2_Peng_Kevin_MSModelListener l : listeners){
			l.modelChanged();
		}
	}
	/**
	 * Generate numMines more mines in non mine unrevealed squares.
	 * If the number of mines to generate is greater than the number of non mine unrevealed squares, the fill the rest of the non mine unrevealed squares with mines
	 * @param numMines The number of mines to add to the board
	 */
	private void generateMines(int numMines) {
		if(getNumRows() * getNumCols() - getNumHiddenMines() - getNumRevealed() <= numMines){
			for(int r = 0; r < getNumRows(); r++){
				for(int c = 0; c < getNumCols(); c++){
					if(!isMine(r, c) && !isRevealed(r, c)){
						grid[r][c].setIsMine(true);
					}
				}
			}
		}else{
		Random rand = new Random();
			for(int i = 0; i < numMines; i++){
				while(true){
					int r = rand.nextInt(getNumRows());
					int c = rand.nextInt(getNumCols());
					if(!isMine(r, c) && !isRevealed(r, c)){
						grid[r][c].setIsMine(true);
						for(P2_Peng_Kevin_MSModelListener l : listeners){
							l.cellChanged(r, c);
						}
						break;
					}
				}
			}
		}
	}

	private int getNumHiddenMines(){
		int sum = 0;
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(isMine(r, c) && !isRevealed(r, c)) sum++;
			}
		}
		return sum;
	}
	
	private int getNumRevealed(){
		int sum = 0;
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(isRevealed(r, c)) sum++;
			}
		}
		return sum;
	}

	public int getNumNeighborMines(int row, int col){
		int sum = 0;
		//top row
		if(row > 0){
			//left
			if(col > 0 && isMine(row - 1, col - 1)) sum++;
			//center
			if(isMine(row - 1, col)) sum++;
			//right
			if(col < getNumCols() - 1 && isMine(row - 1, col + 1)) sum++;
		}
		//center row
		//left
		if(col > 0 && isMine(row , col - 1)) sum++;
		//right
		if(col < getNumCols() - 1 && isMine(row, col + 1)) sum++;
		//bottom row
		if(row < getNumRows() - 1){
			//left
			if(col > 0 && isMine(row + 1, col - 1)) sum++;
			//center
			if(isMine(row + 1, col)) sum++;
			//right
			if(col < getNumCols() - 1 && isMine(row + 1, col + 1)) sum++;
		}
		return sum;
	}
	
	private int getNumNeighborFlags(int row, int col){
		int sum = 0;
		//top row
		if(row > 0){
			//left
			if(col > 0 && isFlagged(row - 1, col - 1)) sum++;
			//center
			if(isFlagged(row - 1, col)) sum++;
			//right
			if(col < getNumCols() - 1 && isFlagged(row - 1, col + 1)) sum++;
		}
		//center row
		//left
		if(col > 0 && isFlagged(row , col - 1)) sum++;
		//right
		if(col < getNumCols() - 1 && isFlagged(row, col + 1)) sum++;
		//bottom row
		if(row < getNumRows() - 1){
			//left
			if(col > 0 && isFlagged(row + 1, col - 1)) sum++;
			//center
			if(isFlagged(row + 1, col)) sum++;
			//right
			if(col < getNumCols() - 1 && isFlagged(row + 1, col + 1)) sum++;
		}
		return sum;
	}

	public boolean isMine(int row, int col) {
		return grid[row][col].isMine();
	}

	public boolean isRevealed(int row, int col) {
		return grid[row][col].isRevealed();
	}
	
	/**
	 * If the cell at (row, col) is revealed and the number of neighboring mines equals the number of neighboring flags, reveal all surrounding unflagged cells
	 * @param row The row index of the cell
	 * @param col The column index of the cell
	 */
	public void quickReveal(int row, int col){
		if(getNumNeighborFlags(row, col) == getNumNeighborMines(row, col)){
			//top row
			if(row > 0){
				//left
				if(col > 0 && !isFlagged(row - 1, col - 1)) reveal(row - 1, col - 1);
				//center
				if(!isFlagged(row - 1, col)) reveal(row - 1, col);
				//right
				if(col < getNumCols() - 1 && !isFlagged(row - 1, col + 1)) reveal(row - 1, col + 1);
			}
			//center row
			//left
			if(col > 0 && !isFlagged(row , col - 1))reveal(row, col - 1);
			//right
			if(col < getNumCols() - 1 && !isFlagged(row, col + 1)) reveal(row, col + 1);
			//bottom row
			if(row < getNumRows() - 1){
				//left
				if(col > 0 && !isFlagged(row + 1, col - 1)) reveal(row + 1, col - 1);
				//center
				if(!isFlagged(row + 1, col)) reveal(row + 1, col);
				//right
				if(col < getNumCols() - 1 && !isFlagged(row + 1, col + 1)) reveal(row + 1, col + 1);
			}
		}
	}

	public void reveal(int row, int col) {
		if(!isRevealed(row, col)){
			grid[row][col].setRevealed(true);
			if(isFirstReveal){
				generateMines(numMines);
				isFirstReveal = false;
			}
			for(P2_Peng_Kevin_MSModelListener l : listeners){
				l.cellChanged(row, col);
			}
			//recursively reveal
			if(!isMine(row, col) && getNumNeighborMines(row, col) == 0){
				//top row
				if(row > 0){
					//left
					if(col > 0) reveal(row - 1, col - 1);
					//center
					reveal(row - 1, col);
					//right
					if(col < getNumCols() - 1) reveal(row - 1, col + 1);
				}
				//center row
				//left
				if(col > 0) reveal(row, col - 1);
				//right
				if(col < getNumCols() - 1) reveal(row, col + 1);
				//bottom row
				if(row < getNumRows() - 1){
					//left
					if(col > 0) reveal(row + 1, col - 1);
					//center
					reveal(row + 1, col);
					//right
					if(col < getNumCols() - 1) reveal(row + 1, col + 1);
				}
			}
		}
	}

	public boolean isFlagged(int row, int col) {
		return grid[row][col].isFlagged();
	}

	public void setFlagged(int row, int col, boolean isFlagged) {
		if(!isFlagged(row, col) && isFlagged){
			grid[row][col].setFlagged(isFlagged);
			for(P2_Peng_Kevin_MSModelListener l : listeners){
				l.cellChanged(row, col);
			}
		}else if(isFlagged(row, col) && !isFlagged){
			grid[row][col].setFlagged(isFlagged);
			for(P2_Peng_Kevin_MSModelListener l : listeners){
				l.cellChanged(row, col);
			}
		}
	}

	public int getNumRows() {
		return grid.length;
	}

	public int getNumCols() {
		return grid[0].length;
	}

	public int getNumMinesLeft() {
		int numFlags = 0;
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(isFlagged(r, c)){
					numFlags++;
				}
			}
		}
		return numMines - numFlags;
	}

	public boolean hasWon() {
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(!isMine(r, c) && !isRevealed(r, c)){
					return false;
				}
			}
		}
		return true;
	}

	public boolean hasLost() {
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				if(isMine(r, c) && isRevealed(r, c)){
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
