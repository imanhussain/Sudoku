/* SudokuPuzzle.java
 * CS230 Final Project
 * Written by: Iman Hussain, Alice Pan and Nicole Gates
 * Modify date: May 5 2017
 * 
 * Purpose: creates a 2D array that acts as a 9x9 grid and simulates a Sudoku puzzle.
 */

import java.util.Scanner;
import java.util.Random;
import java.io.*;

/******************************************************* 
 * This class creates a 2D array that acts as a 9x9 grid 
 * and simulates a Sudoku puzzle
 *******************************************************/

public class SudokuPuzzle {
 
  int[][] grid;
  String difficulty;

/*******************************************************
 * Constructor #1
 * Creates a 9X9 grid filled with 0's
 * 
 * @param  d  The level of difficulty the user 
 *            has chosen for the puzzles
 *******************************************************/
  public SudokuPuzzle(String d) {
    grid = new int[9][9];
    
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        grid[i][j] = 0;
      }
    }
    
    difficulty = d;
  }

 /************************************************************
  * Constructor #2
  * Creates a 9X9 grid filled with 0's
  * Reads a solvable sudoku puzzle from an input file
  * 
  * @param  d     The level of difficulty the user 
  *               has chosen for the puzzles
  * @param inFile A file that contains a complete sudoku puzzle 
   *************************************************************/
  public SudokuPuzzle(String inFile, String d) {
    difficulty = d;
    grid = new int[9][9];
    int row = 0;
    int column = 0;
    int count = 0;
    
    try {
      Scanner scan = new Scanner(new File(inFile));
      while (scan.hasNext()) {
        String parts[] = scan.next().split(",");        
        for (int i = 0; i < parts.length; i++) {
          try {
            grid[row][column] = Integer.parseInt(parts[i]);
          }
          catch (NumberFormatException e) {
            System.out.println ("Format error in input file " + inFile + ".");
          }          
          column++;
          count++;          
          if (count > 8) {
            row++;
            count = 0;
            column = 0;
          }
        }
      }      
      scan.close();
    }    
    catch (FileNotFoundException e) {
      System.out.println("The file " + inFile + " was not found.");
    } 
  }
  
 //Getter Methods
   
 /*************************************************
  * This method gets the current grid of the puzzle
  * @return current grid of the SudokuPuzzle object
  *************************************************/
  public int[][] getGrid() {
    return grid;
  }
  
 /********************************************************
  * This method gets the value at the specified cell 
  * (row and column) in the grid
  * 
  * @param  row    The row number that is being accessed
  * @param  column The column number that is being accessed 
  * @return the int value at the specified cell 
  *********************************************************/
  public int getValue(int row, int column) {
    return grid[row][column];
  }
  
 /****************************************
  * This method gets the level of difficulty
  * the user has chosen for their puzzles 
  * 
  * @return the current level of difficulty
  ****************************************/
  public String getDifficulty() {
    return difficulty;
  }
  
//Setter Methods
  
 /*********************************************
  * This method sets a new level of difficulty
  * for the puzzles
  * 
  * @param s  the new level of difficulty for 
  *           the puzzles (easy, medium, hard)
  *********************************************/
  public void setDifficulty(String s) {
    difficulty = s;
  }
  
 /*********************************************
  * This method sets a new value for a specified 
  * cell in the puzzle
  * 
  * @param value  The new value to set the cell to 
  * @param row    The row of the cell
  * @param column The column of the cell 
  *********************************************/
  public void setCell(int value, int row, int column) {
    grid[row][column] = value;
  }    
  
//*****************************//
// toString method             //
// prints the grid             //
//*****************************//
  public String toString() {
    String s = "";
    int column = 0;
    
    //----------------------------//
    // prints the grid row by row //
    //----------------------------//
    for (int i = 0; i < grid[column].length; i++) {
      for (int j = 0; j < grid.length; j++) {
          s += (Integer.toString(grid[i][j]) + " ");
      }
      
      s += "\n";
      column++;
      
      if (column > 8)
        break;
    }
    
    return s;
  }
 
 /****************************************************
  * This method checks if the puzzle has 
  * been completely filled with numbers
  * 
  * @return a boolean (true or false) depending on if 
  *         the puzzle has been filled or not
  ****************************************************/ 
  public boolean isFull() {
    
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (grid[i][j] == 0)
          return false;
      }
    }
    
    return true;
  }
  
 /************************************************ 
  * This method replaces a random selection 
  * of numbers in the grid with blank spaces, 
  * to be filled in by the player. How many 
  * spaces is determined by the difficulty entered
  *************************************************/ 
  public void scrapePuzzle() {
    int count = 0;
    Random rand = new Random();
    
    if (difficulty.toUpperCase().equals("E")) {
      count = 55;
    }
    
    if (difficulty.toUpperCase().equals("M")) {
      count = 60;
    }
    
    if (difficulty.toUpperCase().equals("H")) {
      count = 65;
    }
    
   while (count > 0) {
      int row = rand.nextInt(9);
      int column = rand.nextInt(9);
      
      if (grid[row][column] != 0) {
        count--;
        grid[row][column] = 0;
      }
    }
  }
}