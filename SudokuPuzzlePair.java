/* SudokuPuzzlePair.java
 * CS230 Final Project
 * Written by: Iman Hussain, Alice Pan and Nicole Gates
 * Modify date: May 5 2017
 * 
 * Purpose: creates an array of Sudoku puzzles that contains the
 * solution puzzle and the puzzle that has blanks to be filled in.
 */

import java.util.Vector;
import java.util.Random;

/****************************************************************
 * This class holds two Sudoku Puzzle Objects,
 * the solved puzzle and the scraped puzzle for the user to solve
 *****************************************************************/
public class SudokuPuzzlePair {
  
  SudokuPuzzle solutionGrid;
  SudokuPuzzle puzzleGrid;

/**************************************************************** 
 * Constructor
 * creates a Sudoku Puzzle object with the solution
 * and a scraped Sudoku Puzzle object to be solved
 * 
 * @param  inFile     A file that contains a complete sudoku puzzle
 * @param  difficulty The level of difficulty, 
 *                    Easy, Medium or Hard, that the user chooses
 ****************************************************************/
  public SudokuPuzzlePair(String inFile, String difficulty) {
    solutionGrid = new SudokuPuzzle(inFile, difficulty);
    puzzleGrid = new SudokuPuzzle(inFile, difficulty);
    puzzleGrid.scrapePuzzle();
  }

 //Getter Methods
  
 /****************************************************
  * This method gets the solution of the sudoku puzzle
  * @return  complete/solved SudokuPuzzle object
  ****************************************************/
  public SudokuPuzzle getCompPuzzle() {
    return solutionGrid;
  }
  
 /****************************************************************
  * This method gets the scraped sudoku puzzle that user will solve
  * @return  incomplete/unsolved SudokuPuzzle object
  *****************************************************************/
  public SudokuPuzzle getScrapedPuzzle() {
    return puzzleGrid;
  }
  
/**********************************************************
 * Checks if one puzzle is equivalent to the other
 * Tracks which cells have an incorrect number 
 * in a Vector of arrays (tracks row and column)
 * 
 * @return  a vector of arrays with the 
 *          row and column number of the incorrect numbers  
 **********************************************************/ 
  public Vector<int[]> isEqualPuzzle() {
    Vector<int[]> values = new Vector<int[]>();
    
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (!this.isEqualCell(i, j)) {
          int[] temp = new int[2];
          temp[0] = i;
          temp[1] = j;
          values.add(temp);
        }
      }
    }
    
    return values;
  }
  
/******************************************************************** 
 * Checks the specified cell in both grids to see if they match
 * 
 * @param  row    The row number of the cell that is being checked
 * @param  column The column number of the cell that is being checked 
 * @return a boolean (true or false) according to if the cells match 
 *         in the solution and what the user has inputed
 ********************************************************************/
  public boolean isEqualCell(int row, int column) {
    int[][] temp1 = solutionGrid.getGrid();
    int[][] temp2 = puzzleGrid.getGrid();
    
    if (temp1[row][column] == temp2[row][column])
      return true;
    
    return false;
  }
  
  /**************************************************************
    * This method fills in a correct number in the puzzle 
    * when the hint button is pressed
    * 
    * @return an array with the row and column number of the cell 
    * and the correct number to be filled
   **************************************************************/
  public int[] getHint() {
    Random rand = new Random();
    int[] temp = new int[3];
    
    if (!puzzleGrid.isFull()) {
      
      while (true) {
        int row = rand.nextInt(9);
        int column = rand.nextInt(9);
        
        if (puzzleGrid.getValue(row, column) == 0) {
          temp[0] = row;
          temp[1] = column;
          temp[2] = solutionGrid.getValue(row, column);
          puzzleGrid.setCell(temp[2], temp[0], temp[1]);
          break;
        }
      }
    }
    return temp;
  }
}