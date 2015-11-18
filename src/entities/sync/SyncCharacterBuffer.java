package entities.sync;

import java.util.Observable;

import entities.async.CharacterBuffer;

/**
 * @author Filip
 *
 */
public class SyncCharacterBuffer extends Observable  {
	private char c;
	private boolean hasBeenRead = true;
	
	/**
	 * @param c
	 */
	public void put(char c) {
		this.c = c;
		hasBeenRead = false;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return
	 */
	public boolean hasBeenRead(){
		return hasBeenRead;
	}
	
	/**
	 * @return
	 */
	public char get(){
		hasBeenRead = true;
		setChanged();
		notifyObservers();
		return c;
	}
	
}
