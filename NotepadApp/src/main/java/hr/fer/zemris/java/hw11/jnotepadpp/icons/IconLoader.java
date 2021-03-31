package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;


/**
 * Class that offers method for loading the saved/unsaved icons.
 * 
 * @author Marko
 *
 */
public class IconLoader {
	
	public ImageIcon UNSAVED;
	public ImageIcon SAVED;
	
	
	/**
	 * Constructor that loads both icons.
	 * 
	 */
	public IconLoader() {
		try {
			UNSAVED = loadIcon("unsaved icon.png");
			SAVED = loadIcon("saved icon.png");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	/**
	 * Method that reads the bytes from the wanted icon file and
	 * returns the ImageIcon that can be then displayed on tabs.
	 * 
	 * @param iconName {@code String} name of the icon
	 * @return ImageIcon 
	 * @throws IOException if there was problem with loading or reading the icon
	 */
	public ImageIcon loadIcon(String iconName) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(iconName);
		if(is == null) {
			throw new IOException("There was problem with resource loading!");
		}
		else {
			byte[] bytes = is.readAllBytes();
			ImageIcon icon = new ImageIcon(bytes);
			return icon;
		}
	}

}
