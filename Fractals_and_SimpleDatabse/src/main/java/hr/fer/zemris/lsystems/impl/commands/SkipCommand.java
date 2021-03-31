package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Class that implements interface {@code Command} and calculates where
 * the turtle should be next and sets that position to the position
 * of the state on top of the stack
 * 
 * @author Marko
 *
 */
public class SkipCommand implements Command{
	
	private double step;
	
	
	/**
	 * Constructor.
	 * 
	 * @param step {@code double} length of the line to move 
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getCurrentPosition();
		double lengthOfLine = currentState.getEffectiveLength() * step;
		Vector2D scaledVectorToAdd = currentState.getAngle().scaled(lengthOfLine);
		Vector2D calculatedPosition = currentPosition.translated(scaledVectorToAdd);
		currentState.setCurrentPosition(calculatedPosition);
	}

}
