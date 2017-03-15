
public interface MSModel {
	public void createGrid(int width, int height);
	public void GenerateMines(int numMines);
	public int getNumNeighborMines(int row, int col);
	public boolean isMine(int row, int col);
	public boolean isRevealed(int row, int col);
	public void setRevealed(int row, int col, boolean isRevealed);
	public boolean isFlagged(int row, int col);
	public void setFlagged(int row, int col, boolean isFlagged);
	public boolean isQuestionMarked(int row, int col);
	public void setQuestionMarked(int row, int col, boolean isQuestionMarked);
}
