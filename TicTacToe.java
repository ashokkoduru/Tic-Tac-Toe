/*
 Author    : Ashok Koduru
 Date      : 13-06-2013
 Algorithm : Ad-hoc
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe implements ActionListener	{
	final String VERSION = "1.0";
	//Setting up ALL the variables
	JFrame window = new JFrame("Tic-Tac-Toe " + VERSION);

	JMenuBar MainMenu = new JMenuBar();
	JMenuItem 	menuNewGame = new JMenuItem("New Game"),	
				menuInstruction = new JMenuItem("Instructions"),
				menuExit = new JMenuItem("Exit"), 
				menuAbout = new JMenuItem("About");

	JButton 	button1v1 = new JButton("Player vs Player"),
				button1vCPU = new JButton("Player vs Computer"),
				buttonQuit = new JButton("Quit"),
				buttonSetName = new JButton("Set Player Names"),
				buttonContinue = new JButton("Continue..."),
				buttonTryAgain = new JButton("Try Again?");
	JButton buttonEmpty[] = new JButton[10];

	JPanel 	panelNewGame = new JPanel(),
			panelMenu = new JPanel(),
			panelMain = new JPanel(),
			panelTop = new JPanel(),
			panelBottom = new JPanel(),
			panelQuitNTryAgain = new JPanel(),
			panelPlayingField = new JPanel();

	JLabel 	labelTitle = new JLabel("Tic-Tac-Toe"),
			labelTurn = new JLabel(),
			labelStatus = new JLabel("", JLabel.CENTER),
			labelMode = new JLabel("", JLabel.LEFT);
	JTextArea txtMessage = new JTextArea();

	final int winCombo[][] = new int[][]	{
		{1, 2, 3}, 			{1, 4, 7}, 		{1, 5, 9},
			{4, 5, 6}, 			{2, 5, 8}, 		{3, 5, 7},
			{7, 8, 9}, 			{3, 6, 9}
		/*Horizontal Wins*/	/*Vertical Wins*/ /*Diagonal Wins*/
	};
	final int X = 535, Y = 342,
		  mainColorR = 175, mainColorG = 238, mainColorB = 238,
		  buttonColorR = 70, buttonColorG = 70, buttonColorB = 70;
	Color clrbuttonWonColor = new Color(190, 190, 190);
	int 	turn = 1,
			player1Won = 0, player2Won = 0,
			wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1,
			option;
	boolean 	inGame = false,
				CPUGame = false,
				win = false;
	String 	message,
			Player1 = "Player 1", Player2 = "Player 2",
			tempPlayer2 = "Player 2";


	public TicTacToe()	{	
		window.setSize(X, Y);
		window.setLocation(350, 260);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER));

		panelNewGame.setBackground(new Color(mainColorR - 50, mainColorG - 50, mainColorB- 50));
		panelMenu.setBackground(new Color((mainColorR - 50), (mainColorG - 50), (mainColorB- 50)));
		panelMain.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		panelTop.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		panelBottom.setBackground(new Color(mainColorR, mainColorG, mainColorB));

		//Setting up Panel QuitNTryAgain
		panelQuitNTryAgain.setLayout(new GridLayout(1, 2, 2, 2));
		panelQuitNTryAgain.add(buttonTryAgain);
		panelQuitNTryAgain.add(buttonQuit);

		//Adding menu items to menu bar
		MainMenu.add(menuNewGame);
		MainMenu.add(menuInstruction);
		MainMenu.add(menuAbout);
		MainMenu.add(menuExit);//	Menu Bar is Complete

		//Adding buttons to NewGame panel
		panelNewGame.setLayout(new GridLayout(4, 1, 2, 10));
		panelNewGame.add(buttonContinue);
		panelNewGame.add(button1v1);
		panelNewGame.add(button1vCPU);
		panelNewGame.add(buttonSetName);

		//Setting Button propertied
		buttonTryAgain.setEnabled(false);
		buttonContinue.setEnabled(false);

		//Setting txtMessage Properties
		txtMessage.setBackground(new Color(mainColorR-30, mainColorG-30, mainColorB-30));
		txtMessage.setForeground(Color.white);
		txtMessage.setEditable(false);

		//Adding Action Listener to all the Buttons and Menu Items
		menuNewGame.addActionListener(this);
		menuExit.addActionListener(this);
		menuInstruction.addActionListener(this);
		menuAbout.addActionListener(this);
		button1v1.addActionListener(this);
		button1vCPU.addActionListener(this);
		buttonQuit.addActionListener(this);
		buttonSetName.addActionListener(this);
		buttonContinue.addActionListener(this);
		buttonTryAgain.addActionListener(this);

		//Setting up the playing field
		panelPlayingField.setLayout(new GridLayout(3, 3, 2, 2));
		panelPlayingField.setBackground(Color.black);
		for(int i=1; i<=9; i++)	{
			buttonEmpty[i] = new JButton();
			buttonEmpty[i].setBackground(new Color(buttonColorR, buttonColorG, buttonColorB));
			buttonEmpty[i].addActionListener(this);
			panelPlayingField.add(buttonEmpty[i]);//	Playing Field is Compelte
		}

		//Adding everything needed to panelMenu and panelMain
		labelMode.setForeground(Color.white);
		panelMenu.add(labelMode);
		panelMenu.add(MainMenu);
		panelMain.add(labelTitle);

		//Adding to window and Showing window
		window.add(panelMenu, BorderLayout.NORTH);
		window.add(panelMain, BorderLayout.CENTER);
		window.setVisible(true);
	}

	public static void main(String[] args)	{
		new TicTacToe();
	}



	 /*
		GAME STARTS
	 */
	public void showGame()	{	
		clearPanelSouth();
		panelMain.setLayout(new BorderLayout());
		panelTop.setLayout(new BorderLayout());
		panelBottom.setLayout(new BorderLayout());
		panelTop.add(panelPlayingField);
		panelBottom.add(labelTurn, BorderLayout.WEST);
		panelBottom.add(labelStatus, BorderLayout.CENTER);
		panelBottom.add(panelQuitNTryAgain, BorderLayout.EAST);
		panelMain.add(panelTop, BorderLayout.CENTER);
		panelMain.add(panelBottom, BorderLayout.SOUTH);
		panelPlayingField.requestFocus();
		inGame = true;
		checkTurn();
		checkWinStatus();
	}
	public void newGame()	{
		buttonEmpty[wonNumber1].setBackground(new Color(buttonColorR, buttonColorG, buttonColorB));
		buttonEmpty[wonNumber2].setBackground(new Color(buttonColorR, buttonColorG, buttonColorB));
		buttonEmpty[wonNumber3].setBackground(new Color(buttonColorR, buttonColorG, buttonColorB));
		for(int i=1; i<10; i++)	{
			buttonEmpty[i].setText("");
			buttonEmpty[i].setEnabled(true);
		}
		turn = 1;
		win = false;
		showGame();
	}
		
	public void quit()	{
		inGame = false;
		labelMode.setText("");
		buttonContinue.setEnabled(false);
		clearPanelSouth();
		setDefaultLayout();
		panelTop.add(panelNewGame);
		panelMain.add(panelTop);
	}
		
	public void checkWin()	{	//	checks if there are 3 symbols in a row vertically, diagonally, or horizontally.
		//	then shows a message and disables buttons. If the game is over then it asks
		//	if you want to play again.
		for(int i=0; i<8; i++)	{
			if(
					!buttonEmpty[winCombo[i][0]].getText().equals("") &&
					buttonEmpty[winCombo[i][0]].getText().equals(buttonEmpty[winCombo[i][1]].getText()) &&
					//								if {1 == 2 && 2 == 3}
					buttonEmpty[winCombo[i][1]].getText().equals(buttonEmpty[winCombo[i][2]].getText()))	{
	
				win = true;
				wonNumber1 = winCombo[i][0];
				wonNumber2 = winCombo[i][1];
				wonNumber3 = winCombo[i][2];
				buttonEmpty[wonNumber1].setBackground(clrbuttonWonColor);
				buttonEmpty[wonNumber2].setBackground(clrbuttonWonColor);
				buttonEmpty[wonNumber3].setBackground(clrbuttonWonColor);
				break;
			}
		}
		if(win || (!win && turn>9))	{
			if(win)	{
				if(buttonEmpty[wonNumber1].getText().equals("X"))	{
					message = Player1 + " has won";
					player1Won++;
				}
				else	{
					message = Player2 + " has won";
					player2Won++;
				}
			}	else if(!win && turn>9)
				message = "Both players have tied!\nBetter luck next time.";
			showMessage(message);
			for(int i=1; i<=9; i++)	{
				buttonEmpty[i].setEnabled(false);
			}
			buttonTryAgain.setEnabled(true);
			checkWinStatus();
		} else
			checkTurn();
	}
	public void AI()	{
		int computerButton;
		if(turn <= 9)	{
			turn++;
			computerButton = CPU.doMove(
					buttonEmpty[1], buttonEmpty[2], buttonEmpty[3],
					buttonEmpty[4], buttonEmpty[5], buttonEmpty[6],
					buttonEmpty[7], buttonEmpty[8], buttonEmpty[9]);
			if(computerButton == 0)
				Random();
			else {
				buttonEmpty[computerButton].setText("O");
				buttonEmpty[computerButton].setEnabled(false);
			}
			checkWin();
		}
	}
	public void Random()	{
		int random;
		if(turn <= 9)	{
			random = 0;
			while(random == 0)	{
				random = (int)(Math.random() * 10);
			}
			if(CPU.doRandomMove(buttonEmpty[random]))	{
				buttonEmpty[random].setText("O");
				buttonEmpty[random].setEnabled(false);
			} else {
				Random();
			}
		}
	}
	public void checkTurn()	{
		String whoTurn;
		if(!(turn % 2 == 0))	{
			whoTurn = Player1 + " [X]";
		}	else	{
			whoTurn = Player2 + " [O]";
		}
		labelTurn.setText("Turn: " + whoTurn);
	}
	public void askUserForPlayerNames()	{
		String temp;
		boolean tempIsValid = false;
		temp = getInput("Enter player 1 name:", Player1);
		if(temp == null)	{}
		else if(temp.equals(""))
			showMessage("Invalid Name!");
		else if(temp.equals(Player2))	{
			option = askMessage("Player 1 name matches Player 2's\nDo you want to continue?", "Name Match", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION)
				tempIsValid = true;
		} else if(temp != null)	{
			tempIsValid = true;
		}
		if(tempIsValid)	{
			Player1 = temp;
			tempIsValid = false;
		}

		temp = getInput("Enter player 2 name:", Player2);
		if(temp == null)	{}
		else if(temp.equals(""))
			showMessage("Invalid Name!");
		else if(temp.equals(Player1))	{
			option = askMessage("Player 2 name matches Player 1's\nDo you want to continue?", "Name Match", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION)
				tempIsValid = true;
		} else if(temp != null)	{
			tempIsValid = true;
		}
		if(tempIsValid)	{
			Player2 = temp;
			tempPlayer2 = temp;
			tempIsValid = false;
		}
	}
	public void setDefaultLayout()	{
		panelMain.setLayout(new GridLayout(2, 1, 2, 5));
		panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
	}
	public void checkWinStatus()	{
		labelStatus.setText(Player1 + ": " + player1Won + " | " + Player2 + ": " + player2Won);	
	}
	public int askMessage(String msg, String tle, int op)	{
		return JOptionPane.showConfirmDialog(null, msg, tle, op);
	}
	public String getInput(String msg, String setText)	{
		return JOptionPane.showInputDialog(null, msg, setText);
	}
	public void showMessage(String msg)	{
		JOptionPane.showMessageDialog(null, msg);
	}
	public void clearPanelSouth()	{	//Removes all the possible panels 
		panelMain.remove(labelTitle);
		panelMain.remove(panelTop);
		panelMain.remove(panelBottom);
		panelTop.remove(panelNewGame);
		panelTop.remove(txtMessage);
		panelTop.remove(panelPlayingField);
		panelBottom.remove(labelTurn);
		panelBottom.remove(panelQuitNTryAgain);
	}
	public void actionPerformed(ActionEvent click)	{
		Object source = click.getSource();
		for(int i=1; i<=9; i++)	{
			if(source == buttonEmpty[i] && turn <	10)	{
				if(!(turn % 2 == 0))
					buttonEmpty[i].setText("X");
				else
					buttonEmpty[i].setText("O");
				buttonEmpty[i].setEnabled(false);
				panelPlayingField.requestFocus();
				turn++;
				checkWin();
				if(CPUGame && win == false)
					AI();
			}
		}
		if(source == menuNewGame || source == menuInstruction || source == menuAbout)	{
			clearPanelSouth();
			setDefaultLayout();

			if(source == menuNewGame)	{//NewGame
				panelTop.add(panelNewGame);
			}	
			else if(source == menuInstruction || source == menuAbout)	{
				if(source == menuInstruction)	{// Instructions
					message = 	"Instructions:\n\n" +
						"Your goal is to be the first player to get 3 X's or O's in a\n" +
						"row. (horizontally, diagonally, or vertically)\n" +
						Player1 + ": X\n" +
						Player2 + ": O\n";
				} else	{//About
					message = 	"About:\n\n" +
						"Title: Tic-Tac-Toe\n" +
						"Creator: Ashok Koduru\n" +
						"Version: " + VERSION + "\n";
				}
				txtMessage.setText(message);
				panelTop.add(txtMessage);
			}	
			panelMain.add(panelTop);
		}
		else if(source == button1v1 || source == button1vCPU)	{
			if(inGame)	{
				option = askMessage("If you start a new game," +
						"your current game will be lost..." + "\n" +
						"Are you sure you want to continue?", 
						"Quit Game?" ,JOptionPane.YES_NO_OPTION
						);
				if(option == JOptionPane.YES_OPTION)
					inGame = false;
			}
			if(!inGame)	{
				buttonContinue.setEnabled(true);
				if(source == button1v1)	{// 1 v 1 Game
					Player2 = tempPlayer2;
					player1Won = 0;
					player2Won = 0;
					labelMode.setText("1 v 1");
					CPUGame = false;	
					newGame();
				} else	{// 1 v CPU Game
					Player2 = "Computer";
					player1Won = 0;
					player2Won = 0;
					labelMode.setText("1 v CPU");
					CPUGame = true;
					newGame();
				}
			}
		}
		else if(source == buttonContinue)	{
			checkTurn();
			showGame();
		}
		else if(source == buttonSetName)	{
			askUserForPlayerNames();
		}
		else if(source == menuExit)	{
			option = askMessage("Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION)
				System.exit(0);
		}
		else if(source == buttonTryAgain)	{
			newGame();
			buttonTryAgain.setEnabled(false);
		}
		else if(source == buttonQuit)	{
			quit();
		}
		panelMain.setVisible(false);
		panelMain.setVisible(true);
	}

	static class CPU	{
		static int doMove(JButton button1, JButton button2, JButton button3, JButton button4, JButton button5, JButton button6, JButton button7, JButton button8, JButton button9)	{
			if(button1.getText().equals("O") && button2.getText().equals("O") && button3.getText().equals(""))
				return 3;
			else if(button4.getText().equals("O") && button5.getText().equals("O") && button6.getText().equals(""))
				return 6;
			else if(button7.getText().equals("O") && button8.getText().equals("O") && button9.getText().equals(""))
				return 9;

			else if(button2.getText().equals("O") && button3.getText().equals("O") && button1.getText().equals(""))
				return 1;
			else if(button5.getText().equals("O") && button6.getText().equals("O") && button4.getText().equals(""))
				return 4;
			else if(button8.getText().equals("O") && button9.getText().equals("O") && button7.getText().equals(""))
				return 7;

			else if(button1.getText().equals("O") && button3.getText().equals("O") && button2.getText().equals(""))
				return 2;
			else if(button4.getText().equals("O") && button6.getText().equals("O") && button5.getText().equals(""))
				return 5;
			else if(button7.getText().equals("O") && button9.getText().equals("O") && button8.getText().equals(""))
				return 8;

			else if(button1.getText().equals("O") && button4.getText().equals("O") && button7.getText().equals(""))
				return 7;
			else if(button2.getText().equals("O") && button5.getText().equals("O") && button8.getText().equals(""))
				return 8;
			else if(button3.getText().equals("O") && button6.getText().equals("O") && button9.getText().equals(""))
				return 9;

			else if(button4.getText().equals("O") && button7.getText().equals("O") && button1.getText().equals(""))
				return 1;
			else if(button5.getText().equals("O") && button8.getText().equals("O") && button2.getText().equals(""))
				return 2;
			else if(button6.getText().equals("O") && button9.getText().equals("O") && button3.getText().equals(""))
				return 3;

			else if(button1.getText().equals("O") && button7.getText().equals("O") && button4.getText().equals(""))
				return 4;
			else if(button2.getText().equals("O") && button8.getText().equals("O") && button5.getText().equals(""))
				return 5;
			else if(button3.getText().equals("O") && button9.getText().equals("O") && button6.getText().equals(""))
				return 6;

			else if(button1.getText().equals("O") && button5.getText().equals("O") && button9.getText().equals(""))
				return 9;
			else if(button5.getText().equals("O") && button9.getText().equals("O") && button1.getText().equals(""))
				return 1;
			else if(button1.getText().equals("O") && button9.getText().equals("O") && button5.getText().equals(""))
				return 5;

			else if(button3.getText().equals("O") && button5.getText().equals("O") && button7.getText().equals(""))
				return 7;
			else if(button7.getText().equals("O") && button5.getText().equals("O") && button3.getText().equals(""))
				return 3;
			else if(button7.getText().equals("O") && button3.getText().equals("O") && button5.getText().equals(""))
				return 5;

			else if(button1.getText().equals("X") && button2.getText().equals("X") && button3.getText().equals(""))
				return 3;
			else if(button4.getText().equals("X") && button5.getText().equals("X") && button6.getText().equals(""))
				return 6;
			else if(button7.getText().equals("X") && button8.getText().equals("X") && button9.getText().equals(""))
				return 9;

			else if(button2.getText().equals("X") && button3.getText().equals("X") && button1.getText().equals(""))
				return 1;
			else if(button5.getText().equals("X") && button6.getText().equals("X") && button4.getText().equals(""))
				return 4;
			else if(button8.getText().equals("X") && button9.getText().equals("X") && button7.getText().equals(""))
				return 7;

			else if(button1.getText().equals("X") && button3.getText().equals("X") && button2.getText().equals(""))
				return 2;
			else if(button4.getText().equals("X") && button6.getText().equals("X") && button5.getText().equals(""))
				return 5;
			else if(button7.getText().equals("X") && button9.getText().equals("X") && button8.getText().equals(""))
				return 8;

			else if(button1.getText().equals("X") && button4.getText().equals("X") && button7.getText().equals(""))
				return 7;
			else if(button2.getText().equals("X") && button5.getText().equals("X") && button8.getText().equals(""))
				return 8;
			else if(button3.getText().equals("X") && button6.getText().equals("X") && button9.getText().equals(""))
				return 9;

			else if(button4.getText().equals("X") && button7.getText().equals("X") && button1.getText().equals(""))
				return 1;
			else if(button5.getText().equals("X") && button8.getText().equals("X") && button2.getText().equals(""))
				return 2;
			else if(button6.getText().equals("X") && button9.getText().equals("X") && button3.getText().equals(""))
				return 3;

			else if(button1.getText().equals("X") && button7.getText().equals("X") && button4.getText().equals(""))
				return 4;
			else if(button2.getText().equals("X") && button8.getText().equals("X") && button5.getText().equals(""))
				return 5;
			else if(button3.getText().equals("X") && button9.getText().equals("X") && button6.getText().equals(""))
				return 6;

			else if(button1.getText().equals("X") && button5.getText().equals("X") && button9.getText().equals(""))
				return 9;
			else if(button5.getText().equals("X") && button9.getText().equals("X") && button1.getText().equals(""))
				return 1;
			else if(button1.getText().equals("X") && button9.getText().equals("X") && button5.getText().equals(""))
				return 5;

			else if(button3.getText().equals("X") && button5.getText().equals("X") && button7.getText().equals(""))
				return 7;
			else if(button7.getText().equals("X") && button5.getText().equals("X") && button3.getText().equals(""))
				return 3;
			else if(button7.getText().equals("X") && button3.getText().equals("X") && button5.getText().equals(""))
				return 5;

			else if(button1.getText().equals("X") && button5.getText().equals("O") && button9.getText().equals("X"))
				return 6;

			else if(button3.getText().equals("X") && button5.getText().equals("O") && button7.getText().equals("X")) 
				return 4;

			else if(button5.getText().equals(""))
				return 5;

			else if(button1.getText().equals(""))
				return 1;
			else
				return 0;
		}


		static boolean doRandomMove(JButton button)	{
			if(button.getText().equals("O") || button.getText().equals("X"))
				return false;
			else	{
				return true;
			}
		}
	}
}

