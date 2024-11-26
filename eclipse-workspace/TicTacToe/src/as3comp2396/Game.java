package as3comp2396;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * The {@code Game} class includes logics and GUI for Tic Tac Toe game.
 * 
 * @author shirley430316
 * @version 1.0.0
 */
public class Game implements ActionListener {
	
	private JFrame frame = new JFrame("Tic Tac Toe");
	private JButton submit = new JButton("Submit");
	private JTextField nameTextField = new JTextField(10);
	private SlotButton[] slotButtons;
	private Player player = new Player();
	private Computer computer = new Computer();
	private Board board = new Board();
	private JPanel west_board_panel = new JPanel(new GridLayout(3, 3));
	private EastPanel east_score_panel = new EastPanel();
	private SouthPanel south_panel = new SouthPanel();
	private NorthPanel north_panel = new NorthPanel();
	private int win, lose, draw = 0;
	private int turns = 0;
	private boolean pending = false;
	private int status = 0; // 0 for input name, 1 for beginning, 2 for waiting opponent, 3 for player's turn
	
	/**
	 * Initializes a {@code Game} instance.
	 */
	public Game() {
		
		submit.addActionListener(this);
	    BoxLayout frameLayout = new BoxLayout(frame, BoxLayout.Y_AXIS);
	}
	
	/**
     * The entry point of the application. This method creates a new 
     * instance of the {@code Game} class and starts the game.
     *
     * @param args command-line arguments (not used)
     */
	public static void main(String[] args) {
		Game game = new Game();
		game.go();
	}
	
	/**
     * Sets up the game board and user interface components.
     */
	public void go() {
		
		
		west_board_panel.setSize(new Dimension(300,300));
		
		this.slotButtons = new SlotButton[9];
		for (int i=0; i<9; i++) {
			slotButtons[i] = new SlotButton(i);
			slotButtons[i].setBorder(new LineBorder(Color.BLACK));;
			slotButtons[i].addActionListener(this);
			slotButtons[i].setPreferredSize(new Dimension(150,150));
			west_board_panel.add(slotButtons[i]);
		}
		
		JPanel except_menu = new JPanel();
		except_menu.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		except_menu.add(north_panel,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		except_menu.add(west_board_panel,c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		except_menu.add(east_score_panel,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		except_menu.add(south_panel,c);
		
		frame.setJMenuBar(new MenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(except_menu);
		frame.setSize(650,600);
		frame.setVisible(true);
		
	}
	
	 /**
     * Handles user actions performed on the game interface.
     * <p>
     * This method manages the turns between player and computer, checks
     * and updates game status.
     * 
     * @param e the {@code ActionEvent} triggered by user actions
     */
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		
		if (o == submit) {
			
			String name = nameTextField.getText();
			
			if (!name.isEmpty()) {
				frame.setTitle("Tic Tac Toe - " + name);
				status = 1;
				north_panel.setStatus(status);
				frame.repaint();
				submit.setEnabled(false);
				return;
			}
			else return;
		}
		
		SlotButton sButton = (SlotButton)o;
		
		int slot = sButton.slot;
		if (!pending && status != 0) {
			if (player.makeMove(board, slot)) {
				status = 2;
				north_panel.setStatus(status);
				turns++;

				Font font = new Font("Arial", Font.PLAIN, 40); // Create a Font object with size 20
				sButton.setFont(font);
				sButton.setText("O");
				sButton.setForeground(Color.GREEN);
				west_board_panel.repaint();

				if (player.isWin(board)) {
					win++;
					board = new Board();
					east_score_panel.updates();
					JOptionPane.showMessageDialog(frame,"You win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					reset();
					return;
				}

				else if (turns==9) {
					draw++;
					board = new Board();
					east_score_panel.updates();
					JOptionPane.showMessageDialog(frame,"It's a draw!","Game over", JOptionPane.INFORMATION_MESSAGE);
					reset();
					return;
				}

				else {
					turns++;
					pending = true;

					int compSlot = computer.makeMove(board);
					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							status = 3;
							north_panel.setStatus(status);
							pending = false;
							SlotButton sButton = new SlotButton(0);
							sButton = slotButtons[compSlot];
							sButton.setFont(font);
							sButton.setText("X");
							sButton.setForeground(Color.RED);
							west_board_panel.repaint();
		
							if (computer.isWin(board)) {
								lose ++;
								board = new Board();
								east_score_panel.updates();
								JOptionPane.showMessageDialog(frame,"Computer win!","Game over", JOptionPane.INFORMATION_MESSAGE);
								reset();
								return;
							}
						}
					};
					Timer timer = new Timer(2000, taskPerformer);
					timer.setRepeats(false);
					timer.start();
					

				}
			}

			else {
				status = 4;
				north_panel.setStatus(status);
				
			}
		}
	}
	
	/**
	 * Resets the board and updates game status.
	 */
	private void reset() {
		ActionListener clear = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				for (int i=0; i<9; i++) {
					slotButtons[i].setText("");
				}
			}
		};
		Timer timer = new Timer(1000, clear);
		timer.setRepeats(false);
		timer.start();
		turns = 0;
		status = 1;
		north_panel.setStatus(status);
		
	}
	

	
	/**
	 * The {@code SlotButton} class extends {@code JButton}.
	 * Add the {@code slot} property.
	 */
	public class SlotButton extends JButton{
		
		private int slot;
		
		/**
		 * Initializes a {@code SlotButton} instance.
		 * @param s slot index
		 */
		public SlotButton(int s) {
			slot = s;
		}
	}
	
	/**
	 * The {@code MenuBar} class extends {@code JMenuBar} class.
	 * Designs the menu bar for the game.
	 */
	public class MenuBar extends JMenuBar {
		
		/**
		 * Initializes a {@code MenuBar} instance.
		 */
		public MenuBar() {
			
			super();
			this.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JMenu control_menu = new JMenu("Control");
			JMenu instruction_menu = new JMenu("Instruction");
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			JMenuItem instruction = new JMenuItem("Instruction");
			instruction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String msg = "Some information about the game:\n"
							+ "- ﻿﻿The move is not occupied by any mark.\n"
							+ "- ﻿﻿The move is made in the player's turn.\n"
							+ "- ﻿﻿The move is made within the 3 x 3 board.\n"
							+ "The game would continue and switch among the player and the computer until it reaches either one of the following conditions:\n"
							+ "- ﻿﻿Player wins.\n"
							+ "- ﻿﻿Computer wins.\n"
							+ "- ﻿﻿Draw.";
					JOptionPane.showMessageDialog(frame,msg, "Instruction", JOptionPane.INFORMATION_MESSAGE);
				}
			});


			control_menu.add(exit);
			instruction_menu.add(instruction);
			this.add(control_menu);
			this.add(instruction_menu);
		}
	}
	

	/**
	 * The {@code SouthPanel} class extends {@code JPanel} class.
	 * <p>
	 * Designs the south panel, consisting name submission area 
	 * and the current time.
	 */
	public class SouthPanel extends JPanel {

		JLabel timeLabel = new JLabel();
		
		/**
		 * Initializes a {@code SouthPanel} instance.
		 */
		public SouthPanel() {
			
			super();
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel namePanel = new JPanel();
			namePanel.add(new JLabel("Enter your name: "));
			namePanel.add(nameTextField);
			namePanel.add(submit);
	        
			updateTime();
	        // Create a Timer to update the time label every second
	        Timer timer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                updateTime();
	            }
	        });
	        timer.start();
	        
	        this.add(namePanel);
	        this.add(timeLabel);
		}
		
		/**
		 * This method update {@code timeLabel}.
		 */
		private void updateTime() {
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        Date now = new Date();
	        String timeStr = sdf.format(now);
	        timeLabel.setText("Current Time: " + timeStr);
	    }

	}
	
	/**
	 * The {@code NorthPanel} class extends {@code JPanel} class.
	 * <p>
	 * Present player guides based on each game status.
	 */
	public class NorthPanel extends JPanel {
		
		private JLabel label = new JLabel("Enter your player name...");
		
		/**
		 * Initializes a {@code NorthPanel} instance.
		 */
		public NorthPanel() {
			
			label.setFont(new Font("Arial",Font.BOLD, 15));
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			this.add(label);
		}
		
		/**
		 * This method sets different status and updates player guides.
		 * 
		 * @param status 
		 */
		public void setStatus(int status) {
			
			if (status == 0) {
				label.setText("Enter your player name...");
			}
			else if (status == 1) {
				label.setText("Welcome " + nameTextField.getText());
			}
			else if (status == 2) {
				label.setText("Valid move. Waiting for your opponent...");
			}
			else if (status == 3) {
				label.setText("Your opponent has moved, now is your turn.");
			}
			else if (status == 4) {
				label.setText("The slot is occupied. Choose another!");
			}
		}
		
	}
	
	/**
	 * The {@code EastPanel} class extends {@code JPanel} class.
	 * <p>
	 * Show scores of the player and the computer.
	 */
	public class EastPanel extends JPanel {
		
		JPanel bottom_panel = new JPanel();
		
		/**
		 * Initializes a {@code EastPanel} instance.
		 */
		public EastPanel() {
			
		    super();
		    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		    
		    bottom_panel.setLayout(new GridBagLayout());
		    
		    GridBagConstraints c = new GridBagConstraints();
		    
		    c.gridx = 0;
		    c.gridy = 0;
		    c.gridwidth = 3;
		    c.ipady = 100;
		    c.ipadx = 30;
		    
		    JPanel high = new JPanel(new BorderLayout());
		    WinLabel win = new WinLabel();
		    high.add(win, BorderLayout.EAST);
		    bottom_panel.add(high,c);
		    
		    c.gridy = 1;
		    JPanel mid = new JPanel(new BorderLayout());
		    LoseLabel lose = new LoseLabel();
		    mid.add(new LoseLabel(),BorderLayout.EAST);
		    bottom_panel.add(mid,c);
		    
		    c.gridy = 2;
		    JPanel low = new JPanel(new BorderLayout());
		    DrawLabel draw = new DrawLabel();
		    low.add(new DrawLabel(),BorderLayout.EAST);
		    bottom_panel.add(low,c);
		    
		    JLabel result = new JLabel("Result");
		    result.setFont(new Font("Arial", Font.BOLD, 15));
		    this.add(result, BorderLayout.NORTH);
		    this.add(bottom_panel);
		}
		
		/**
		 * This method updates {@code EastPanel} by current scores.
		 */
		public void updates() {
			for (int i=0; i<3; i++) {
				JPanel p = (JPanel)bottom_panel.getComponent(i);
				p.getComponent(0).repaint();
			}
			bottom_panel.repaint();
			this.repaint();
		}
	}
	
	/**
	 * The {@code WinLabel} class extends {@code JLabel}.
	 * It shows the number of games that the player has won.
	 */
	public class WinLabel extends JLabel {
		
		/**
		 * Initializes a {@code WinLabel} instance.
		 */
		public WinLabel() {
			super("Player wins: 0");
		}
		
		/**
		 * This method repaints the label.
		 */
		public void repaint() {
			this.setText("Player wins: " + win);
		}
	}
	
	/**
	 * The {@code LoseLabel} class extends {@code JLabel}.
	 * It shows the number of games that the computer has won.
	 */
	public class LoseLabel extends JLabel {
		
		/**
		 * Initializes a {@code LoseLabel} instance.
		 */
		public LoseLabel() {
			super("Computer wins: 0");
		}
		
		/**
		 * This method repaints the label.
		 */
		public void repaint() {
			this.setText("Computer wins: " + lose);
		}
	}
	
	/**
	 * The {@code DrawLabel} class extends {@code JLabel}.
	 * It shows the number of games that has reached a draw.
	 */
	public class DrawLabel extends JLabel {
		
		/**
		 * Initializes a {@code DrawLabel} instance.
		 */
		DrawLabel() {
			super("Draws: 0");
		}
		
		/**
		 * This method repaints the label.
		 */
		public void repaint() {
			this.setText("Draws: " + draw);
		}
	}
}
