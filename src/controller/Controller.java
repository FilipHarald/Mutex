package controller;

import java.util.LinkedList;

import readerwriter.CharacterBuffer;
import readerwriter.Reader;
import readerwriter.Writer;
import gui.GUIMutex;

public class Controller {
	private GUIMutex guiFrame;
	private char[] chars;
	private boolean writerIsDone;
	private boolean readerIsDone;
	private LinkedList<Character> readCharsList;

	public Controller(GUIMutex guiFrame) {
		this.guiFrame = guiFrame;
	}
	
	public void startRW(String text, boolean syncronous){
		//TODO sync/async
		CharacterBuffer cb = new CharacterBuffer();
		this.chars = text.toCharArray();
		readCharsList = new LinkedList<Character>();
		new Thread(new Writer(chars, cb, this)).start();
		new Thread(new Reader(cb, this)).start();
	}
	
	public void updateWriterLogger(String s){
		guiFrame.updateWriterLogger(s);
	}
	
	public void updateReaderLogger(String s){
		guiFrame.updateReaderLogger(s);
	}
	
	public void updateReader(char c){
		readCharsList.add(new Character(c));
		guiFrame.updateReaderLogger("Reading " + c);
	}

	public void setWriterDone() {
		writerIsDone = true;
		checkMatch();
	}
	
	public void setReaderDone(){
		readerIsDone = true;
		checkMatch();
	}
	
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
		}
	}
}
