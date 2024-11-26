package as3comp2396;

import java.util.ArrayList;


/**
 * The {@code Board} class represents a Tic Tac Toe board for a two-player game. 
 * It keeps track of moves by two players and the current state of the board
 * 
 * @author shirley430316
 * @version 1.0.0
 */
public class Board {
	
	private ArrayList<Integer> playerRecord;
	private ArrayList<Integer> computerRecord;
	private ArrayList<Integer> availableSlot;
	
	/**
	 * Creates and initializes a new Board
	 */
	public Board() {
		playerRecord = new ArrayList<>();
		computerRecord = new ArrayList<>();
		availableSlot = new ArrayList<>();
		
		for (int i=0; i<9; i++) {
			availableSlot.add(i);
		}
	}
	
	
	/**
	 * Makes move on the board at specific player at a given slot.
	 * 
	 * @param slot where to put the next mark (0-8)
	 * @param p the {@code Player} making the move
	 * 
	 * @return {@code true} if the move is successful; {@code false} otherwise
	 * 
	 */
	public boolean makeMove(int slot, Player p) {
		
		if (!availableSlot.contains(slot)) {
			return false;
		}
		
		availableSlot.remove(Integer.valueOf(slot));
		playerRecord.add(slot);
		
		return true;
		
	}
	
	/**
	 * Makes move on the board at specific player at a given slot.
	 * 
	 * @param slot where to put the next mark (0-8)
	 * @param p the {@code Computer} making the move
	 * 
	 * @return {@code true} if the move is successful; {@code false} otherwise
	 * 
	 */
	public boolean makeMove(int slot, Computer c) {
		
		if (!availableSlot.contains(slot)) {
			return false;
		}
		
		availableSlot.remove(Integer.valueOf(slot));
		

		
		computerRecord.add(slot);
		
		return true;
		
	}
	
	/**
	 * Check whether the specific player has won.
	 * 
	 * @param p the {@code Player} making the move
	 * @return  {@code true} if the player has won; {@code false} otherwise
	 */
	public boolean isWin(Player p) {
		if ((playerRecord.contains(0) && playerRecord.contains(3) && playerRecord.contains(6) || 
				playerRecord.contains(1) && playerRecord.contains(4) && playerRecord.contains(7) ||
				playerRecord.contains(2) && playerRecord.contains(5) && playerRecord.contains(8) ||
				playerRecord.contains(0) && playerRecord.contains(1) && playerRecord.contains(2) ||
				playerRecord.contains(3) && playerRecord.contains(4) && playerRecord.contains(5) ||
				playerRecord.contains(6) && playerRecord.contains(7) && playerRecord.contains(8) ||
				playerRecord.contains(0) && playerRecord.contains(4) && playerRecord.contains(8) ||
				playerRecord.contains(2) && playerRecord.contains(4) && playerRecord.contains(6)))	{
			return true;
		}
		else return false;
	}
	
	/**
	 * Check whether the specific player has won.
	 * 
	 * @param p the {@code Computer} making the move
	 * @return  {@code true} if the computer has won; {@code false} otherwise
	 */
	public boolean isWin(Computer c) {
		if ((computerRecord.contains(0) && computerRecord.contains(3) && computerRecord.contains(6) || 
				computerRecord.contains(1) && computerRecord.contains(4) && computerRecord.contains(7) ||
				computerRecord.contains(2) && computerRecord.contains(5) && computerRecord.contains(8) ||
				computerRecord.contains(0) && computerRecord.contains(1) && computerRecord.contains(2) ||
				computerRecord.contains(3) && computerRecord.contains(4) && computerRecord.contains(5) ||
				computerRecord.contains(6) && computerRecord.contains(7) && computerRecord.contains(8) ||
				computerRecord.contains(0) && computerRecord.contains(4) && computerRecord.contains(8) ||
				computerRecord.contains(2) && computerRecord.contains(4) && computerRecord.contains(6))) {
			
			return true;
		}
		else return false;
	}
	
    /**
     * Returns the list of available slots on the board.
     *
     * @return an {@code ArrayList<Integer>} containing the indices of available slots
     */
	public ArrayList<Integer> getAvailableSlot() {
		return availableSlot;
	}
}

