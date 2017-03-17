
public interface P2_Peng_Kevin_MSModel {
	public int getNumRows();
	public int getNumCols();
	public void createGrid(int width, int height, int numMines);
	public int getNumNeighborMines(int row, int col);
	public boolean isMine(int row, int col);
	public int getNumMinesLeft();
	public boolean isRevealed(int row, int col);
	public void reveal(int row, int col);
	public boolean isFlagged(int row, int col);
	public void setFlagged(int row, int col, boolean isFlagged);
	public boolean hasWon();
	public boolean hasLost();
}
