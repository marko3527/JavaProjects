package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**
 * The class that implements interface {@code Command} and gives the 
 * implementation for poping one state from the stack of states.
 * 
 * @author Marko
 *
 */
public class PopCommand implements Command{

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
