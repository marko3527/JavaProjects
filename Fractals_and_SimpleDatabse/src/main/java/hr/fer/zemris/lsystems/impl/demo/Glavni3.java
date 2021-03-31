package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo that can load L systems from text document.
 * 
 * @author Marko
 *
 */
public class Glavni3 {
	
	/**
	 * Main method from which the program starts.
	 * @param args
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}

}
