package controller;

import java.util.LinkedList;

import entities.async.*;
import entities.sync.*;
import gui.GUIMutex;

/**
 * @author Filip
 *
 */
public class Controller {
	private GUIMutex guiFrame;
	private char[] chars;
	private boolean writerIsDone;
	private boolean readerIsDone;
	private LinkedList<Character> readCharsList;

	/**
	 * @param guiFrame
	 */
	public Controller(GUIMutex guiFrame) {
		this.guiFrame = guiFrame;
	}
	
	/**
	 * @param text
	 * @param synchronous
	 */
	public void startRW(String text, boolean synchronous){
		this.chars = text.toCharArray();
		readCharsList = new LinkedList<Character>();
		if(synchronous){
			SyncCharacterBuffer scb = new SyncCharacterBuffer();
			new Thread(new SyncWriter(chars, scb, this)).start();
			new Thread(new SyncReader(scb, this)).start();			
		}else{
			CharacterBuffer cb = new CharacterBuffer();
			new Thread(new Writer(chars, cb, this)).start();
			new Thread(new Reader(cb, this)).start();			

		}
	}
	
	/**
	 * @param s
	 */
	public void updateWriterLogger(String s){
		guiFrame.updateWriterLogger(s);
	}
	
	/**
	 * @param s
	 */
	public void updateReaderLogger(String s){
		guiFrame.updateReaderLogger(s);
	}
	
	/**
	 * @param c
	 */
	public void updateReader(char c){
		readCharsList.add(new Character(c));
		guiFrame.updateReaderLogger("Reading " + c);
	}

	/**
	 * 
	 */
	public void setWriterDone() {
		writerIsDone = true;
		checkMatch();
	}
	
	/**
	 * 
	 */
	public void setReaderDone(){
		readerIsDone = true;
		checkMatch();
	}
	/**
	 * @return
	 */
	public String getTransmittedText(){
		String s = "";
		for(char c : chars){
			s += c;
		}
		return s;
	}
	/**
	 * @return
	 */
	public String getReceivedText(){
		String s = "";
		for(char c : readCharsList){
			s += c;
		}
		return s;
	}
	/**
	 * 
	 */
	public void checkMatch(){
		if(writerIsDone && readerIsDone){
			boolean textIsMatching = true;
			if(readCharsList.size() != chars.length){
				textIsMatching = false;
			}else{				
				for(int i = 0; i < chars.length; i++){
					if(!(chars[i] == readCharsList.get(i))){
						textIsMatching = false;
						break;
					}
				}
			}
			guiFrame.setTextMatching(textIsMatching);
			guiFrame.setTransmittedText(getTransmittedText());
			guiFrame.setReceivedText(getReceivedText());
		}
	}
}
