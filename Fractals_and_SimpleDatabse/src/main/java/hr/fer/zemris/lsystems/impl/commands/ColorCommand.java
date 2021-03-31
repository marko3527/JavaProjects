package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class that implements interface {@code Command} and gives implementation
 * for setting the colour of the current line.
 * 
 * @author Marko
 *
 */
public class ColorCommand implements Command{
	
	private Color color;
	
	
	/**
	 * Constructor.
	 * 
	 * @param color {@code Color} to set the line on
	 * @throws NullPointerException if the argument is null reference
	 */
	public ColorCommand(Color color) {
		if(color == null) {
			throw new NullPointerException("Color can't be null reference!");
		}
		
		this.color = color;
	}
	
	
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
