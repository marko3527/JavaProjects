package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.environment.Environment;
import hr.fer.zemris.java.hw06.shell.util.ShellUtil;


/**
 * Class that implements ShellCommand interface and give implementation
 * for copying the content of file into another file or in a file 
 * in directory with original file name. If destination file exist
 * it asks user for permission to rewrite its content.
 * 
 * @author Marko
 *
 */
public class CopyShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] commandArgs = ShellUtil.splitArguments(arguments, true);
		
		if(!checkArguments(commandArgs, env)) {
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream sourceFile = Files.newInputStream(Paths.get(commandArgs[0]))){
			String srcFileName = new File(commandArgs[0]).getName();
			boolean destFileExist = new File(commandArgs[1]).exists();
			
			OutputStream destFile;
			File file = new File(commandArgs[1]);
			if(file.isDirectory()) {
				destFile = Files.newOutputStream(Paths.get(
						commandArgs[1] + "\\" + srcFileName
				));
			}
			
			else {
				destFile = Files.newOutputStream(Paths.get(commandArgs[1]));
				
				if(destFileExist) {
					env.write("The destination file already exist! Do you want to rewrite its content?\n");
					String userAgreement = "";
					while(!userAgreement.equals("Y") && !userAgreement.equals("N")) {
						env.write("Type Y for yes and N for no.");
						env.writeln("");
						userAgreement = env.readLine();
					}
					if(userAgreement.equals("N")){
						env.write("Stopping the copy command!");
						return ShellStatus.CONTINUE;
					}
				}
			}
			
			byte[] buffer = new byte[1024];
			int lengthOfRead;
			while((lengthOfRead = sourceFile.read(buffer)) > 0) {
				try {
					destFile.write(buffer, 0, lengthOfRead);
				} catch (IOException e) {
					env.write("There was a problem with writing into destination file!");
					return ShellStatus.CONTINUE;
				}
			}
			
			destFile.close();
			
		} catch (IOException e) {
			env.write("There was a problem with openning a source file!");
			return ShellStatus.CONTINUE;
		}
		env.write("File copied.");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> linesOfDescription = new LinkedList<>();
		
		linesOfDescription.add("Command 'copy' copies the content of the one file");
		linesOfDescription.add("into second file. It expects two arguments: first");
		linesOfDescription.add("source file name and second destination file name.");
		linesOfDescription.add("If destination exist command asks user permission ");
		linesOfDescription.add("to rewrite file. And if second argument is directory");
		linesOfDescription.add("command assumes that user wants to copy the original");
		linesOfDescription.add("file into that directory using original file name.");
		
		return linesOfDescription;
	}
	
	
	
	/**
	 * Method that checks whether the given arguments are appropriate for copy
	 * command.
	 * 
	 * @param commandArgs arguments
	 * @param env 
	 * @return {@code true} if number of arguments is appropriate
	 * 		   {@code false} if number of arguments is not appropriate
	 */
	private boolean checkArguments(String[] commandArgs, Environment env) {
		if(commandArgs.length != 2) {
			env.write("Command 'copy' takes two arguments!");
			return false;
		}
		File sourceFile = new File(commandArgs[0]);
		if(sourceFile.isDirectory()) {
			env.write("First argument of the command 'copy' needs to be file!");
			return false;
		}
		return true;
	}
	
}
