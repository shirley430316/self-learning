package as3comp2396;


/**
 * The {@code Player} class implements {@code Candidate} interface.
 * Player can make certain move on the board and check if they has won.
 * 
 * @author shirley430316
 * @version 1.0.0
 * 
 */
public class Player implements Candidate{

	/**
	 * Initializes a {@code Player} instance.
	 */
	public Player() {}
	
	/**
     * Makes a move on the specified board at a slot.
     *
     * @param board the {@code Board} on which to make the move
     * @param slot  the index of the slot to be used for the move
     * @return {@code true} if the move is successful; {@code false} otherwise
     */
	public boolean makeMove(Board board, int slot) {
		return board.makeMove(slot, this);
	}
	
	 /**
     * Checks if the player has won the game based on the current state of the 
     * specified board.
     *
     * @param board the {@code Board} to check for a win condition
     * @return {@code true} if the player has won; {@code false} otherwise
     */
	public boolean isWin(Board board) {
		return board.isWin(this);
	}
}
