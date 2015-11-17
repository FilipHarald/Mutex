package readerwriter;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import controller.Controller;

public class Writer implements Runnable, Observer{
	private char[] chars;
	private CharacterBuffer cb;
	private Controller controller;
	private Random rand;
	
	public Writer(char[] chars, CharacterBuffer cb, Controller controller) {
		this.chars = chars;
		this.cb = cb;
		cb.addObserver(this);
		this.controller = controller;
		rand = new Random();
	}
	
	public void ifHasNotBeenRead(){
		while(!cb.hasBeenRead()){
			controller.updateWriterLogger("Trying to write. Data exists. Writer waits.");
			try {
				synchronized(this){						
					while(!cb.hasBeenRead()){						
						this.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		for(char c : chars){
			try {
				Thread.sleep(rand.nextInt(200)+100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ifHasNotBeenRead();
			controller.updateWriterLogger("Writing " + c);
			System.out.println("Writer" + c);
			cb.put(c);
		}
		ifHasNotBeenRead();
		cb.put('\0');
		controller.setWriterDone();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		synchronized(this){			
			notifyAll();
		}
	}

}
