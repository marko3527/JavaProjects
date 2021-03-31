package hr.fer.zemris.java.hw11.jnotepadpp.local;


/**
 * Decorator for LocalizationProvider, it manages 
 * connection status, so if you are already connected 
 * you can't connect unless you disconnect.
 * 
 * @author Marko
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	private boolean connected;
	
	private ILocalizationListener listener;
	
	private ILocalizationProvider parent;
	private String currentLanguage;
	
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		currentLanguage = parent.getCurrentLanguage();
	}
	
	
	/**
	 * Registering localization listener.
	 */
	public void connect() {
		if(connected) {
			return;
		}
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				for(ILocalizationListener listener : listeners) {
					listener.localizationChanged();
				}
			}
		};
		parent.addLocalizationListener(listener);
	}
	
	
	/**
	 * Disconnecting the listener.
	 */
	public void disconnect() {
		if(connected) {
			listener = null;
			parent.removeLocalizationListener(listener);
		}
	}
	
	@Override
	public String getString(String key) {
		return this.parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return currentLanguage;
	}

}
