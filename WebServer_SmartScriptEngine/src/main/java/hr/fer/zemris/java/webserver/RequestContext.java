package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Class that has informations about the context of the 
 * HTTP request that user made. It makes a header with informations 
 * about the request, request status, and encoding of the content and then 
 * returns the content of the http page or resource, whatever it is that
 * user is making request on.
 * 
 * @author Marko
 *
 */
public class RequestContext {
	
	
	/**
	 * Static class with read only properties
	 * 
	 * @author Marko
	 *
	 */
	public static class RCCookie{
		
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		
		
		/**
		 * Constructor. Sets the all parameters of the cookie to the given values.
		 * Max age, path and domain values can be null.
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		
		/**
		 * Getter for domain of the cookie.
		 * @return
		 */
		public String getDomain() {
			return domain;
		}
		
		
		/**
		 * Getter for max age of the cookie.
		 * @return
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		
		/**
		 * Getter for name of the cookie.
		 * @return
		 */
		public String getName() {
			return name;
		}
		
		
		/**
		 * Getter for path of the cookie.
		 * @return
		 */
		public String getPath() {
			return path;
		}
		
		
		/**
		 * Getter for value of the cookie.
		 * @return
		 */
		public String getValue() {
			return value;
		}
	}
	
	
	/**
	 * OutputStream for writing the header and request result
	 */
	private OutputStream outputStream;
	private Charset charset;
	
	
	/**
	 * Variables that are set only and can be changed just before
	 * writing header.
	 */
	public String encoding = "UTF-8";
	public int statusCode = 200;
	public String statusText = "OK";
	public String mimeType = "text/html";
	public Long contentLength = null;
	
	
	private Map<String, String> parameters = new HashMap<>();
	private Map<String, String> temporaryParameters = new HashMap<>();
	private Map<String, String> persisantParameters = new HashMap<>();
	private List<RCCookie> outputCookies = new ArrayList<>();
	private boolean headerGenerated = false;
	private IDispatcher dispatcher;
	
	/**
	 * Id of the session to which context is assigned.
	 */
	private String sid;
	
	
	/**
	 * Constructor.
	 * 
	 * @param outputStream {@code OutputStream} output stream to write the 
	 * 		  content of request and header
	 * @param parameters {@code Map<String, String>}
	 * @param persistentParameters {@code Map<String, String>}
	 * @param outputCookies {@code List<RCCookie>} cookies stored on the request
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
						  Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if(outputStream == null) {
			throw new NullPointerException("Output stream can't be null");
		}
		
		this.outputStream = outputStream;
		if(parameters != null) {
			this.parameters = parameters;
		}
		
		if(persistentParameters != null) {
			this.persisantParameters = persistentParameters;
		}
		
		if(outputCookies != null) {
			this.outputCookies = outputCookies;
		}
	}
	
	
	/**
	 * Optional contructor.
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 * @param temporaryParameters
	 * @param dispatcher
	 * @param sid
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			  Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			  Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		if(temporaryParameters != null) {
			this.temporaryParameters = temporaryParameters;
		}
		if(dispatcher != null) {
			this.dispatcher = dispatcher;
		}
		this.sid = sid;
	}
	
	
	/**
	 * Setter. Sets the encoding for the request. It can be changed 
	 * if header wasn't created.
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Encoding can't be modified!");
		}
		this.encoding = encoding;
	}
	
	
	/**
	 * Setter. Sets the statusCode for the request. It can be changed 
	 * if header wasn't created.
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Status code can't be changed!");
		}
		this.statusCode = statusCode;
	}
	
	
	/**
	 * Setter. Sets the status text for the request. It can be changed 
	 * if header wasn't created.
	 * 
	 * 
	 * @param statusText
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Status text can't be changed!");
		}
		this.statusText = statusText;
	}
	
	
	/**
	 * Setter. Sets the mime type for the request. It can be changed 
	 * if header wasn't created.
	 * 
	 * 
	 * @param mimeType
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Mime type can't be changed!");
		}
		this.mimeType = mimeType;
	}
	
	
	/**
	 * Setter. Sets the output cookies for the request. It can be changed 
	 * if header wasn't created.
	 * 
	 * 
	 * @param outputCookies
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		if(headerGenerated) {
			throw new RuntimeException("Output cookies can't be changed!");
		}
		this.outputCookies = outputCookies;
	}
	
	
	/**
	 * Setter. Sets the contentLength for the request. It can be changed 
	 * if header wasn't created.
	 * 
	 * 
	 * @param contentLength
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException("Content length can't be changed!");
		}
		this.contentLength = contentLength;
	}
	
	
	/**
	 * Method that retrieves value from parameters map 
	 * (or null if no association exists).
	 * 
	 * @param name {@code name of wanted parameter}
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	
	/**
	 * Method that retrieves names of all parameters in parameters map
	 * @return {@code Set}
	 */
	public Set<String> getParameterNames(){
		return parameters.keySet();
	}
	
	    
	/**
	 * Method that retrieves value from persistentParameters map 
	 * @param name
	 * @return
	 */
	public String getPersistentParameter(String name) {
		return persisantParameters.get(name);
	}
	
	
	/**
	 * Method that retrieves names of all parameters in persistent parameters map 
	 * @return
	 */
	public Set<String> getPersistentParameterNames() {
		return persisantParameters.keySet();
	}
	
	
	/**
	 * Method that stores a value to persistentParameters map
	 * 
	 * @param name
	 * @param value
	 */
	public void setPersistentParameter(String name, String value) {
		persisantParameters.put(name, value);
	}
	
	
	/**
	 * Method that removes a value from persistentParameters map
	 * 
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		persisantParameters.remove(name);
	}
	
	
	/**
	 * Method that retrieves value from temporaryParameters map 
	 * (or null if no association exists)
	 *
	 * @param name
	 * @return
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	
	/**
	 * Method that retrieves names of all parameters in temporary parameters map 
	 * 
	 * @return
	 */
	public Set<String> getTemporaryParameterNames() {
		return temporaryParameters.keySet();
	}
	
	
	/**
	 * Method that retrieves an identifier which is unique for current user session
	 * 
	 * @return
	 */
	public String getSessionID() {
		return sid;
	}
	
	
	/**
	 * Method that stores a value to temporaryParameters map
	 * 
	 * @param name
	 * @param value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	
	/**
	 * Method that removes a value from temporaryParameters map
	 * 
	 * @param name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	
	/**
	 * Method that adds the cookie to the list of cookies
	 * 
	 * @param cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}
	
	
	/**
	 * Method that write the array of byte into outputStream.
	 * 
	 * @param data {@code byte[]} data to write
	 * @return instance of {@code RequestContext} on which the write was called
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		write(data, 0, data.length);
		return this;
	}
	
	/**
	 * Method that write the array of byte with offset and 
	 * length into outputStream.
	 * 
	 * @param data {@code byte[]} data to write
	 * @param offset
	 * @param len
	 * @return instance of {@code RequestContext} on which the write was called
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			writeHeader(outputStream);
		}
		outputStream.write(data, offset, len);
		headerGenerated = true;
		
		return this;
	}
	
	
	/**
	 * Method that write the String into outputStream.
	 * It converts the given String into bytes with set encoding
	 * and then write the byte[] to output
	 * 
	 * @param text {@code String} text to write
	 * @return instance of {@code RequestContext} on which the write was called
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			writeHeader(outputStream);
		}
		outputStream.write(text.getBytes(charset));
		headerGenerated = true;
		
		
		return this;
	}
	
	/**
	 * Method that returns the dispacther which handles the url paths 
	 * that user asked for.
	 * 
	 * @return {@code IDispatcher}
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	
	/**
	 * Header creator method. It creates a header with this pattern:
	 * 'HTTP/1.1'  statusCode/statusText
	 * 'Content-Type: ' + mimeType (if mimeType starts with 'text/' it also adds '; charset= ' + encoding)
	 * 'Content-Length: ' + contentnLength if it is not null
	 * 'Set-Cookie: cookieName = cookieValue; domain = ; path = ; maxAge = 
	 * 
	 * @param outputStream {@code OutputStream} stream to write header to
	 * @throws IOException
	 */
	private void writeHeader(OutputStream outputStream) throws IOException {
		charset = Charset.forName(encoding);
		
		mimeType = mimeType.replace("\"", "");
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + "/" + statusText + "\r\n");
		
		sb.append("Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset= " + encoding);
		}
		sb.append("\r\n");
		
		if(contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		
		if(!outputCookies.isEmpty()) {
			for(RCCookie cookie : outputCookies) {
				sb.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"");
				if(cookie.domain != null) {
					sb.append("; Domain=" + cookie.domain);
				}
				if(cookie.path != null) {
					sb.append("; Path=" + cookie.path);
				}
				if(cookie.maxAge != null) {
					sb.append("; Max-Age=" + cookie.maxAge);
				}
				sb.append("; HttpOnly");
				sb.append("\r\n");
			}
		}
		
		sb.append("\r\n");
		
		outputStream.write(sb.toString().getBytes(charset));
	}
}
