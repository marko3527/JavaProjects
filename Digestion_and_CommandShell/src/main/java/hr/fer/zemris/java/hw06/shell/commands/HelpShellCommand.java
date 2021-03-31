package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.environment.Environment;
import hr.fer.zemris.java.hw06.shell.util.ShellUtil;


/**
 * Class that implements ShellCommand and offers implementation for getting
 * all the possible command in my shell or getting description for 
 * one of them.
 * 
 * @author Marko
 *
 */
public class HelpShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, ShellCommand> commands = env.commands();
		StringBuilder sb = new StringBuilder();
		
		if(arguments.length() == 0) {
			for(String key : commands.keySet()) {
				sb.append("\n" + commands.get(key).getCommandName());
			}
			env.write(sb.toString().replaceFirst("\n", ""));
		}
		
		else {
			
			String[] commandArgs = ShellUtil.splitArguments(arguments, false);
			if(!checkArguments(commandArgs, env)) {
				return ShellStatus.CONTINUE;
			}
			
			else {
				ShellCommand command = commands.get(commandArgs[0]);
				
				if(command == null) {
					env.write("Unknown command!");
					return ShellStatus.CONTINUE;
				}
				createNameAndDescription(sb, command);
				env.write(sb.toString());
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> linesOfDescription = new LinkedList<>();
		
		linesOfDescription.add("Command 'help'  can be started with no arguments");
		linesOfDescription.add("and with one argument. If started with no arguments");
		linesOfDescription.add("it lists the names of supported commands,and otherwise");
		linesOfDescription.add("it prints name and the description of the command.");
		
		return linesOfDescription;
	}
	
	
	/**
	 * Method that checks whether the given arguments are appropriate for help
	 * command.
	 * 
	 * @param commandArgs arguments
	 * @param env 
	 * @return {@code true} if number of arguments is appropriate
	 * 		   {@code false} if number of arguments is not appropriate
	 */
	private boolean checkArguments(String[] commandArgs, Environment env) {
		if(commandArgs.length == 0 || commandArgs.length == 1) {
			return true;
		}
		env.write("Command 'help' takes no arguments or just one argument!");
		return false;
	}
	
	
	/**
	 * Method that builds the output for command help if there was one 
	 * argument. That means the user wants a description of given command.
	 * 
	 * @param sb {@code StringBuilder}
	 * @param command {@code ShellCommand} command to make output of
	 */
	private void createNameAndDescription(StringBuilder sb, ShellCommand command) {
		
		sb.append("command name: " + command.getCommandName() + "\n");
		List<String> description = command.getCommandDescription();
		sb.append("command description: " + description.get(0));
		
		for(int i = 1; i < description.size(); i++) {
			sb.append("\n                     " + description.get(i));
		}
	}

}
