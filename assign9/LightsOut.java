package assign9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is a Lights Out puzzle. The object of the game is to turn off all of the lights on the board.
 * User clicks a light to toggle it on/off, but each click also toggles the surrounding lights to the north, south, east and west.
 * 
 * @author Christina Foreman
 */
public class LightsOut extends JFrame implements ActionListener{
	
	public static void main(String[] args) {
		// create LightsOut object and make visible
		LightsOut l = new LightsOut();
		l.setVisible(true);
	}
	
	/**
	 * LightsOut instance variables.
	 */
	private LightButton[][] buttons;
	private JButton randomizeButton;
	private JButton manualButton;
	private JButton helpButton;
	
	private boolean manualMode;
	
	private JLabel winLabel;
	private int winRecord;

	private int moves;
	private JLabel movesLabel;
	
	private int bestMoves;
	private JLabel bestMovesLabel;
	
	/**
	 * LightsOut constructor
	 * Container created to hold 25 buttons with JPanel in a grid layout.
	 * 25 buttons created, randomly set to on or off, buttons added to container, listener added to buttons.
	 */
	public LightsOut () {
		// Exit on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Randomize button
		randomizeButton = new JButton("RANDOMIZE");
		randomizeButton.addActionListener(this);
		
		//Enter Manual Setup
		manualButton = new JButton("Enter Manual Setup");
		manualButton.addActionListener(this);
		manualMode = false;
		
		// Help Button
		helpButton = new JButton("?");
		helpButton.addActionListener(this);
		
		winLabel = new JLabel("Let's play LIGHTS OUT!", JLabel.CENTER);
		winRecord = 0;
		
		moves = 0;
		movesLabel = new JLabel("Moves: " + moves, JLabel.CENTER);
		
		bestMoves = 0;
		bestMovesLabel = new JLabel("Best Moves score: ", JLabel.CENTER);

		
		// Container to hold and organize the 25 buttons.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 5));
		
		// 25 buttons in a 2D array, 5x5
		// [0,0] [0,1] [0,2] [0,3] [0,4]
		// [1,0] [1,1] [1,2] [1,3] [1,4]
		// [2,0] [2,1] [2,2] [2,3] [2,4]
		// [3,0] [3,1] [3,2] [3,3] [3,4]
		// [4,0] [4,1] [4,2] [4,3] [4,4]
		
		buttons = new LightButton[5][5];
		
		// Construct buttons (which are randomly set to on or off when created).
		for(int i = 0; i < 5; i++) {	//row
			for(int j = 0; j < 5; j++) {	//column
				buttons[i][j] = new LightButton(i, j);
				// Add buttons to container
				panel.add(buttons[i][j]);
				// Listener for buttons
				buttons[i][j].addActionListener(this);
			}
		} // close your stupid loops...stop wasting time.
		
		// Set up the JFrame.
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1,1));
		panel3.add(helpButton);
		panel3.add(randomizeButton);
		panel3.add(manualButton);

		JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayout(1, 1));
		panel4.add(winLabel);
		panel4.add(bestMovesLabel);
		panel4.add(movesLabel);
		
		panel2.add(panel, BorderLayout.CENTER);
		panel2.add(panel3, BorderLayout.SOUTH);
		panel2.add(panel4, BorderLayout.NORTH);
		
		panel = panel2;   // to avoid changing code below the sets up JFrame
			
		setTitle("Lights Out");
		setContentPane(panel);
		setPreferredSize(new Dimension(600, 600));
		pack();
		}
	
	/**
	 * Events for helpButton, randomizeButton, manualButton, and LightButtons.
	 */
	public void actionPerformed(ActionEvent e) {
		// helpButton opens a MessageDialog to describe the game and offers solutions.
		if (e.getSource() == helpButton) {
			JOptionPane.showMessageDialog(null, "Goal: turn OFF all lights on the board. Turning a light on/off \n"
					+ "\t will toggle the lights surrounding it to the north, south, east and west. \n \n "
					+ "Things to keep in mind: \n"
					+ "\t Toggling a light twice is equivalent to not toggling at all, yet it does affect number of moves. \n"
					+ "\t The order in which the lights are toggled does not effect the end result. \n \n"
					+ "Hint: Turn off all of the lights in the top row, proceed \n"
					+ "\t to the second row, third, fourth. Only the bottom row has lights on. Depending on the \n"
					+ "\t pattern in this bottom row, you will toggle (click) the buttons in the top row in the corresponding \n"
					+ "\t pattern, then proceed to turn off all the lights row by row as before. \n \n"
					+ "[  BOTTOM   ] \t \t [    TOP    ] \n"
					+ "[ - - O O O ] \t \t [ - - - X - ] \n"
					+ "[ - O - O - ] \t \t [ - X - - X ] \n"
					+ "[ - O O - O ] \t \t [ X - - - - ] \n"
					+ "[ O - - - O ] \t \t [ - - - X X ] \n"
					+ "[ O - O O - ] \t \t [ - - - - X ] \n"
					+ "[ O O - O O ] \t \t [ - - X - - ]\n"
					+ "[ O O O - - ] \t \t [ - X - - - ]"
					, "Need help?", JOptionPane.PLAIN_MESSAGE);
		}
		
		// Randomize board
		else if (e.getSource() == randomizeButton) {
			enableButtons();
			// Reset moves to 0
			moves = 0;
			movesLabel.setText("Moves: " + moves);
			
			for(int i = 0; i < 5; i++) {	//row
				for(int j = 0; j < 5; j++) {	//column
					buttons[i][j].randomize();
				}
			}
			manualMode = false;
			manualButton.setText("Enter Manual Mode");
		}
		
		// Manual mode: toggles enter/exit manual set up
		else if (e.getSource() == manualButton) {
			enableButtons();
			// Reset moves to 0
			moves = 0;
			movesLabel.setText("Moves: " + moves);
			
			if (manualMode == true) {
				manualMode = false;
				manualButton.setText("Enter Manual Mode");
			}
			else if (manualMode == false) {
				manualMode = true;
				manualButton.setText("Exit Manual Mode");
			}
		}	
		
		// LightButton toggle
		else {
			LightButton button = (LightButton) e.getSource();
				if (e.getSource() instanceof LightButton) {
					((LightButton) e.getSource()).toggle();
					if (manualMode==false)
						moves++;
				}
			// Update movesLabel after a LightButton has been clicked
			movesLabel.setText("Moves: " + moves);
		
			if (manualMode == false) {
				for(int i = 0; i < 5; i++) {	//row
					for(int j = 0; j < 5; j++) {	//column
						// Button's row - adjacent button's row = 0
						// Absolute value of button's column - adjacent button's column = 1
						if ((button.getRow() - i == 0) && (Math.abs(button.getColumn() - j) == 1)) { 
							buttons[i][j].toggle();
							if (gameWon()) // if all buttons are turned off
								winGame();
						}
						// Button's column - adjacent button's column = 0; 
						// Absolute value of button's row - adjacent button's row = 1;
						if ((button.getColumn() - j == 0) && (Math.abs(button.getRow() - i) == 1)) {
							buttons[i][j].toggle();
							if (gameWon())  // if all buttons are turned off
								winGame();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Cycles through every button, checking its state. If all buttons are off, gameWon returns true and winRecord count is updated.
	 * @return true if all buttons are off, false if there are lights on.
	 */
	public boolean gameWon() {
		int countOff = 0;
		for(int i = 0; i < 5; i++) {	//row
			for(int j = 0; j < 5; j++) {	//column
				if (!buttons[i][j].isLightOn())
					countOff++;
			}
		}
		if (countOff == 25) {
			winRecord++;
			return true;
		}
		return false;
	}
	
	/**
	 * Updates winLabel with winRecord, disables LightButtons, updates bestMovesLabel with bestMoves. 
	 * Sets focus to the randomize button.
	 */
	public void winGame() {
			winLabel.setText("Win record: " + winRecord);
			disableButtons();
			recordMoves();
			bestMovesLabel.setText("Best Moves score: " + bestMoves);
			// MessageDialog informs player number of the number of moves it took to win.
			JOptionPane.showMessageDialog(null, "You won with " + moves + " moves!", "WINNER!", JOptionPane.PLAIN_MESSAGE);
			randomizeButton.requestFocusInWindow();
	}
	
	/**
	 * This method keeps compares the player's lowest moves score against the number of moves that they just won the game with.
	 * @param player's 
	 * @return player's lowest "moves" score after winning a game
	 */
	public int recordMoves() {
		if (winRecord < 2)
			bestMoves = moves;
		else if (moves < bestMoves) {
			bestMoves = moves;
		}
		return bestMoves;
	}
	
	/**
	 * Cycles through each button and disables it.
	 */
	public void disableButtons() {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				buttons[i][j].setEnabled(false);
			}
		}
	}
	
	/**
	 * Cycles through each button and enables it.
	 */
	public void enableButtons() {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				buttons[i][j].setEnabled(true);
			}
		}
	}
	
}

// about 133 lines