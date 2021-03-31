package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;

/**
 * Factory that creates threads that have daemonic flag set on.
 * 
 * @author Marko
 *
 */
public class DaemonicThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		Thread newThread = new Thread(r);
		newThread.setDaemon(true);
		
		return newThread;
	}

}
