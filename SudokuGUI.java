/*SudokuGUI.java
  CS230 Final Project
  Written by: Iman Hussain, Alice Pan and Nicole Gates
  Modified date: 5/9/2017
 */

import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import javafoundations.*;

/*************************************************
 * This class is responsible for:
 * creating a queue of the 8 predefined puzzles
 * setting up and displaying the panels and buttons
 **************************************************/

public class SudokuGUI {
  private static Hashtable<String, SudokuPuzzlePair> database;
  private static LinkedQueue<SudokuPuzzlePair> dataE, dataM, dataH;
  private static JButton startGame = new JButton("Start Game");
  private static JButton newGame = new JButton("New Game");
  private static JButton switchUser = new JButton("Switch User");
  private static String currentUser, currentLevel;
  private static SudokuPuzzlePair currentPuzzle;
  //private static JFrame frame1, frame2;
  private static JFrame frame;
  private static SudokuWPanel panel1;
  private static SudokuPanel panel2;
  
/********************************************
 * Main Method
 * Creates a queue of the 8 puzzles
 * Creates the frame and the first panel (welcome panel)
 * @param args takes in command-line arguments 
 *             as an array of String objects
 ********************************************/
  public static void main (String[] args) {
    //instantiate the database for usernames
    database = new Hashtable<String, SudokuPuzzlePair>();
    startGame.addActionListener(new StartListener());
    switchUser.addActionListener(new UserListener());
    newGame.addActionListener(new GameListener());
    
    //instantiate the puzzle database
    dataE = new LinkedQueue<SudokuPuzzlePair>();
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle1.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle2.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle3.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle4.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle5.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle6.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle7.txt","e"));
    dataE.enqueue(new SudokuPuzzlePair("sudokupuzzle8.txt","e"));
    dataM = new LinkedQueue<SudokuPuzzlePair>();
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle1.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle2.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle3.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle4.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle5.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle6.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle7.txt","m"));
    dataM.enqueue(new SudokuPuzzlePair("sudokupuzzle8.txt","m"));
    dataH = new LinkedQueue<SudokuPuzzlePair>();
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle1.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle2.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle3.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle4.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle5.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle6.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle7.txt","h"));
    dataH.enqueue(new SudokuPuzzlePair("sudokupuzzle8.txt","h"));
    
    
    //the welcome frame
    frame = new JFrame("Sudoku");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    panel1 = new SudokuWPanel(startGame);
    frame.getContentPane().add(panel1);
    
    frame.pack();
    frame.setVisible(true);    
  }
  
/*******************************************************************
 * This class creates actionlisteners for the welcome panel buttons
 *******************************************************************/
  private static class StartListener implements ActionListener {
    
 /****************************************************
  * This method takes care of the start game button
  * Recognizes which level the user has chosen, 
  * and gets the corresponding queue
  * If the entered username already exists, 
  * it resumes the game with the puzzle the left off on
  * 
  * @param  event  when the startGame button is pressed 
  ****************************************************/
    public void actionPerformed (ActionEvent event) {
      if (event.getSource() == startGame) {
        //get information from welcome panel
        currentUser = panel1.getCurrentUser();
        //System.out.println(currentUser);
        currentLevel = panel1.getLevel();
        //currentPuzzle = new SudokuPuzzlePair();
        
        //add information to user database and assign puzzle if the usernames
        //exists, a dialogue window would pop up and the user will have to
        //resume their game
        if (!database.isEmpty()) {
          if (!database.containsKey(currentUser)) {
            if (currentLevel == "e") {
              currentPuzzle = dataE.dequeue();
              dataE.enqueue(currentPuzzle);
            }
            else if (currentLevel == "m") {
              currentPuzzle = dataM.dequeue();
              dataM.enqueue(currentPuzzle);
            }
            else {
              currentPuzzle = dataH.dequeue();
              dataH.enqueue(currentPuzzle);
            }
            database.put(currentUser, currentPuzzle);
          }
          else {
            JOptionPane.showMessageDialog(null, "You'll resume your previous puzzle.");
            currentPuzzle = database.get(currentUser);
          }
        }
        else {
          if (currentLevel == "e") {
            currentPuzzle = dataE.first();
            SudokuPuzzlePair temp = dataE.dequeue();
            dataE.enqueue(temp);
          }
          else if (currentLevel == "m") {
            currentPuzzle = dataM.first();
            SudokuPuzzlePair temp = dataM.dequeue();
            dataM.enqueue(temp);
          }
          else {
            currentPuzzle = dataH.first(); 
            SudokuPuzzlePair temp = dataH.dequeue();
            dataH.enqueue(temp);
          }
        }
        database.put(currentUser, currentPuzzle);
        frame.getContentPane().removeAll();
        panel2 = new SudokuPanel(currentPuzzle, switchUser, newGame);
        frame.getContentPane().add(panel2);
        frame.pack();
        frame.setVisible(true);
      }
    }
  }
  
/*****************************************************************
 * This class creates a userlistener for the switch user button
 *****************************************************************/
  private static class UserListener implements ActionListener {
    
 /*******************************************************
  * This method takes care of the switchUser button
  * If the button is pressed, it switches the panel 
  * back to the Welcome Panel
  * 
  * @param  event  when the switchUser button is pressed 
  *******************************************************/
    public void actionPerformed (ActionEvent event) {
      if (event.getSource() == switchUser) {
        frame.getContentPane().removeAll();
        SudokuWPanel p = new SudokuWPanel(startGame);
        frame.getContentPane().add(p);
        frame.pack();
        frame.setVisible(true);
      }
    }
  }
  
/**********************************************************
 * This class creates a gameListener for the newGame button
 **********************************************************/
  private static class GameListener implements ActionListener {
    
 /***************************************************
  * This method takes care of the newGame button
  * If the button is pressed, the current puzzle 
  * is dequeued and shown
  * 
  * @param  event  when the newGame button is pressed 
  ***************************************************/
    public void actionPerformed (ActionEvent event) {  
      if (event.getSource() == newGame) {
        if (currentLevel == "e") {
          currentPuzzle = dataE.dequeue();
          dataE.enqueue(currentPuzzle);
        }
        else if (currentLevel == "m") {
          currentPuzzle = dataM.dequeue();
          dataM.enqueue(currentPuzzle);
        }
        else {
          currentPuzzle = dataH.dequeue();
          dataH.enqueue(currentPuzzle);
        }
        // remove current sudokupanel, replace with a new one with a new puzzle
        frame.getContentPane().removeAll();
        panel2 = new SudokuPanel(currentPuzzle, switchUser, newGame);
        frame.getContentPane().add(panel2);
        frame.pack();
        frame.setVisible(true);
      }
    }
  }
}