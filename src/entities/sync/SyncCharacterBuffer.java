package entities.sync;

import java.util.Observable;

import entities.async.CharacterBuffer;

/**A character buffer which keeps track of if the char is read or not. 
 * @author Filip
 *
 */
public class SyncCharacterBuffer extends Observable  {
	private char c;
	private boolean hasBeenRead = true;
	
	/**Sets the character to the specified char, sets status to not read and notifies observers.
	 * @param c
	 */
	public void put(char c) {
		this.c = c;
		hasBeenRead = false;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return true if the char has been read otherwise false
	 */
	public boolean hasBeenRead(){
		return hasBeenRead;
	}
	
	/**Returns the char, sets status to has not been read and notifies observers
	 * @return the char
	 */
	public char get(){
		hasBeenRead = true;
		setChanged();
		notifyObservers();
		return c;
	}
	
}
