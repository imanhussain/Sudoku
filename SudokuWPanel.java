/*SudokuWPanel.java
 CS230 Final Project
 Written by: Iman Hussain, Alice Pan and Nicole Gates
 Modified date: 5/9/2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/********************************************************
 * This class creates the Welcome panel in the GUI.
 *******************************************************/
public class SudokuWPanel extends JPanel{
  private JLabel welcome, user;
  private JPanel panel, subPanel, userp, levelp, startp;
  private JTextField username;
  private JButton easy, medium, hard, start;
  private static String userc, level;
  
  /*******************************************************
   * Constructor
   * Creates the Welcome panel, which allows users to
   * enter their username, choose the difficulty of the
   * puzzle they want to play, and generate a puzzle to
   * start the game.
   * 
   * @param  startGame  Jbutton from the GUI driver class,
   *                    allows the Welcome panel to switch
   *                    to the Sudoku panel when pressed
   *******************************************************/
  public SudokuWPanel(JButton startGame) {
    start = startGame;
    
    // Set overall layout
    panel = new JPanel();
    panel.setBackground(new Color(223, 195, 207));
    panel.setLayout(new GridLayout(0,1));
    
    // Create and add the welcome words
    String s = ("<html>Welcome!" +
                "<br>Please enter your username " +
                "and choose the level of difficulty. " +
                "<br>ENJOY the puzzle!" + 
                "<br>Remember to hit Enter button after entering the username." +
                "<br>Remember also to hit Enter button after entering a number.</html>");
    welcome = new JLabel(s);
    panel.add(welcome);
    
    // Create the data entry part
    subPanel = new JPanel();
    subPanel.setBackground(new Color(223, 195, 207));
    subPanel.setLayout(new GridLayout(0,3));
    
    // Create and add the username entry part
    userp = new JPanel();
    userp.setBackground(new Color(223, 195, 207));
    userp.setLayout(new FlowLayout());
    username = new JTextField(10);
    username.addActionListener(new TextListener());
    String usern = "Username: ";
    user = new JLabel(usern);
    userp.add(user);
    userp.add(username);
    subPanel.add(userp);
    
    // Create and add the level entry part
    levelp = new JPanel();
    levelp.setBackground(new Color(223, 195, 207));
    levelp.setLayout(new BoxLayout(levelp, BoxLayout.Y_AXIS));
    easy = new JButton("Easy");
    medium = new JButton("Medium");
    hard = new JButton("Hard");
    easy.setAlignmentX(Component.CENTER_ALIGNMENT);
    medium.setAlignmentX(Component.CENTER_ALIGNMENT);
    hard.setAlignmentX(Component.CENTER_ALIGNMENT);
    easy.addActionListener(new ButtonListener());
    medium.addActionListener(new ButtonListener());
    hard.addActionListener(new ButtonListener());
    levelp.add(easy);
    levelp.add(medium);
    levelp.add(hard);
    subPanel.add(levelp);
    
    // Create the start game panel
    startp = new JPanel();
    startp.setBackground(new Color(223, 195, 207));
    startp.setLayout(new FlowLayout());
    startp.add(start);
    subPanel.add(startp);
    
    // Add subPanel to the overall panel
    panel.add(subPanel);
    
    add(panel);
    
  }
  
  /****************************************************
   * This method gets the username the user has entered
   * into the textfield
   * 
   * @return the username entered
   ****************************************************/
  public static String getCurrentUser() {
    return userc;
  }
  
  
  /****************************************************
   * This method gets the level difficulty the user has
   * chosen by clicking on the appropriate button
   * 
   * @return a String containing the difficulty level
   *         chosen
   ****************************************************/
  public static String getLevel() {
    return level;
  }
  
  /*****************************************************
   * This class implements the ActionListener for the
   * difficulty buttons in the Welcome panel.
   *****************************************************/
  private class ButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      //gets the level of difficulty
      if (event.getSource() == easy)
        level = "e";
      else if (event.getSource() == medium)
        level = "m";
      else if (event.getSource() == hard)
        level = "h";
    }
  }
  
  /*******************************************************
   * This class implements the ActionListener that reads
   * in the username the user enters in the textfield
   *******************************************************/  
  private class TextListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      //gets the user information
      userc = username.getText();
    }
  }
}