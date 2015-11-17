package entities.async;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import controller.Controller;

public class Reader implements Runnable{
	private CharacterBuffer cb;
	private Controller controller;
	private Random rand;

	public Reader(CharacterBuffer cb, Controller controller) {
		this.cb = cb;
		this.controller = controller;
		rand = new Random();
	}

	@Override
	public void run() {
		boolean hasReadFirstChar = false;
		while(!Thread.interrupted()){			
			try {
				Thread.sleep(rand.nextInt(500)+100);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
}