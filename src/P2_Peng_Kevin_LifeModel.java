/**
 * Kevin Peng
 * Period 2
 * Feb 1, 2017
 * Took 40 minutes
 * 
 * Everything went smoothly. The bugs I encountered were typos. The main problem I couldn't solve was how to set the font to monospaced so that the column markers and columns would match up.
 * I decided not do try to hard because the example wasn't in monospace either.
 */

import java.util.Scanner;
import java.io.*;
public class P2_Peng_Kevin_LifeModel extends GridModel<Boolean>{

	//the main array that keeps track of all the cells. True is alive and false is dead
	/*public static void main(String[] args) throws IOException{
		P2_Peng_Kevin_Life test = new P2_Peng_Kevin_Life("life100.txt");
		test.printBoard();
		for(int i = 0; i < 150; i++){
			test.nextGeneration();
			test.printBoard();
		}
		//test.runLife(5);
		//test.printBoard();
		System.out.println("Number of liveing cells in row 9: " + test.rowCount(9));
		System.out.println("Number of liveing cells in col 9: " + test.colCount(9));
		System.out.println("Number of living cells: " + test.totalCount());
	}*/
	
	/**
	 * Creates a new grid using the file passed in
	 * @param filename The name of the file containing the initial configuration
	 * @throws IOException
	 */
	public P2_Peng_Kevin_LifeModel(Boolean[][] grid){
		super(grid);
	}
	/**
	 * Runs for a given number of generations
	 * @param numGenerations The number of generations to run
	 */
	public void runLife(int numGenerations){
		for(int i = 0; i < numGenerations; i++){
			nextGeneration();
		}
	}
	
	/**
	 * Steps forward one generation
	 */
	public void nextGeneration(){
		Boolean[][] nextGrid = new Boolean[getNumRows()][getNumCols()];
		for(int r = 0; r < getNumRows(); r++){
			for(int c = 0; c < getNumCols(); c++){
				int numNeighbors = numNeighbors(r, c);
				if(getValueAt(r, c)){
					if(numNeighbors == 2 || numNeighbors == 3){
						nextGrid[r][c] = true;
					}else{
						nextGrid[r][c] = false;
					}
				}else{
					if(numNeighbors == 3){
						nextGrid[r][c] = true;
					}else{
						nextGrid[r][c] = false;
					}
				}
			}
		}
		setGrid(nextGrid);
	}
	
	/**
	 * Prints out the board with row and column headers
	 */
	public void printBoard(){
		//length of the row markers
		int length = (int) Math.log10(getNumRows()) + 1;
		//print buffers before the column markers
		for(int i = 0; i < length; i++){
			System.out.print(" ");
		}
		//print column markers
		for(int i = 0; i < getNumCols(); i++){
			System.out.print(i);
		}
		System.out.println();
		//print each row
		for(int i = 0; i < getNumRows(); i++){
			//print row marker
			System.out.printf("%" + length + "d", i);
			//print the cells
			for(int k = 0; k < getNumCols(); k++){
				if(getValueAt(i, k)){
					System.out.print("*");
				}else{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Counts the number of living neighbors around a given cell
	 * @param row The row the cell is in
	 * @param column The column the cell is in
	 * @return The number of living neighbors around the cell
	 */
	public int numNeighbors(int row, int column){
		int num = 0;
		for(int r = -1; r <= 1; r++){
			for(int c = -1; c <= 1; c++){
				//checks if both r and c or zero or if the index we're going to check is out of bound
				if((r != 0 || c != 0) && row + r >= 0 && row + r < getNumRows() && column + c >= 0 && column + c < getNumCols()){
					num += getValueAt(row + r, column + c) ? 1 : 0;
				}
			}
		}
		return num;
	}
}
