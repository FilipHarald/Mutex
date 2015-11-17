package entities.async;

import java.util.Random;

import controller.Controller;

public class Writer implements Runnable{
	private char[] chars;
	private CharacterBuffer cb;
	private Controller controller;
	private Random rand;
	
	public Writer(char[] chars, CharacterBuffer cb, Controller controller) {
		this.chars = chars;
		this.cb = cb;
		this.controller = controller;
		rand = new Random();
	}

	@Override
	public void run() {
		for(char c : chars){
			controller.updateWriterLogger("Writing " + c);
			cb.put(c);
			try {
				Thread.sleep(rand.nextInt(500)+100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cb.put('\0');
		controller.setWriterDone();
	}
}
