package entities.sync;

import java.util.Observable;

import entities.async.CharacterBuffer;

public class SyncCharacterBuffer extends Observable  {
	private char c;
	private boolean hasBeenRead = true;
	
	public void put(char c) {
		this.c = c;
		hasBeenRead = false;
		setChanged();
		notifyObservers();
	}
	
	public boolean hasBeenRead(){
		return hasBeenRead;
	}
	
	public char get(){
		hasBeenRead = true;
		setChanged();
		notifyObservers();
		return c;
	}
	
}
