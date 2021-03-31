package hr.fer.zemris.java.servleti.trigonometry;

import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.List;

/**
 * This class offers methods for calculating sin and cos
 * values of angles between bounds.
 * 
 * @author Marko
 *
 */
@JavaBean
public class TrigonometryFunctions {
	
	private Integer lowerBound;
	private Integer upperBound;
	
	
	/**
	 * Constructor. If lowerBound is null it sets it to 0. 
	 * If upperBound is null it sets the upperBound to 360.
	 * Then if the lowerBound is higher than upperBound we switch their values
	 * and if the upper bound is higher that (lowerBound+720) it sets the value of
	 * upper bound to (lowerBound + 720=
	 * 
	 * @param lowerBound
	 * @param upperBound
	 */
	public TrigonometryFunctions(Integer lowerBound, Integer upperBound) {
		this.lowerBound = lowerBound == null ? 0 : lowerBound;
		this.upperBound = upperBound == null ? 360 : upperBound;
		
		if(this.lowerBound > this.upperBound) {
			int help = this.lowerBound;
			this.lowerBound = this.upperBound;
			this.upperBound = help;
		}
		else if(this.upperBound > this.lowerBound + 720) {
			this.upperBound = this.lowerBound + 720;
		}
	}
	
	
	/**
	 * Returns all integers between lower and upper bound.
	 * 
	 * @return {@code List<Integer>} all integer angles withing bounds.
	 */
	public List<Integer> getAngles(){
		List<Integer> listOfAngles = new ArrayList<>();
		
		int index = lowerBound;
		for(;lowerBound <= upperBound; lowerBound++) {
			listOfAngles.add(lowerBound);
		}
		
		lowerBound = index;
		return listOfAngles;
	}
	
	
	/**
	 * Method that returns the sin values of the 
	 * all integer angles within bounds.
	 * 
	 * @return {@code List<Double>}
	 */
	public List<Double> getSinValues(){
		List<Double> listOfSinValues = new ArrayList<>();

		int index = lowerBound;
		for(;lowerBound <= upperBound; lowerBound++) {
			listOfSinValues.add(Math.sin(Math.toRadians(lowerBound)));
		}
		
		lowerBound = index;
		return listOfSinValues;
	}
	
	
	/**
	 * Method that returns the cos values of all integer
	 * angles within bounds.
	 * 
	 * @return {@code List<Double>}
	 */
	public List<Double> getCosValues(){
		
		List<Double> listOfCosValues = new ArrayList<>();

		int index = lowerBound;
		for(;lowerBound <= upperBound; lowerBound++) {
			listOfCosValues.add(Math.cos(Math.toRadians(lowerBound)));
		}
		
		lowerBound = index;
		return listOfCosValues;
	}

}
