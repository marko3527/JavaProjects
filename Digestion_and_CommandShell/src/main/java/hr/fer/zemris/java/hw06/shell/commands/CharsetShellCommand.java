package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.environment.Environment;

/**
 * Class that extends ShellCommand and offers implmentation for method execute
 * for listing all possible charsets for current Java platform. It takes no arguments.
 * 
 * @author Marko
 *
 */
public class CharsetShellCommand implements ShellCommand{
	

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!checkArguments(arguments, env)) {
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String,Charset> mapOfCharsets = Charset.availableCharsets();
		for(String key : mapOfCharsets.keySet()) {
			env.write("  ====> \"" + mapOfCharsets.get(key) + "\"");
			if(!mapOfCharsets.lastKey().equals(key)) {
				env.write("\n");
			}
		}
		return ShellStatus.CONTINUE;
	}


	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> linesOfDescription = new LinkedList<>();
		linesOfDescription.add("Command 'charsets' is used to list all of the");
		linesOfDescription.add("supported charsets for current Java platform.");
		linesOfDescription.add("A single charset name is written per line.");
		linesOfDescription.add("This command takes no arguemnts.");
		
		return linesOfDescription;
	}
	
	/**
	 * Method that checks whether the given arguments are appropriate for charsets
	 * command.
	 * 
	 * @param commandArgs arguments
	 * @param env 
	 * @return {@code true} if arguments are appropriate
	 * 		   {@code false} if arguments are not appropriate
	 */
	private boolean checkArguments(String commandArgs, Environment env) {
		if(commandArgs.length() != 0) {
			env.write("Command 'charsets' takes no arguments!");
			return false;
		}
		return true;
	}
	
}
