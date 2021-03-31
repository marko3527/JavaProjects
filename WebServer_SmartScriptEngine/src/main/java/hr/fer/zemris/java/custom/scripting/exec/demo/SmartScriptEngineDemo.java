package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;


/**
 * Demo that shows the usage of the smart script engine used
 * for executing simple smart script programs.
 * 
 * @author Marko
 *
 */
public class SmartScriptEngineDemo {
	
	
	/**
	 * Main method from which the program starts its execution.
	 * 
	 * @param args {@code String[]} command line arguments
	 */
	public static void main(String[] args) {
		
		String p = new File("src\\main\\resources\\FibonacciHTML.txt").getAbsolutePath();
		Path path = Paths.get(p);
		
		try {
			String docBody = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<>();
			Map<String,String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
			parameters.put("a", "4");
			parameters.put("b", "2");
			persistentParameters.put("brojPoziva", "3");
			
			OutputStream os = Files.newOutputStream(Paths.get("webroot\\index.html"));
			
			RequestContext rc = new RequestContext(os, parameters, persistentParameters, cookies);
			
			new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
					rc).
			execute();
			
		} catch (IOException e) {
		}
		
	}

}
