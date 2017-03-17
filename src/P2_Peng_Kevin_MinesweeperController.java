import java.util.Scanner;

/**
 * Kevin Peng
 * Period 2
 * Mar 16, 2017
 * Took 40 minutes
 * I surprisingly ran into few problems. I incorrectly calculated the number of digits in the row/col incorrectly, and had to change the order so that
 * flags have a higher priority than unrevealed squares. I also move the win/loss check so that the board would be printed before the game lost.
 */


public class P2_Peng_Kevin_MinesweeperController {

	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(true){
			play(10, 10, 5);
			System.out.println("Want to play again? (Enter \"y\" or \"n\"");
			if(scanner.next().equals("n")) break;
		}
		System.out.println("Goodbye.  Thanks for playing!");
	}
	
	private static void play(int width, int height, int numMines){
		Scanner scanner = new Scanner(System.in);
		P2_Saha_Siddharth_MinesweeperModel grid = new P2_Saha_Siddharth_MinesweeperModel(width, height, numMines);
		while(true){
			printBoard(grid);
			if(grid.hasWon()){
				System.out.println("You win");
				break;
			}else if(grid.hasLost()){
				System.out.println("You lost");
				break;
			}
			System.out.println("There are " + grid.getNumMinesLeft() + " left");
			System.out.println("Would you like to flag a cell or reveal a cell?");
			System.out.print("Enter \"f\" of \"r\" > ");
			String command = scanner.next();
			scanner.nextLine();
			System.out.print("Enter row: ");
			int row = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Enter column: ");
			int col = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
			
			if(row >= 0 && row < grid.getNumRows() && col >= 0 && col < grid.getNumCols()){
				if(command.equals("f") && !grid.isRevealed(row, col)){
					grid.setFlagged(row, col, !grid.isFlagged(row, col));
				}else if(command.equals("r")){
					grid.reveal(row, col);
				}else{
					System.out.println("Invalid command");
				}
			}else{
				System.out.println("Invalid command");
			}
			
		}
	}
	
	private static void printBoard(P2_Peng_Kevin_MSModel grid){
		int rowDigits = (int)(Math.log10(grid.getNumRows() - 1) + 1); 
		int colDigits = (int)(Math.log10(grid.getNumCols() - 1) + 1) + 1;//the plus one adds a buffer between columns
		//print column header twice
		for(int i = 0; i < 2; i++){
			System.out.printf("%" + rowDigits + "s", "");
			for(int k = 0; k < grid.getNumCols(); k++){
				System.out.printf("%-" + colDigits + "d", k);
			}
			System.out.printf("%5s", "");
		}
		System.out.println();
		//print the row with row headers
		for(int r = 0; r < grid.getNumRows(); r++){
			//hidden board
			System.out.printf("%" + rowDigits + "d", r);
			for(int c = 0; c < grid.getNumCols(); c++){
				if (grid.isFlagged(r, c)){
					System.out.printf("%-" + colDigits + "s", "!");
				}else if(!grid.isRevealed(r, c)){
					System.out.printf("%-" + colDigits + "s", "_");
				}else if(grid.isMine(r, c)){
					System.out.printf("%-" + colDigits + "s", "*");
				}else{
					System.out.printf("%-" + colDigits + "s", grid.getNumNeighborMines(r, c) == 0 ? " " : Integer.valueOf(grid.getNumNeighborMines(r, c)));
				}
			}
			
			//revealed board
			System.out.printf("%5s", "");
			System.out.printf("%" + rowDigits + "d", r);
			for(int c = 0; c < grid.getNumCols(); c++){
				if(grid.isMine(r, c)){
					System.out.printf("%-" + colDigits + "s", "*");
				}else{
					System.out.printf("%-" + colDigits + "s", grid.getNumNeighborMines(r, c) == 0 ? " " : Integer.valueOf(grid.getNumNeighborMines(r, c)));
				}
			}
			System.out.println();
		}
	}
}
