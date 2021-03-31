package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements interface {@code Command} and gives implementation for
 * copying the current state and pushing it to stack of states
 * 
 * @author Marko
 *
 */
public class PushCommand implements Command{

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copiedState = ctx.getCurrentState().copy();
		ctx.pushState(copiedState);
		
	}

}
