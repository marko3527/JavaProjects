package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;


/**
 * Class that represent a multi thread web server with different 
 * workers and stored resources.
 * 
 * 
 * @author Marko
 *
 */
public class SmartHttpServer {
	
	
	/**
	 * Main method from which the server is runned and 
	 * being active. As argument we provide the name
	 * of the properties file that will be used for 
	 * configuration of server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SmartHttpServer("server.properties");
	}
	

	private String adress;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	
	/**
	 * mime types that this server can provide
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	
	/**
	 * root where the resources that are available to user are 
	 * stored
	 */
	private Path documentRoot;
	
	/**
	 * map that stores references to mapped workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * session of which the server is aware, used for cookies
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	
	private boolean stopRequested = false;
	
	
	/**
	 * This is runnable object which goes through map
	 * sessions and searches for the session that has expired
	 * and if it finds session like that it removes it from the map
	 */
	Runnable cleaningSessions = new Runnable() {
		
		@Override
		public void run() {
			for(String key : sessions.keySet()) {
				if(checkIfSessionTooOld(sessions.get(key))) {
					sessions.remove(key);
				}
			}
		}
	};
	
	
	/**
	 * Constructor. It takes the name of properties file 
	 * for server configuration. From that file it extracts the 
	 * properties as adress, domainName, port, mimeTypes... And after
	 * assigning properties it runs the server thread.
	 * 
	 * @param configFileName
	 */
	public SmartHttpServer(String configFileName) {
		Properties propertiesLoader = new Properties();
		
		try {
			propertiesLoader.load(Files.newInputStream(Paths.get("./config/" + configFileName)));
			
			this.adress = propertiesLoader.getProperty("server.adress");
			this.domainName = propertiesLoader.getProperty("server.domainName");
			this.port = Integer.parseInt(propertiesLoader.getProperty("server.port"));
			this.documentRoot = Paths.get(propertiesLoader.getProperty("server.documentRoot"));
			this.sessionTimeout = Integer.parseInt(propertiesLoader.getProperty("session.timeout"));
			this.workerThreads = Integer.parseInt(propertiesLoader.getProperty("server.workerThreads"));
			
			Path mimeConfigPath = Paths.get(propertiesLoader.getProperty("server.mimeConfig"));
			Properties mimeProperties = new Properties();
			mimeProperties.load(Files.newInputStream(mimeConfigPath));
			for(Object key : mimeProperties.keySet()) {
				mimeTypes.put("" + key, mimeProperties.getProperty("" + key));
			}
			
			Path workersPath = Paths.get(propertiesLoader.getProperty("server.workers"));
			Properties workersProperties = new Properties();
			workersProperties.load(Files.newInputStream(workersPath));
			for(Object key : workersProperties.keySet()) {
				Class<?> referenceToClass = this.getClass().getClassLoader().
											loadClass(workersProperties.getProperty("" + key));
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put("" + key, iww);
				
			}
			
			serverThread = new ServerThread();
			this.start();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Method that initializes pool of threads to number
	 * of worker threads and calls the method run()
	 * over server thread to run it.
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.run();
	}
	
	
	/**
	 * Method that stops the running of the thread.And shuts
	 * down the pool of threads. 
	 * 
	 */
	protected synchronized void stop() {
		stopRequested = true;
		threadPool.shutdown();
	}
	
	
	
	/**
	 * Server thread that makes a socket connection based on port and
	 * address, initializes client worker and pushes the instance 
	 * of the worker to the thread pool to be executed.
	 * 
	 * @author Marko
	 *
	 */
	protected class ServerThread extends Thread{
		
		
		@Override
		public void run() {
			@SuppressWarnings("resource")
			ServerSocket serverSocket;
			
			
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(
						new InetSocketAddress(adress, port)
				);
				
				ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(cleaningSessions, 0, 5, TimeUnit.MINUTES);
				
				while(true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					
					threadPool.execute(cw);
					
					if(stopRequested) {
						break;
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Private class that represents one session that can be 
	 * stored in map sessions. Session's are different based on their
	 * session id name(sid), every session has its expiring date(validUntil), 
	 * and host of that session and the map which is used to store some values
	 * to be used later when the user loads this session.
	 * 
	 * @author Marko
	 *
	 */
	private static class SessionMapEntry {
		String sid;
		String host;
		long validUntil;
		Map<String, String> map;
		
		
		/**
		 * Constructor. It assigns the argument to their variable and
		 * then calculates the validUntil variable. ValidUntil is calculated
		 * by getting the time, when the constructor is being called, in miliseconds
		 * and adding the sessionTimeout(it is defined in minutes) * 1000 to get 
		 * it to miliseconds and adding that value to the time 'now'. 
		 * 
		 * @param sid {@code String} id of the session
		 * @param sessionTimeout {@code int} number of seconds for which the session is active
		 * @param map {@code Map<String,String>} thread safe map
		 * @param host {@code String} name of the host
		 */
		public SessionMapEntry(String sid, int sessionTimeout,
							   Map<String, String> map, String host) {
			this.sid = sid;
			
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(new Date());
			gcal.add(GregorianCalendar.SECOND, sessionTimeout * 1000);
			validUntil = gcal.getTimeInMillis();
			
			this.map = map;
			this.host = host;
		}
	}
	
	
	/**
	 * Client worker implements interface Runnable so it can be assigned 
	 * to the pools of threads. It also implements IDispatcher 
	 * which is used to analyze and server the path user/server requests.
	 * 
	 * @author Marko
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher{
		
		private Socket csocket;
		private InputStream inputStream;
		private OutputStream outputStream;
		private String version;
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<>();
		private Map<String, String> tempParams = new HashMap<>();
		private Map<String, String> permParams = new HashMap<>();
		private List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		
		private RequestContext rc = null;
		
		
		/**
		 * Conctstructor. As argument it takes socket connection from
		 * which client worker will extract the input and output stream.
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			try {
				inputStream = new BufferedInputStream(csocket.getInputStream());
				outputStream = new BufferedOutputStream(csocket.getOutputStream());
				
				List<String> headers = readHeaders();
				/**
				 * First line of headers should be GET ..., in next lines of code
				 * we check if that request is good, if it's not then we 
				 * send appropriate response
				 */
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				
				if(firstLine == null || firstLine.length != 3) {
					sendResponse(outputStream, 400, "Bad request");
					return;
				}
				
				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendResponse(outputStream, 400, "Method not allowed");
					return;
				}
				
				version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendResponse(outputStream, 400, "HTTP version not supported");
					return;
				}
				
				for(String header : headers) {
					if(header.contains("Host:")) {
						String hostProperty = header.substring(5).trim();
						if(hostProperty.contains(":")){
							hostProperty = hostProperty.replaceAll(":\\d*", "");
						}
						host = hostProperty;
						break;
					}
					else {
						host = domainName;
					}
				}
				
				checkSession(headers);
				
				String path = "";
				if(firstLine[1].contains("?")) {
					String[] requestedPath = firstLine[1].split("\\?");
					path = requestedPath[0];
					if(requestedPath.length > 1) {
						String parameterString = requestedPath[1];
						parseParameters(parameterString);
					}
				}
				else {
					path = firstLine[1];
				}
				
				internalDispatchRequest(path.toString(), true);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
			try {
				outputStream.flush();
				outputStream.close();
				csocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * Method that checks if headers have line that starts with "Cookie:".
		 * If it does we extract the cookie that has value of name equal to 'sid'
		 * and then we check if that session id exists in the sessions map.
		 * If sid does not exist in our session map we make an entry to the map
		 * with 'sid' value as key and if entry exists we make sure that 
		 * the session hasn't expired and if it has expired we remove 
		 * the session from sessions else we add the to he validUntil value anoter
		 * sessionTimeout to be sure that this cookie is going to be active for a while, and 
		 * finally we set the persistant parameters to the map of the entry 
		 * to grab any value that the cookie is holding.
		 * 
		 * @param headers
		 */
		private synchronized void checkSession(List<String> headers) {	
			
			String sidCandidate = "";
			for(String header : headers) {
				if(header.startsWith("Cookie:")) {
					String[] cookies = header.replace("Cookie:", "").split(";");
					for(String cookie : cookies) {
						if(cookie.contains("sid")) {
							sidCandidate = cookie.split("=")[1].replace("\"", "");
						}
					}
				}
			}
			
			if(sidCandidate.isEmpty() || sidCandidate.equals("null")) {
				assignNewSession(null);
			}
			else {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if(entry == null) {
					assignNewSession(sidCandidate);
				}
				else if(!entry.host.equals(this.host)) {
					assignNewSession(null);
				}
				else if(checkIfSessionTooOld(entry)){
					sessions.remove(sidCandidate);
					assignNewSession(null);
				}
				else {
					entry.validUntil += sessionTimeout * 1000;
					permParams = entry.map;
				}
			}
		}
		
		/**
		 * Method that assigns new session and puts it in 
		 * sessions map. It takes one argument, which can be null. It it is not null
		 * we don't go through steps of generating the session id cause we got 
		 * it through argument. Else we create a random session id. Generating random
		 * session id is done by defining the letter from which the id will be 
		 * generated and then generating random string with length of 20.
		 * 
		 * @param sidCandidate {@code String} can be null
		 * @return {@code SessionMapEntry} entry that has been put to map.
		 */
		private synchronized SessionMapEntry assignNewSession(String sidCandidate) {

			if(sidCandidate == null) {
				String alphabet = "ABCDEFGHIJKLMNOPQRSTUYWVZ";
				this.SID = new SecureRandom()
			            .ints(20, 0, alphabet.length())
			            .mapToObj(alphabet::charAt)
			            .map(Object::toString)
			            .collect(Collectors.joining());
			}
			else{
				this.SID = sidCandidate;
			}
			SessionMapEntry session = new SessionMapEntry(SID, sessionTimeout,
					  				  new ConcurrentHashMap<String, String>(), host);
			sessions.put(session.sid, session);
			cookies.add(new RCCookie("sid", SID, null, host, "/"));
			session.map = permParams;
			return session;
		}
		
		
		
		
		/**
		 * Method that serves the requested file. User can request a "name.smscr"
		 * type of files. In that case we make instance of {@code SmartScriptEngine}
		 * that generates and we do not set the size of the file cause generated
		 * response can be larger than the file itself.
		 * User can request file by giving 
		 * its path in request from browser. Method will determine the size of
		 * requested file and mime type of the file. Then it will set those variables
		 * and generate header of the response and then will write the bytes of the 
		 * wanted file to the output stream over instance of request context.
		 * 
		 * @param outputStream {@code OutputStream}
		 * @param requestedFile {@code Path}
		 * @throws IOException
		 */
		private void serveFile(OutputStream outputStream, Path requestedFile) throws IOException {
			long size = Files.size(requestedFile);
			String mime = determineMimeType(requestedFile);
			
			
			if(mime == null) {
				mime = "application/octet-stream";
			}
			
			if(requestedFile.toString().endsWith(".smscr")) {
				try {
					String docBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
					RequestContext rc = provideRequestContext();
					rc.setStatusCode(200);
					
					
					new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), rc).execute();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			try(InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile))){
				RequestContext rc = provideRequestContext();
				rc.setMimeType(mime);
				rc.setStatusCode(200);
				rc.setContentLength(size);
				
				byte[] buf = new byte[1024];
				while(true) {
					int r = is.read(buf);
					if(r<1) break;
					rc.write(buf, 0, r);
				}
			}
		}
		
		
		/**
		 * Method goes through the map of mime types that was initialized 
		 * when reading properties and compares the ending of the requested
		 * file with the key of the map. If the requested file ends with key 
		 * then it returns the value held under that key.
		 * 
		 * @param requestedFile
		 * @return value for found mime type key or {@code null} if the
		 * file ends with type that is not supported by the mime type map
		 */
		private String determineMimeType(Path requestedFile) {
			if(requestedFile != null) {
				for(String key : mimeTypes.keySet()) {
					if(requestedFile.toString().endsWith(key)) {
						return mimeTypes.get(key);
					}
				}
			}
			return null;
		}
		
		
		/**
		 * User can input parameters in request. Those parameters will be
		 * parsed and saved in a params map that is going to be used
		 * for creating Request context.
		 * 
		 * @param parameterString {@code String} more paramaters should be concatenated with '&'
		 */
		private void parseParameters(String parameterString) {
			if(parameterString.isEmpty()) {
				return;
			}
			String[] parameters = parameterString.split("&");
			for(String param : parameters) {
				String[] keyValue = param.split("=");
				String firstParam = keyValue.length > 1 ? keyValue[0] : null;
				String secondParam = keyValue.length > 1 ? keyValue[1] : null;
				params.put(firstParam, secondParam);
			}
		}
		
		
		/**
		 * Method that reads and returns the headers from the user's request.
		 *	
		 * @return
		 * @throws IOException
		 */
		private List<String> readHeaders() throws IOException{
			
			byte[] request = readRequest(inputStream);
			if(request == null) {
				sendResponse(outputStream, 400, "Bad request");
			}
			String requestStr = new String(request, StandardCharsets.US_ASCII);
			List<String> headers = new ArrayList<String>();
			
			String currLine = null;
			for(String s : requestStr.split("\n")) {
				if(s.isEmpty()) {
					break;
				}
				char c = s.charAt(0);
				if(c == 9 || c == 32) {
					currLine += s;
				}
				else {
					if(currLine != null) {
						headers.add(currLine);
					}
					currLine = s;
				}
			}
			if(currLine.isEmpty()) {
				headers.add(currLine);
			}
			
			return headers;
			
		}
		
		
		/**
		 * Method that implements simple automat for reading the request that
		 * user made in browser.
		 * 
		 * @param inputStream
		 * @return byte[] of the users request
		 * @throws IOException
		 */
		private byte[] readRequest(InputStream inputStream) throws IOException{
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
l:			while(true) {
				int b = inputStream.read();
				if(b == -1) {
					return null;
				}
				if(b != 13) {
					bos.write(b);
				}
				switch(state){
					case 0:
						if(b == 13) {
							state = 1;
						}
						else if(b == 10) {
							state = 4;
						}
						break;
					case 1:
						if(b == 10) {
							state = 2;
						}
						else {
							state = 0;
						}
						break;
					case 2:
						if(b == 13) {
							state = 3;
						}
						else {
							state = 0;
						}
						break;
					case 3:
						if(b == 10) {
							break l;
						}
						else {
							state = 0;
						}
						break;
					case 4:
						if(b == 10) {
							break l;
						}
						else {
							state = 0;
						}
						break;
				}
			}
			return bos.toByteArray();
		}
		
		
		/**
		 * Method that returns the response to the user.
		 * This method is highly used for generating response 
		 * when seomthing bad has happened, the file does not exist or
		 * the file can't be accesed...
		 * 
		 * @param output {@code OutputStream}
		 * @param statusCode {@code int} status code of the response
		 * @param statusText {@code String} status text that desribes the response
		 * @throws IOException
		 */
		public void sendResponse(OutputStream output, int statusCode, String statusText) throws IOException {
			
			RequestContext rc = provideRequestContext();
			rc.setMimeType("text/plain");
			rc.setStatusCode(statusCode);
			rc.setStatusText(statusText);
			rc.write(statusCode + "  " + statusText);
		}

		
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		
		/**
		 * {@inheritDoc}
		 * If the urlPath has contains a value to which the workers 
		 * are mapped to, which can be visible in workers.properties file,
		 * then it calls the process request over that worker.
		 * If the urlPath contains the '/ext' we determine which worker
		 * should we call, and we make an instance of that worker and call 
		 * the process request over it.
		 */
		@Override
		public void internalDispatchRequest(String urlPath, boolean directCall) {
			
			if(urlPath.equals("/private") || urlPath.startsWith("/private/")) {
				if(directCall) {
					try {
						sendResponse(outputStream, 404, "Forbidden method");
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(urlPath.contains("/ext")) {
				
				String className = urlPath.replace("/ext/", "");
				className = "hr.fer.zemris.java.webserver.workers." + className;
				
				try {
					Class<?> referenceToClass = this.getClass().getClassLoader().
							loadClass(className);
					Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
					IWebWorker iww = (IWebWorker)newObject;
					iww.processRequest(provideRequestContext());
					return;
				} catch (Exception e) {
					
				}
				
				
			}
			
			
			if(workersMap.containsKey(urlPath)) {
				try {
					workersMap.get(urlPath).processRequest(provideRequestContext());
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			Path requestedFile = documentRoot.resolve(urlPath.substring(1));
			try {
				if(!requestedFile.startsWith(documentRoot)) {
					sendResponse(outputStream, 403, "Forbidden action");
					return;
				}
				
				if(!Files.isReadable(requestedFile)) {
					sendResponse(outputStream, 404, "File does not exist");
					return;
				}
				else {
					serveFile(outputStream, requestedFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		/**
		 * Method that checks whether request context has been instances
		 * already, if it wasn't then it makes a new instance of it and remembers it
		 * in a reference rc and returns the reference to it.
		 * 
		 * @return
		 */
		private RequestContext provideRequestContext() {
			if(rc == null) {
				rc = new RequestContext(outputStream, params, permParams, cookies, tempParams, this, SID);
			}
			return rc;
		}
		
		
	}
	
	
	/**
	 * Method that checks if the session is too old.
	 * As argument it gets the {@code SessionMapEntry} and if grabs the
	 * value validUntil from it. Then it creates a new date which returns
	 * the time when the date has been created(now) and we extract the miliseconds
	 * from it. If that value if larger than value validUntil then session has expired.
	 * 
	 * @param entry {@code SessionMapEntry}
	 * @return {@code true} if the session expired else returns {@code false}
	 */
	private boolean checkIfSessionTooOld(SessionMapEntry entry) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(new Date());
		if(gcal.getTimeInMillis() > entry.validUntil) {
			return true;
		}
		return false;
	}
	

}