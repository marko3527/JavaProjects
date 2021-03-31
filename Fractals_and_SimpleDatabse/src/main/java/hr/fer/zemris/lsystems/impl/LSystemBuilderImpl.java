package hr.fer.zemris.lsystems.impl;


import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;


/**
 * Class that implements interface {@code LSystemBuilder} and gives the 
 * ability to make the L system fractals by parsing text or setting directives
 * one by one.
 * 
 * @author Marko
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	
	/**
	 * Class that implements the functions for drawing one L system.
	 * 
	 * @author Marko
	 *
	 */
	static class LSystemImpl implements LSystem{
		
		private Vector2D origin;
		private double angle;
		private double effectiveLength;
		private double unitLength;
		private double unitLengthDegreeScaler;
		private Dictionary<String, String> productions;
		private Dictionary<String, Command> actions;
		private String axiom; 
		
		/**
		 * Contructors that gets everything need for one L system
		 * 
		 * @param origin {@code Vector2D} of the system
		 * @param angle {@code double} starting angle of the system
		 * @param unitLength {@code double} 
		 * @param unitLengthDegreeScaler {@code}
		 * @param productions {@code Dictionary} every production
		 * @param actions {@code Dictionary} every action
		 * @param axiom {@code String} starting string
		 */
		public LSystemImpl(Vector2D origin, double angle, 
						   double unitLength, double unitLengthDegreeScaler,
						   Dictionary<String, String> productions, 
						   Dictionary<String, Command> actions,
						   String axiom) {
			this.origin = origin;
			this.angle = angle;
			this.unitLength = unitLength;
			this.unitLengthDegreeScaler = unitLengthDegreeScaler;
			this.productions = productions;
			this.actions = actions;
			this.axiom = axiom;
		}

		
		/**
		 * Draw function creates a new context and a starting turtle state based on origin, angle and 
		 * calculated effective length.By calling function generate it gets generated string for 
		 * number of iterations and then for every character in that string looks if the command
		 * for that character exists in dictionary and if it does then calls the function 
		 * execute store under value for that symbol in a dictionary.
		 * 
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			effectiveLength = unitLength * Math.pow(unitLengthDegreeScaler, level);
			TurtleState startingState = new TurtleState(origin, angle, null, effectiveLength);
			ctx.pushState(startingState);
			String production = generate(level);
			
			for(String action : production.split("")) {
				Command command = actions.get(action);
				if(command != null) {
					command.execute(ctx, painter);
				}
			}
		}

		
		/**
		 * Generate function performs productions n times and returns the produces string.
		 * 
		 */
		@Override
		public String generate(int level) {
			String generatedString = axiom;
			
			for(int i = 0; i < level; i++) {
				StringBuilder sb = new StringBuilder();
				for(char symbol : generatedString.toCharArray()) {
					String product = productions.get("" + symbol);
					if(product == null) {
						sb.append(symbol);
					}
					else {
						sb.append(product);
					}
				}
				generatedString = sb.toString();
			}
			
			return generatedString;
		}
		
	}
	
	/**
	 * Dictionary for remembering the productions of the system
	 */
	private Dictionary<String, String> productions;
	
	/**
	 * Dictionary to remember which symbol represents which action
	 */
	private Dictionary<String, Command> actions;
	private double unitLength;
	private double angle;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private String axiom;
	
	/**
	 * Constructor. It initializes dictionaries and gives default values for the system.
	 * 
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<String, String>();
		actions = new Dictionary<String, Command>();
		unitLength = 0.1;
		angle = 0;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		axiom = "";
	}
	

	
	/**
	 * Creates a new LSystemImpl with all needed resources to perform the drawing of that system.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl(origin, angle, unitLength,unitLengthDegreeScaler,
							   productions, actions, axiom);
	}
	
	
	/**
	 * Looks for certain clue word in each string to know exactly which method to call
	 * to set the appropriate property of the L system.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] text) {
		
		for(String property : text) {
			property = property.replaceAll("\\s+", " ");
			String[] splitProperty = property.split(" ");
			
			if(property.contains("origin")) {
				if(splitProperty.length != 3) {
					throw new IllegalArgumentException("Setting origin takes just two arguments!");
				}
				double x = Double.parseDouble(splitProperty[1]);
				double y = Double.parseDouble(splitProperty[2]);
				this.setOrigin(x, y);
			}
			
			else if(property.contains("angle")) {
				if(splitProperty.length != 2) {
					throw new IllegalArgumentException("Setting angle takes just one argument!");
				}
				double angle = Double.parseDouble(splitProperty[1]);
				this.setAngle(angle);
			}
			
			//unitLengthDegree scaler can be provided as 1/2 or just one number 
			else if(property.contains("unitLengthDegreeScaler")) {
				double unitLengthDegreeScaler;
				
				if(property.contains("/")) {
					String prop = property.replace("unitLengthDegreeScaler", "");
					prop = prop.replaceAll("\\s+", "");
					String[] splitProp = prop.split("/");
					unitLengthDegreeScaler = Double.parseDouble(splitProp[0]) / Double.parseDouble(splitProp[1]);
				}
				else if(splitProperty.length == 2) {
					unitLengthDegreeScaler = Double.parseDouble(splitProperty[1]);
				}
				else {
					throw new IllegalArgumentException("Setting unit length degree scaler is not written properly!");
				}
				this.setUnitLengthDegreeScaler(unitLengthDegreeScaler);
			}
			
			else if(property.contains("unitLength")) {
				if(splitProperty.length != 2) {
					throw new IllegalArgumentException("Setting unit length takes just one argument!");
				}
				double unitLength = Double.parseDouble(splitProperty[1]);
				this.setUnitLength(unitLength);
			}
			
			else if(property.contains("axiom")) {
				if(splitProperty.length != 2) {
					throw new IllegalArgumentException("Setting axiom takes just one argument!");
				}
				this.setAxiom(splitProperty[1]);
			}
			
			else if(property.contains("command")) {
				char symbol;
				String command = "";
				if(property.contains("pop") || property.contains("push")) {
					if(splitProperty[1].length() != 1 || splitProperty.length != 3) {
						throw new IllegalArgumentException("Command directive not properly written!");
					}
					else{
						command = splitProperty[2];
					}
				}
				else {
					if(splitProperty[1].length() != 1 || splitProperty.length != 4) {
						throw new IllegalArgumentException("Command directive not properly written!");
					}
					else {
						command = splitProperty[2] + " " + splitProperty[3];
					}
				}
				symbol = splitProperty[1].charAt(0);
				this.registerCommand(symbol, command);
			}
			
			else if(property.contains("production")) {
				if(splitProperty.length != 3 || splitProperty[1].length() != 1) {
					throw new IllegalArgumentException("Command directive not properly written!");
				}
				this.registerProduction(splitProperty[1].charAt(0), splitProperty[2]);
			}
		}
		
		return this;
	}
	
	
	
	/**
	 * Sets the unitLength of the L system.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	
	
	
	/**
	 * Sets the origin of the L system.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new  Vector2D(x, y);
		return this;
	}
	
	/**
	 * Sets the angle of the L system.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	/**
	 * Sets the axiom of the L system.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * Sets the unit length degree scaler of the L system which is used for calculating 
	 * the effective length of the system
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * Registers a production in a dictionary.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put("" + symbol, production);
		return this;
	}
	
	/**
	 * In the given string looks for the clue words that indicates which command should be performed
	 * for given symbol and then stores the pair(symbol, command) to dictionary
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command = null;
		
		if(action.contains("draw")) {
			double step = Double.parseDouble(action.split(" ")[1]);
			if(step < 0 || step > 1) {
				throw new IllegalArgumentException("Draw can have argument between 0 and 1!");
			}
			command = new DrawCommand(step);
		}
		
		else if(action.contains("skip")) {
			double step = Double.parseDouble(action.split(" ")[1]);
			if(step < 0 || step > 1) {
				throw new IllegalArgumentException("Skip can have argument between 0 and 1!");
			}
			command = new SkipCommand(step);
		}
		
		else if(action.contains("scale")) {
			double scaler = Double.parseDouble(action.split(" ")[1]);
			if(scaler < 0) {
				throw new IllegalArgumentException("Scale function can't accept negative numbers!");
			}
			command = new ScaleCommand(scaler);
		}
		
		else if(action.contains("rotate")) {
			double angle = Double.parseDouble(action.split(" ")[1]);
			command = new RotateCommand(angle);
		}
		
		else if(action.contains("push")) {
			command = new PushCommand();
		}
		
		else if(action.contains("pop")) {
			command = new PopCommand();
		}
		
		else if(action.contains("color")) {
			String color = action.split(" ")[1];
			if(color.length() != 6) {
				throw new IllegalArgumentException("Color is not correctly provided!");
			}
			Color newColor = toRGB(color);
			command = new ColorCommand(newColor);
			
		}
		
		actions.put("" + symbol, command);
		return this;
	}
	
	
	/**
	 * Method that takes string of color written in hex format and 
	 * turns every color to RGB color
	 * 
	 * @param colorStr {@code String} color written in hexFormat
	 * @return {@code Color}
	 */
	private static Color toRGB(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 0, 2 ), 16),
	            Integer.valueOf( colorStr.substring( 2, 4 ), 16),
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16));
	}
	

}
