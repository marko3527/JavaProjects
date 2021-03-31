package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;


/**
 * Class that implements interface {@code Command} and gives implementation 
 * that calculates where the turtle should be next and draws the line 
 * between current position and calculated position with certain colour and
 * sets the turtle's current position to calculated position.
 * 
 * @author Marko
 *
 */
public class DrawCommand implements Command{
	
	private double step;
	
	/**
	 * Constructor.
	 * 
	 * @param step {@code double} length of the line to draw
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getCurrentPosition();
		double lengthOfLine = currentState.getEffectiveLength() * step;
		Vector2D scaledVectorToAdd = currentState.getAngle().scaled(lengthOfLine);
		Vector2D calculatedPosition = currentPosition.translated(scaledVectorToAdd);
		
		painter.drawLine(currentPosition.getX(),
				         currentPosition.getY(),
				         calculatedPosition.getX(),
				         calculatedPosition.getY(),currentState.getColor(),1f);
		currentState.setCurrentPosition(calculatedPosition);
	}
}
