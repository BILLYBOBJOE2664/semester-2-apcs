/**
 * Kevin Peng
 * Period 2
 * Feb 1, 2017
 * Took 40 minutes
 * 
 * Everything went smoothly. The bugs I encountered were typos. The main problem I couldn't solve was how to set the font to monospaced so that the column markers and columns would match up.
 * I decided not do try to hard because the example wasn't in monospace either.
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class P2_Peng_Kevin_LifeModel extends GridModel<Boolean>{
	
	ArrayList<GenerationListener> generationListeners;
	int gen; //the current generation
	/**
	 * Creates a new grid using the file passed in
	 * @param filename The name of the file containing the initial configuration
	 * @throws IOException
	 */
	public P2_Peng_Kevin_LifeModel(Boolean[][] grid){
		super(grid);
		generationListeners = new ArrayList<GenerationListener>();
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
		setGeneration(getGeneration() + 1);
	}
	
	/**
	 * returns a string representation of the board with row and column headers
	 */
	public String toString(){
		String str = "";
		//length of the row markers
		int length = (int) Math.log10(getNumRows()) + 1;
		//print buffers before the column markers
		for(int i = 0; i < length; i++){
			str += " ";
		}
		//print column markers
		for(int i = 0; i < getNumCols(); i++){
			str += ".";
		}
		str += "\n";
		//print each row
		for(int i = 0; i < getNumRows(); i++){
			//print row marker
			str += String.format("%" + length + "d", i);
			//print the cells
			for(int k = 0; k < getNumCols(); k++){
				if(getValueAt(i, k)){
					str += "*";
				}else{
					str += " ";
				}
			}
			str += "\n";
		}
		return str;
	}
	
	/**
	 * Counts the number of living neighbors around a given cell
	 * @param row The row the cell is in
	 * @param column The column the cell is in
	 * @return The number of living neighbors around the cell
	 */
	private int numNeighbors(int row, int column){
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
	
	public void addGenerationListener(GenerationListener l){
		if(!generationListeners.contains(l)){
			generationListeners.add(l);
		}
	}
	
	public void removeGenerationListener(GenerationListener l){
		generationListeners.remove(l);
	}
	
	public void setGeneration(int gen){
		if(this.gen != gen){
			for(GenerationListener l : generationListeners){
				l.GenerationChanged(this.gen, gen);
			}
			this.gen = gen;
		}
	}
	
	public int getGeneration(){
		return gen;
	}
}
