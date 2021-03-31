package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.environment.Environment;
import hr.fer.zemris.java.hw06.shell.util.ShellUtil;


/**
 * Class that implements ShellCommand and offers implementation 
 * to execute method for dumping content of the file into console.
 * 
 * @author Marko
 *
 */
public class CatShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] commandArgs = ShellUtil.splitArguments(arguments, true);
		
		if(!checkArguments(commandArgs, env)) {
			return ShellStatus.CONTINUE;
		}
		
		try {
			BufferedReader br;
			
			if(commandArgs.length == 2) {
				Charset charset = Charset.forName(commandArgs[1]);
				br = new BufferedReader(new FileReader(commandArgs[0],charset));
			}
			else {
				br = new BufferedReader(new FileReader(commandArgs[0],Charset.defaultCharset()));
			}
			
			StringBuilder sb = new StringBuilder();
			br.lines().forEach(line -> {
				sb.append("\n" + line);
			});
			env.write(sb.toString().replaceFirst("\n", ""));
			br.close();
			
		}catch (UnsupportedCharsetException | UnsupportedEncodingException ex) {
			env.write("Charset is not properly provided!");
		}catch(IOException ex) {
			env.write("There has been problem with the provided file!\n"
					+ "Command 'cat' takes one argument, path to file, not directory!");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> linesOfDescription = new LinkedList<>();
		linesOfDescription.add("Command 'cat' opens given file and writes its");
		linesOfDescription.add("content to console. It takes two arguments, first");
		linesOfDescription.add("argument is path to file, and second is charset");
		linesOfDescription.add("that should be used by reading the file. If charset");
		linesOfDescription.add("is not provided default charset is being used.");
		
		return linesOfDescription;
	}
	
	
	/**
	 * Method that checks whether the given arguments are appropriate for cat
	 * command.
	 * 
	 * @param commandArgs arguments
	 * @param env 
	 * @return {@code true} if arguments are appropriate
	 * 		   {@code false} if arguments are not appropriate
	 */
	private boolean checkArguments(String[] commandArgs, Environment env) {
		
		if(commandArgs.length != 1 && commandArgs.length != 2) {
			env.write("Command 'cat' takes one or two arguments!");
			return false;
		}
		
		else {
			try {
				Paths.get(commandArgs[0]);
			} catch (InvalidPathException | NullPointerException ex) {
				env.write("First argument is not a valid path or the file does not exist!");
				return false;
			}
		}
		
		return true;
	}

	
}
