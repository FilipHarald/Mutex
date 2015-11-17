package entities.sync;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import controller.Controller;

public class SyncReader implements Runnable, Observer{
	private SyncCharacterBuffer cb;
	private Controller controller;
	private Random rand;

	public SyncReader(SyncCharacterBuffer cb, Controller controller) {
		this.cb = cb;
		cb.addObserver(this);
		this.controller = controller;
		rand = new Random();
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){			
			try {
				Thread.sleep(rand.nextInt(200)+100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(cb.hasBeenRead()){
				controller.updateReaderLogger("Trying to read. No data. Reader waits.");
				try {
					synchronized(this){						
						while(cb.hasBeenRead()){						
							this.wait();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			char c = cb.get();
			if(c != '\0'){				
				controller.updateReader(c);
			}else{
				break;
			}
		}
		
		controller.setReaderDone();
	}

	@Override
	public void update(Observable o, Object arg) {
		synchronized(this){			
			notifyAll();
		}
	}

}
