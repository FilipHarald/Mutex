package controller;

import java.util.LinkedList;

import entities.async.*;
import entities.sync.*;
import gui.GUIMutex;

/**This class handles the comunication between the GUI and the reader and writer
 * @author Filip
 *
 */
public class Controller {
	private GUIMutex guiFrame;
	private char[] chars;
	private boolean writerIsDone;
	private boolean readerIsDone;
	private LinkedList<Character> readCharsList;

	/**Contructs the controller for the specified GUI
	 * @param guiFrame the specified GUI
	 */
	public Controller(GUIMutex guiFrame) {
		this.guiFrame = guiFrame;
	}
	
	/**Initializes the reader, writer and character buffer and starts the reader- and writer- threads.
	 * @param text the text to transmit
	 * @param synchronous specifies if the transimission should be synchronous or asynchronous
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
	
	/**Updates the writer logger(text area) in the GUI with the specified String
	 * @param s the specified String
	 */
	public void updateWriterLogger(String s){
		guiFrame.updateWriterLogger(s);
	}
	
	/**Updates the reader logger(text area) in the GUI with the specified String
	 * @param s the specified String
	 */
	public void updateReaderLogger(String s){
		guiFrame.updateReaderLogger(s);
	}
	
	/**First stores the the read(received) char and then invokes the method updateReaderLogger
	 * @param c the read char
	 */
	public void updateReader(char c){
		readCharsList.add(new Character(c));
		guiFrame.updateReaderLogger("Reading " + c);
	}

	/**If the reader is done, this method checks if the transmitted text matches the received text
	 * 
	 */
	public void setWriterDone() {
		writerIsDone = true;
		checkMatch();
	}
	
	/**If the writer is done, this method checks if the transmitted text matches the received text
	 * 
	 */
	public void setReaderDone(){
		readerIsDone = true;
		checkMatch();
	}
	/**
	 * @return The string-representation of the transmitted text
	 */
	public String getTransmittedText(){
		String s = "";
		for(char c : chars){
			s += c;
		}
		return s;
	}
	/**
	 * @returnThe string-representation of the received text
	 */
	public String getReceivedText(){
		String s = "";
		for(char c : readCharsList){
			s += c;
		}
		return s;
	}
	/**
	 * Checks if there is a match and updates the GUI accordingly
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
