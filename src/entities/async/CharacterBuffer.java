package entities.async;

import java.util.Observable;

/**
 * @author Filip
 *
 */
public class CharacterBuffer{
	private char c;
	
	/**
	 * @param c
	 */
	public void put(char c) {
		this.c = c;
	}
	
	/**
	 * @return
	 */
	public char get(){
		return c;
	}
	
}
