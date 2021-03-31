package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * It extends the bridge for localization. It registers itself
 * as a listener on opened window and when that window is closed
 * it disconnects.
 * 
 * @author Marko
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 * @param frame
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		
		});
	}
	
	

}
