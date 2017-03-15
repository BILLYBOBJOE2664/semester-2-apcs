import java.util.Random;

public class MinesweeperModel implements MSModel {
	private Cell[][] grid;
	
	public MinesweeperModel(int width, int height){
		createGrid(width, height);
	}
	
	public void createGrid(int width, int height) {
		grid = new Cell[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				grid[r][c] = new Cell(false);
			}
		}
	}

	public void GenerateMines(int numMines) {
		Random rand = new Random();
		for(int i = 0; i < numMines; i++){
			while(true){
				int r = rand.nextInt(grid.length);
				int c = rand.nextInt(grid[0].length);
				if(!grid[r][c].isMine()){
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
		grid[row][col].setRevealed(isRevealed);
	}

	public boolean isFlagged(int row, int col) {
		return grid[row][col].isFlagged();
	}

	public void setFlagged(int row, int col, boolean isFlagged) {
		grid[row][col].setFlagged(isFlagged);
	}

	public boolean isQuestionMarked(int row, int col) {
		return grid[row][col].isQuestionMarked();
	}

	public void setQuestionMarked(int row, int col, boolean isQuestionMarked) {
		grid[row][col].setQuestionMarked(isQuestionMarked);
	}
	
	private class Cell{
		private static final int DEFAULT = 0;
		private static final int REVEALED = 1;
		private static final int FLAGGED = 2;
		private static final int QUESTION_MARKED = 3;
		
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

		public boolean isQuestionMarked() {
			return state == QUESTION_MARKED;
		}

		public void setQuestionMarked(boolean isQuestionMarked) {
			if(isQuestionMarked){
				state = QUESTION_MARKED;
			}else if(state == QUESTION_MARKED){
				state = DEFAULT;
			}
		}
	}
	
}
