package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**
 * Class that implements interface {@code Command} and gives the implementation
 * for rotating the turtle for certain angle
 * 
 * @author Marko
 *
 */
public class RotateCommand implements Command{
	
	private double angle;
	
	/**
	 * Constructor. Angle is expected to be in degrees, so [-360,360]
	 * 
	 * @param angle {@code double} angle to rotate turtle's direction for
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getAngle().rotate(Math.toRadians(angle));
	}

}
