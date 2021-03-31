package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface for executing the command. It defines just one method called
 * execute.
 * 
 * @author Marko
 *
 */
public interface Command {

	/**
	 * Execetution method of the command
	 * 
	 * @param ctx
	 * @param painter
	 */
	public void execute(Context ctx, Painter painter);
}
