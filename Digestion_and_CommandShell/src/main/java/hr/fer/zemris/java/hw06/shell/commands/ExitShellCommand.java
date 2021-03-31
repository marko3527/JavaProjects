package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.environment.Environment;

/**
 * Class that implements ShellCommand and offers execution for 
 * exiting from myshell.
 * 
 * @author Marko
 *
 */
public class ExitShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> linesOfDescription = new LinkedList<>();
		
		linesOfDescription.add("Command 'exit' just returns the TERMINATE state");
		linesOfDescription.add("as the product of execution.");
		
		return linesOfDescription;
	}

}
