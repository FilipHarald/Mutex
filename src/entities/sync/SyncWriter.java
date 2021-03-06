package entities.sync;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import controller.Controller;

/**A synchronized writer that writes to the CharacterBuffer
 * @author Filip
 *
 */
public class SyncWriter implements Runnable, Observer{
	private char[] chars;
	private SyncCharacterBuffer cb;
	private Controller controller;
	private Random rand;
	
	/**Constructs a Writer with the specified CharacterBuffer, Controller and the text to transmit(represented in an char[])
	 * @param cb
	 * @param controller
	 * @param chars
	 */
	public SyncWriter(char[] chars, SyncCharacterBuffer cb, Controller controller) {
		this.chars = chars;
		this.cb = cb;
		cb.addObserver(this);
		this.controller = controller;
		rand = new Random();
	}
	
	/**
	 * Prints "Trying to write. Data exists. Writer waits." and then waits for the character buffer to notify when it can be used.
	 */
	public void whileHasNotBeenRead(){
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
			whileHasNotBeenRead();
			controller.updateWriterLogger("Writing " + c);
			cb.put(c);
		}
		whileHasNotBeenRead();
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
