package readerwriter;

import java.util.Observable;

public class CharacterBuffer extends Observable {
	private char c;
	private boolean hasBeenRead = true;
	
	public void put(char c) {
		System.out.println("cb put " + c);
		this.c = c;
		hasBeenRead = false;
		setChanged();
		notifyObservers();
	}
	
	public boolean hasBeenRead(){
		return hasBeenRead;
	}
	
	public char get(){
		System.out.println("cb get " + c);
		hasBeenRead = true;
		setChanged();
		notifyObservers();
		return c;
	}
	
}
