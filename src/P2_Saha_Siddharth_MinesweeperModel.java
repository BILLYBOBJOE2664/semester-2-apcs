import java.util.Random;

public class P2_Saha_Siddharth_MinesweeperModel implements P2_Peng_Kevin_MSModel {
  private char[][] model;
  private boolean[][] visible;
  private boolean[][] flag;
  private boolean gameOver;
  private int flags, cheat;
  
  public P2_Saha_Siddharth_MinesweeperModel(int rows, int cols, int n) {
    this.model = new char[rows][cols];
    this.visible = new boolean[rows][cols];
    this.flag = new boolean[rows][cols];
    this.gameOver = false;
    cheat = 1;
    flags = 0;
  }
  
  @Override
  public int getNumRows() {
    return this.model.length;
  }
  @Override
  public int getNumCols() {
    return this.model[0].length;
  }
  @Override
  public boolean isMine(int row, int col) {
    if(getValueAt(row,col) == '*')
      return true;
    else
      return false;
  }
  
  public int flagCount() {
	  return flags;
  }
  @Override
  public int getNumNeighborMines(int row, int col) {
    int n = 0;
    for(int r = row - 1; r <= row + 1; r++) {
      for(int c = col - 1; c <= col + 1; c++) {
        if(r >= 0 && r < getNumRows() && c >= 0 && c < getNumCols()) {
        	if(r == c)
        		continue;
          if(isMine(r,c))
            n++;
          }
      }
    }
    return n;
  }
  
  private char getValueAt(int row, int col) {
	  return model[row][col];
  }
  
  private void setMines(int n) {
	  for(int i = 0; i < n; i++) {
		  Random rand = new Random();
		  int  r = rand.nextInt(getNumRows());
		  int c = rand.nextInt(getNumCols());
		  if(model[r][c] == '*')
			  i--;
		  model[r][c] = '*';
	  }
  }
  @Override
  public void createGrid(int rows, int cols, int n) {
    for(int i = 0; i < getNumRows(); i++) {
      for(int j = 0; j < getNumCols(); j++) {
        model[i][j] = '_';
        visible[i][j] = false;
      }
    }
    setMines(n);
  }
  @Override
  public int isGameOver() {
	  if(gameOver == true)
		  return 0;
	  for(int i = 0 ; i < getNumRows(); i++) {
		  for(int j = 0; j < getNumCols(); j++) {
			  if(!isMine(i, j) && !isRevealed(i, j))
				  return 2;
		  }
	  }
	return 1;
  }
  @Override
  public boolean isRevealed(int row, int col) {
	  // TODO Auto-generated method stub
	  if (visible[row][col])
		  return true;
	  else
		  return false;
  }
  private int totalMines() {
	  // TODO Auto-generated method stub
	  int n = 0;
	  for(int i = 0; i < getNumRows(); i++) {
		  for(int j = 0; j < getNumCols(); j++) {
			  if(getValueAt(i,j) == '*' )
				  n++;
		  }
	  }
	  return n;
  }
  @Override
  public void reveal(int row, int col) {
	  // TODO Auto-generated method stub
	  if(getValueAt(row,col) == '*' && cheat == 1) {
		  setMines(1);
		  model[row][col] = '_';
		  cheat++;
		  reveal(row,col);
	  }
	  else if(getValueAt(row,col) == '*' && cheat!=1) {
		  gameOver = true;
	  }
	  else {
		  visible[row][col] = true;
		  for(int r = row - 1; r <= row + 1; r++) {
		      for(int c = col - 1; c <= col + 1; c++) {
		        if(r >= 0 && r < getNumRows() && c >= 0 && c < getNumCols()) {
		          if(!isRevealed(r,c)) {
		        	  visible[r][c] = true;

		        	  if(getNumNeighborMines(r,c) == 0)
		        		  reveal(r,c);
		          }
		          }
		      }
		    }   
	  }
  }

  @Override
  public void setFlagged(int row, int col, boolean oldVal) {
	// TODO Auto-generated method stub
	flag[row][col] = !oldVal;
}

@Override
public boolean isFlagged(int row, int col) {
	// TODO Auto-generated method stub
	if(flag[row][col])
		return true;
	else
		return false;
}

@Override
public int getNumMinesLeft() {
	// TODO Auto-generated method stub
	return totalMines() - flagCount();
}

@Override
public boolean hasWon() {
	// TODO Auto-generated method stub
	for(int r = 0; r < getNumRows(); r++){
		for(int c = 0; c < getNumCols(); c++){
			if(!isMine(r,c) && !isRevealed(r,c)){
				return false;
			}
		}
	}
	return true;
}

@Override
public boolean hasLost() {
	// TODO Auto-generated method stub
	for(int r = 0; r < getNumRows(); r++){
		for(int c = 0; c < getNumCols(); c++){
			if(isMine(r,c) && isRevealed(r,c)){
				return true;
			}
		}
	}
	return false;
}


}
