package as3comp2396;

import java.util.Random;

/**
 * The {@code Computer} class implements {@code Candidate} interface.
 * Computer can make move on the board randomly and check if it has won.
 * 
 * @author shirley430316
 * @version 1.0.0
 * 
 */

public class Computer implements Candidate{
	
	/**
	 * Initializes a {@code Computer} instance.
	 */
	public Computer() {}
	
	/**
     * Makes a move on the specified board at a random available slot.
     *
     * @param board the {@code Board} on which to make the move
     * @param slot  the index of the slot to be used for the move
     * @return {@code true} if the move is successful; {@code false} otherwise
     */
	public boolean makeMove(Board board, int slot) {
		
		Random random = new Random();
        int randomNumber = random.nextInt(board.getAvailableSlot().size());
		
		return board.makeMove(board.getAvailableSlot().get(randomNumber), this);
	}
	
	/**
     * Makes a move on the specified board at a random available slot 
     * and returns the slot used for the move.
     *
     * @param board the {@code Board} on which to make the move
     * @return the index of the slot where the move was made
     */
	public int makeMove(Board board) {
		
		Random random = new Random();
        int randomNumber = random.nextInt(board.getAvailableSlot().size()-1);
		int slot = board.getAvailableSlot().get(randomNumber);
		board.makeMove(slot, this);
		
		return slot;
	}
	
	 /**
     * Checks if the computer has won the game based on the current state of the 
     * specified board.
     *
     * @param board the {@code Board} to check for a win condition
     * @return {@code true} if the computer has won; {@code false} otherwise
     */
	public boolean isWin(Board board) {
		return board.isWin(this);
	}
	
}
