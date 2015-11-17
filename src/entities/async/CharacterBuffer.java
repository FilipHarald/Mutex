package entities.async;

import java.util.Observable;

public class CharacterBuffer{
	private char c;
	
	public void put(char c) {
		this.c = c;
	}
	
	public char get(){
		return c;
	}
	
}
