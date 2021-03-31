package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * Class that represents the Phong's lighting model for 3D scenes.
 * It offers the method for calculating ambiental light, 
 * diffused light and reflected light. 
 * 
 * @author Marko
 *
 */
public class PhongsModel {
	
	//ambiental coefficients
	private double kABlue;
	private double kARed;
	private double kAGreen;
	
	//diffuse coefficients
	private double kDBlue; 
	private double kDRed;
	private double kDGreen; 
	
	//reflective coefficients
	private double kRBlue; 
	private double kRRed;
	private double kRGreen;
	private double reflectiveCoefficient;
	
	
	/**
	 * Method that sets the coefficients for ambient light component
	 * 
	 * @param kABlue blue component
	 * @param kARed red component
	 * @param kAGreen green component
	 */
	public void setAmbientCoefficient(double kABlue, double kARed, double kAGreen) {
		this.kABlue = kABlue;
		this.kARed = kARed;
		this.kAGreen = kAGreen;
	}
	
	
	/**
	 * Method that sets the coefficients for diffuse light component
	 * 
	 * @param kDBlue blue component
	 * @param kDRed red component
	 * @param kDGreen green component
	 */
	public void setDiffuseCoefficient(double kDBlue, double kDRed, double kDGreen) {
		this.kDBlue = kDBlue;
		this.kDRed = kDRed;
		this.kDGreen = kDGreen;
	}
	
	
	/**
	 * Method that sets the coefficients for reflective light component
	 * 
	 * @param kRBlue blue component
	 * @param kRRed red component
	 * @param kRGreen green component
	 * @param n describes the reflective features of the surface
	 */
	public void setReflectiveCoefficient(double kRBlue, double kRRed, double kRGreen, double n) {
		this.kRBlue = kRBlue;
		this.kRRed = kRRed;
		this.kRGreen = kRGreen;
		this.reflectiveCoefficient = n;
	}
	
	
	/**
	 * Method that returns the ambient component of the light
	 * 
	 * @param Ia intensity of ambient light
	 * @return
	 */
	public short[] calculateAmbient(double Ia) {
		short red = (short) (this.kARed * Ia);
		short green = (short) (this.kAGreen * Ia);
		short blue = (short) (this.kABlue * Ia);
		
		return new short[] {red, green, blue};
	}
	
	
	/**
	 * Method that calculates the diffuse component of lighting
	 * base on the intensity of light and intersection point.
	 * 
	 * @param light {@code LightSource} 
	 * @param intersectionPoint {@code RayIntersection} point of intersection
	 * @return array of red, green, blue diffuse component
	 */
	public short[] calculateDiffuse(LightSource light, RayIntersection intersectionPoint) {
		Point3D normal = intersectionPoint.getNormal();
		Point3D lightVector = Ray.fromPoints(light.getPoint(), intersectionPoint.getPoint()).direction;
		
		double LN = lightVector.scalarProduct(normal.negate());
		short red = (short) (light.getR() * this.kDRed * LN);
		short green = (short) (light.getG() * this.kDGreen * LN);
		short blue = (short) (light.getB() * this.kDBlue * LN);
		
		return new short[] {red, green, blue};
		
	}
	
	
	/**
	 * Method that calculates the reflective component of the lighting.
	 * 
	 * @param light {@code LightSource} 
	 * @param intersectionPoint {@code RayIntersection} point of intersection
	 * @param ray 
	 * @return array of red, green, blue diffuse component
	 */
	public short[] calculateReflective(LightSource light, RayIntersection intersectionPoint, Point3D ray) {
		Point3D normal = intersectionPoint.getNormal();
		Point3D lightVector = Ray.fromPoints(intersectionPoint.getPoint(), light.getPoint()).direction;
		
		
		
		Point3D helpVector = normal.scalarMultiply(lightVector.scalarProduct(normal)*2);
		Point3D R = helpVector.sub(lightVector);
		R.modifyNormalize();
		
		double RV = Math.pow(R.scalarProduct(ray), this.reflectiveCoefficient);
		
		
		short red = (short) (light.getR() * this.kRRed * RV);
		short green = (short) (light.getG() * this.kRGreen * RV);
		short blue = (short) (light.getB() *  this.kRBlue * RV);
		
		return new short[] {red, green, blue};
		
	}
	

}
