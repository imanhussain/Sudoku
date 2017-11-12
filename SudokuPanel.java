/*SudokuPanel.java
  CS230 Final Project
  Written by: Iman Hussain, Alice Pan and Nicole Gates
  Modified date: 5/9/2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

/*********************************************************
 * This class creates the SudokuPuzzle panel where the
 * user can play the game.
 *********************************************************/

public class SudokuPanel extends JPanel{
  private SudokuPuzzlePair pairs;
  private SudokuPuzzle puzzle, solution;
  private JPanel panel, left, right;
  private JTextField[][] grid;
  private JButton hint, switchUser, newGame;
  private JLabel space;
  private int count;
 
/*******************************************************
 * Constructor
 * Creates the Sudoku panel, which allows users to fill
 * in sudoku puzzles, generate a new sudoku puzzle,
 * switch users, or get a hint (fill in a random number
 * in the puzzle).
 * 
 * @param  chosenPair  contains the completed sudoku 
 *                     puzzle (solution) and the 
 *                     incomplete puzzle (to be filled by 
 *                     user)
 * @param  swu         JButton from the GUI driver class,
 *                     allows the Sudoku panel to switch
 *                     to the Welcome panel when pressed
 * @param  newG        JButton from the GUI driver class,
 *                     allows the user to generate a new
 *                     sudoku puzzle
 *******************************************************/  
  public SudokuPanel(SudokuPuzzlePair chosenPair, JButton swu, JButton newG) {
    pairs = chosenPair;
    puzzle = pairs.getScrapedPuzzle();
    solution = pairs.getCompPuzzle();
    switchUser = swu;
    newGame = newG;
    grid = new JTextField[9][9];
    space = new JLabel(" ");
    count = 0;
    
    // Set the overall layout
    panel = new JPanel();
    panel.setBackground(new Color(217, 173, 155));
    panel.setLayout(new FlowLayout());
    
    // Create the left part of the panel, i.e the puzzle itself
    left = new JPanel();
    left.setBackground(new Color(217, 173, 155));
    left.setLayout(new GridLayout(9,9));
    
    // Set different types of textfield according to the given puzzle, i.e.
    // how many numbers are left blank to be filled in by the user
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        if (puzzle.getValue(i,j) != 0) {
          grid[i][j] = new JTextField(Integer.toString(puzzle.getValue(i,j)), 3);
          grid[i][j].setHorizontalAlignment(JTextField.CENTER);
          grid[i][j].setEditable(false);
        }
        else 
          grid[i][j] = new JTextField(3);
      }
    }
      
      // Add all the textfields onto the panel
      for (int m = 0; m < 9; m ++) {
        for (int n = 0; n < 9; n ++) {
          grid[m][n].addActionListener(new NumberListener());
          left.add(grid[m][n]);
        }
      }
      
      //add the left panel
      panel.add(left);
      
      // Create the right part of the panel, which is where the users can set
      // get hints, start a new game, and switch users
      right = new JPanel();
      right.setBackground(new Color(217, 173, 155));
      right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));
      
      // Create each part of the right side and set alignment
      hint = new JButton("Hint");
      hint.addActionListener(new HintListener());
      hint.setAlignmentX(Component.CENTER_ALIGNMENT);
      newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
      switchUser.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      right.add(hint);
      right.add(newGame);
      right.add(switchUser);
      panel.add(right);
      add(panel);
    }
  
 /*****************************************************
  * This class implements the ActionListener for the
  * hint button. It selects a random unfilled space in
  * the sudoku puzzles, fills in the number, turns the
  * background yellow, and makes the number uneditable.
  *****************************************************/  
  private class HintListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      if (event.getSource() == hint) {
        int[] temp = new int[3];
        temp = pairs.getHint();
        grid[temp[0]][temp[1]].setText(Integer.toString(temp[2]));
        grid[temp[0]][temp[1]].setBackground(Color.YELLOW);
        grid[temp[0]][temp[1]].setEditable(false);
        count++;
        
        if (puzzle.getDifficulty() == "m" && count == 5) {
          hint.setEnabled(false);
        }
        else if (puzzle.getDifficulty() == "h" && count == 3) {
          hint.setEnabled(false);
        }
        else
          hint.setEnabled(true);
      }
    }
  }
  
  /*****************************************************
   * This class implements the ActionListener for the
   * number textfields that the user fills in.
   ****************************************************/
  private class NumberListener implements ActionListener {
    int rowSelected, colSelected;
    boolean full = false;
    Vector<int[]> cells = new Vector<int[]>();
    Color color = UIManager.getColor("Panel.background");
    
    public void actionPerformed(ActionEvent event) {
      //create variables to store the changed cell
      rowSelected = -1;
      colSelected = -1;
      JTextField source = (JTextField)event.getSource();
      boolean found = false;
      
      //find the exact cell
      for (int row = 0; row < 9 && !found; row ++) {
        for (int col = 0; col < 9 && !found; col ++) {
          if (grid[row][col] == source) {
            rowSelected = row;
            colSelected = col;
            found = true; 
          }
        }
      }
      
      if (!full) {
        // Catches error if the user enters a non-integer value
        try {
          int check = Integer.parseInt(grid[rowSelected][colSelected].getText());
        } catch (NumberFormatException e) {
          grid[rowSelected][colSelected].setText(Integer.toString(0));
        }           
        
        // Get the entered value, react according to the level of difficulty
        int input = Integer.parseInt(grid[rowSelected][colSelected].getText());
        if (puzzle.getDifficulty() == "e" || puzzle.getDifficulty() == "m") {
          puzzle.setCell(input, rowSelected, colSelected);
          if (pairs.isEqualCell(rowSelected, colSelected)) {
            grid[rowSelected][colSelected].setText(Integer.toString(input));
            grid[rowSelected][colSelected].setHorizontalAlignment(JTextField.CENTER);
            grid[rowSelected][colSelected].setBackground(Color.WHITE);
            grid[rowSelected][colSelected].setEditable(false);
          }
          else {
            puzzle.setCell(0, rowSelected, colSelected);
            grid[rowSelected][colSelected].setText(Integer.toString(input));
            grid[rowSelected][colSelected].setHorizontalAlignment(JTextField.CENTER);
            grid[rowSelected][colSelected].setBackground(Color.RED);
          }
        }
        else if (puzzle.getDifficulty() == "h") {
          if (pairs.isEqualCell(rowSelected, colSelected)) 
            grid[rowSelected][colSelected].setBackground(Color.WHITE);
        }
        
        if (puzzle.getDifficulty() == "h") {
          puzzle.setCell(input, rowSelected, colSelected);
        }
        
        // Check if full (call a method)
        if (pairs.getScrapedPuzzle().isFull()) {
          full = true;
        }
      }
      // When the user has filled in all numbers, checks if the puzzle has been
      // correctly completed (or not) and responds accordingly
      else {
        if (pairs.isEqualPuzzle().size() == 0) {
          for (int m = 0; m < 9; m ++) {
            for (int n = 0; n < 9; n ++) 
              grid[m][n].setBackground(Color.WHITE);
          }
          JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle.");
        }
        else {
          cells = pairs.isEqualPuzzle();
          for (int i = 0; i < cells.size(); i ++) {
            rowSelected = cells.get(i)[0];
            colSelected = cells.get(i)[1];
            grid[rowSelected][colSelected].setBackground(Color.RED);
          }
        }
      }
    }
  }
}