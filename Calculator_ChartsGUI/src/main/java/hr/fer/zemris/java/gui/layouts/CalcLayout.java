package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;


/**
 * Custom layout manager that will be used specifically
 * for custom calculator application.Conceptually manager is working
 * with 5 rows and 7 columns, which is fixed and can't be changed.
 * All heights of rows or width of columns do not need to be equal 
 * because of the rounding numbers, 
 * but the differences will be uniformly distributed.
 * There can be added 31 components because the first component covers 
 * the first five spots so adding component to (1,2)...(1,5) will result
 * with exception.
 * 
 * @author Marko
 *
 */
public class CalcLayout implements LayoutManager2{
	
	//space between rows and columns
	private int offsetBetweenComps;
	Map<RCPosition, Component> mapOfComponents;
	
	
	/**
	 * Constructor.
	 * 
	 * @param offsetBetweenComps {@code int} space between rows and columns
	 */
	public CalcLayout(int offsetBetweenComps) {
		super();
		this.offsetBetweenComps = offsetBetweenComps;
		mapOfComponents = new HashMap<RCPosition, Component>();
	}
	
	/**
	 * Constructor that calls the other constructor and sets the spaces
	 * between rows and columns to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("You can't call this method!");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for(RCPosition key : mapOfComponents.keySet()) {
			if(mapOfComponents.get(key) == comp) {
				mapOfComponents.remove(key, comp);
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		if(mapOfComponents.isEmpty()) {
			return null;
		}
		return determineSize("max");
		
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return determineSize("min");
	}

	@Override
	public void layoutContainer(Container parent) {
		double compHeight = (double) parent.getHeight() / 5;
		double compWidth = (double) parent.getWidth() / 7 ;
		
		Bounds[] xBounds = calculateBounds(compWidth, parent.getWidth(), "x");
		Bounds[] yBounds = calculateBounds(compHeight, parent.getHeight(), "y");
		
		
		for(RCPosition position : mapOfComponents.keySet()) {
			Bounds xBound = xBounds[position.getColumnIndex() - 1];
			Bounds yBound = yBounds[position.getRowIndex() - 1];
			int xStartingPos = (int)Math.round(xBound.getxStartPosition());
			int xWidth = (int)xBound.getxWidth();
			int yStartingPos = (int)Math.round(yBound.getxStartPosition());
			int height = (int)yBound.getxWidth();
			
			if(position.getColumnIndex() == 1 && position.getRowIndex() == 1) {
				xWidth = (int) (xBounds[4].getxStartPosition() + xBounds[4].getxWidth());
			}
			if(position.getColumnIndex() == 7) {
				xWidth += offsetBetweenComps;
			}
			
			mapOfComponents.get(position).setBounds(xStartingPos, yStartingPos, 
													xWidth, height);
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) {
			throw new NullPointerException("You provided null reference!");
		}
		else if(constraints instanceof RCPosition) {
			addComponentToMap(comp, (RCPosition) constraints);
		}
		else if(constraints instanceof String) {
			RCPosition position = null;
			try {
				position = RCPosition.parse((String)constraints);
			}catch (CalcLayoutException ex) {
				throw new IllegalArgumentException(ex.getMessage());
			}
			if(position != null) {
				addComponentToMap(comp, position);
			}
			
		}
		else {
			throw new IllegalArgumentException("Constraints argument not instance of String or RCPosition!");
		}
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return determineSize("max");
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}
	

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * Method that registers a component under key position.
	 * 
	 * @param comp {@code Component} component to be registered
	 * @param position {@code RCPosition} key that represent the row, column indexes 
	 * of the component in the layout
	 */
	private void addComponentToMap(Component comp, RCPosition position) {
		if(mapOfComponents.containsKey(position)) {
			if(mapOfComponents.get(position) == null) {
				mapOfComponents.put(position, comp);
			}
			else {
				throw new CalcLayoutException("Component is already defined with constraints: " + position);
			}
		}
		else {
			mapOfComponents.put(position, comp);
		}
	}
	
	
	/**
	 * Method that calculates the  starting position for component
	 * and size(width/height) of the component. It checks whether the components with
	 * given width will fit into parent width and if not then it subtracts
	 * 1 width from width and distributes that new width evenly.
	 * 
	 * @param compWidth {@code double} width of component
	 * @param parentWidth {@code double} width of the parent
	 * @return {@code Bounds[]} 
	 */
	private Bounds[] calculateBounds(double comp, double parent, String axis) {
		int numberOfElems = 0;
		numberOfElems = axis.equals("y") ? 5 : 7;
		
		Bounds[] positions = new Bounds[numberOfElems];
		
		double diff = Math.round(comp) * numberOfElems - parent;
		positions[0] = new Bounds(0, Math.round(comp - offsetBetweenComps));
		if(diff <= 0) {
			for(int i = 1; i < numberOfElems - 1; i++) {
				positions[i] = new Bounds(i * comp,
											Math.round(comp - offsetBetweenComps));
			}
		}
		else {
			int distributionDensity = (int) (numberOfElems / diff);
			for(int i = 1; i < numberOfElems - 1; i++) {
				double width = comp;
				double startingPos = positions[i - 1].getxStartPosition() +  offsetBetweenComps +
									 positions[i - 1].getxWidth();
				if(i % distributionDensity != 0) {
					width = width - 1;
				}
				width = width - offsetBetweenComps;
				positions[i] = new Bounds(startingPos,Math.round(width));
			}
		
		}
		positions[numberOfElems - 1] = new Bounds(parent - Math.round(comp),
								offsetBetweenComps + Math.round(comp));
		return positions;
	}
	
	
	/**
	 * Method that goes through all the components and calls the methods
	 * for finding largest or smallest pref size base on the type given in 
	 * argument. Type can be "max" for largest and "min" for smallest.
	 * 
	 * @param type {@code String} how to compare two dimensions
	 * @return {@code Dimension} calculated pref size for layout
	 */
	private Dimension determineSize(String type) {
		Dimension size = new Dimension(0, 0);
		
		for(RCPosition position : mapOfComponents.keySet()) {
			Component comp = mapOfComponents.get(position);
			if(comp != null) {
				Dimension prefferedSize = comp.getPreferredSize();
				if(position.getRowIndex() == 1 && position.getColumnIndex() == 1) {
					int width = (prefferedSize.width - 4 * offsetBetweenComps) / 5;
					prefferedSize = new Dimension(width, prefferedSize.height);
				}
				if(type.equals("max")) {
					size = getLarger(size, prefferedSize);
				}
				else if(type.equals("min")) {
					size = getSmaller(size, prefferedSize);
				}
			}
			
		}
		
		return new Dimension(size.width * 7 + 6 * offsetBetweenComps,
							 size.height * 5 + 4 * offsetBetweenComps);
	}
	
	
	/**
	 * Method that finds the largest preffered dimension amongst all components.
	 * 
	 * @param size {@code Dimension} current largest size
	 * @param prefferedSize {@code Dimension} potential largest size
	 * @return {@code Dimension} new largest size
	 */
	private Dimension getLarger(Dimension size, Dimension prefferedSize) {
		if(prefferedSize.width > size.width) {
			size.width = prefferedSize.width;
		}
		if(prefferedSize.height > size.height) {
			size.height = prefferedSize.height;
		}
		return size;
	}
	
	
	/**
	 * Method that finds the smallest preffered dimension amongst all components.
	 * 
	 * @param size {@code Dimension} current smallest size
	 * @param prefferedSize {@code Dimension} potential smallest size
	 * @return {@code Dimension} new smallest size
	 */
	private Dimension getSmaller(Dimension size, Dimension prefferedSize) {
		if(prefferedSize.width < size.width) {
			size.width = prefferedSize.width;
		}
		if(prefferedSize.height < size.height) {
			size.height = prefferedSize.height;
		}
		return size;
	}

}
