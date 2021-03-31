package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class that implements interface {@code Command} and gives the 
 * implementation for scaling the effective length of a turtle.
 * 
 * @author Marko
 *
 */
public class ScaleCommand implements Command{

	private double factor;
	
	/**
	 * Contstructor.
	 * 
	 * @param factor {@code double} factor to be scaled effective length by
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		double newEffectiveLength = ctx.getCurrentState().getEffectiveLength() * factor;
		ctx.getCurrentState().setEffectiveLength(newEffectiveLength);
	}
}
