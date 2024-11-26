package as3comp2396;

/**
 * The {@code Candidate} interface represents a player that can 
 * make moves on the board and determine if they have won the game.
 *
 * @author shirley430316
 * @version 1.0.0
 */
public interface Candidate {
	/**
     * Makes a move on the specified board at the given slot.
     *
     * @param board the {@code Board} on which the move is made
     * @param slot  the position on the board where the move is made
     * @return {@code true} if the move is successful; {@code false} otherwise
     */
	public boolean makeMove(Board board, int slot);
	
	/**
     * Checks if the current candidate has won the game based on the current 
     * state of the specified board.
     *
     * @param board the {@code Board} to check for a win condition
     * @return {@code true} if the candidate has won; {@code false} otherwise
     */
	public boolean isWin(Board board);
}
