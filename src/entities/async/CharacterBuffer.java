package entities.async;

import java.util.Observable;

/**A simple characterbuffer
 * @author Filip
 *
 */
public class CharacterBuffer{
	private char c;
	
	/**Sets the character to the specified char
	 * @param c specified char
	 */
	public void put(char c) {
		this.c = c;
	}
	
	/**Returns the current char
	 * @return current char
	 */
	public char get(){
		return c;
	}
	
}
