package hr.fer.zemris.java.raytracer;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.PhongsModel;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Class that offers methods that will help to work with ray caster, such as
 * calculating screen point, calculating corner point, calculating axis, finding 
 * closest intersection in set of objects...
 * 
 * @author Marko
 *
 */
public class RayCasterUtil {
	
	/**
	 * Method that calculates screenCorner base od x and y axis.
	 * 
	 * @param view
	 * @param xAxis
	 * @param yAxis
	 * @param horizontal
	 * @param vertical
	 * @return {@code Point3D} calculated screen corner
	 */
	public static Point3D calculateScreenCorner(Point3D view, Point3D xAxis, Point3D yAxis, double horizontal,
			double vertical) {
		return view.sub(xAxis.scalarMultiply(horizontal/2)).
				   	add(yAxis.scalarMultiply(vertical/2));
	}
	
	
	/**
	 * Calcultes a screen point based on screenCorner. This point
	 * will be directional point for ray.
	 * 
	 * @param xAxis
	 * @param yAxis
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param horizontal
	 * @param vertical
	 * @param screenCorner
	 * @return {@code Point3D} calculated screenPoint
	 */
	public static Point3D calculateScreenPoint(Point3D xAxis, Point3D yAxis, int x, int y, int width, int height,
			double horizontal, double vertical, Point3D screenCorner) {
		Point3D xPartPoint = xAxis.scalarMultiply(horizontal * x/(width - 1));
		Point3D yPartPoint = yAxis.scalarMultiply(vertical * y/(height - 1));
		
		return screenCorner.add(xPartPoint).sub(yPartPoint);	
	}
	
	
	/**
	 * Method that finds the closest intersection. It creates a list of
	 * intersections that ray makes with every object in scene and then 
	 * determines which of those intersection is the closest by looking at 
	 * their distance to eye vector.
	 * 
	 * @param scene {@code Scene}
	 * @param ray {@code Ray}
	 * @return {@code RayIntersection} closest intersection with ray
	 */
	public static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		List<GraphicalObject> objectsInScene = scene.getObjects();
		List<RayIntersection> intersections = new ArrayList<>();
		
		
		for(GraphicalObject object : objectsInScene) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if(intersection != null) {
				intersections.add(object.findClosestRayIntersection(ray));
			}
			
		}
		
		if(intersections.size() > 0) {
			RayIntersection closest = intersections.get(0);
			
			for(int i = 1; i < intersections.size(); i++) {
				RayIntersection candidate = intersections.get(i);
				if(candidate != null && closest != null) {
					if(candidate.getDistance() < closest.getDistance()) {
						closest = candidate;
					}
				}
			}
			
			return closest;
		}
		
		else {
			return null;
		}
		
	}
	
	
	/**
	 * Method that is calculating the x, y, z axis based on the position
	 * of the viewer, the viewer point and the vector that represents 
	 * upper view, viewUp vector
	 * 
	 * @param eye {@code Point3D} position of the viewer
	 * @param view {@code Point3D} viewPoint 
	 * @param viewUp {@code Point3D} vector that "looks" up
	 * @return {@code Point3D[]} array of axis(x, y, z)
	 */
	public static Point3D[] calculateAxis(Point3D eye, Point3D view, Point3D viewUp) {
		
		Point3D vectorOG = view.sub(eye);
		Point3D yAxis = viewUp.sub(vectorOG.scalarMultiply(vectorOG.scalarProduct(viewUp)));
		yAxis = yAxis.normalize();
		
		Point3D xAxis = vectorOG.vectorProduct(yAxis);
		xAxis = xAxis.normalize();
		
		Point3D zAxis = yAxis.vectorProduct(xAxis);
		zAxis = zAxis.normalize();
		
		return new Point3D[] {xAxis, yAxis, zAxis};
		
	}
	
	
	/**
	 * Method that calculates the colour for given intersection point S. Method goes
	 * through all light sources and adds its diffuse and reflected component to 
	 * every intersection point. To test if the color should be calculated for 
	 * light source, method calculates the ray from intersection point to light source
	 * and finds if it has any intersections with other objects. If that intersection
	 * with other object exist(let's call it S') then method compares
	 * its distance to the light source point with distance from intersectionPoint S
	 * to light source point. This distance is important for determining
	 * whether some object is blocking light, so if it is then we don't need to calculate
	 * components for that source.
	 * 
	 * @param scene {@code Scene} with objects
	 * @param intersection {@code RayIntersection} intersection point S
	 * @param rayDirection {@code Point3D} vector from intersection to viewer's eye
	 * @return array of calculated colours (red, green, blue)
	 */
	public static short[] constructColor(Scene scene, RayIntersection intersection, Point3D rayDirection) {
		short[] rgb = {0, 0, 0};
		
		List<LightSource> lights = scene.getLights();
		double kDBlue = intersection.getKdb();
		double kDRed = intersection.getKdr();
		double kDGreen = intersection.getKdg();
		
		double kRBlue = intersection.getKrb();
		double kRRed = intersection.getKrr();
		double kRGreen = intersection.getKrg();
		double reflectiveCoefficient = intersection.getKrn();
		
		PhongsModel phong = new PhongsModel();
		phong.setAmbientCoefficient(1, 1, 1);
		phong.setDiffuseCoefficient(kDBlue, kDRed, kDGreen);
		phong.setReflectiveCoefficient(kRBlue, kRRed, kRGreen, reflectiveCoefficient);
		
		short[] ambient = phong.calculateAmbient(15);
		rgb[0] += ambient[0];
		rgb[1] += ambient[1];
		rgb[2] += ambient[2];
		
		
		for(LightSource light : lights) {
			
			Ray ray = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			RayIntersection closest = RayCasterUtil.findClosestIntersection(scene, ray);
			
			double intersectionSource = RayCasterUtil.calculateDistance(
						intersection.getPoint(), light.getPoint()
			);
			if(closest != null) {
				double sub = closest.getDistance() - intersectionSource;
				if(closest != null && sub < -3.5E-5) {
					continue;
				}
				else {

					short[] diffuse = phong.calculateDiffuse(light, intersection);
					short[] reflective = phong.calculateReflective(light, intersection, rayDirection);
					
					rgb[0] += diffuse[0] + reflective[0];
					rgb[1] += diffuse[1] + reflective[1];
					rgb[2] += diffuse[2] + reflective[2];
					
				}
			}
		}
		
		return rgb;
	}
	
	
	/**
	 * Method that calculates the distance between the 3D points.
	 * 
	 * @param firstPoint {@code Point3D}
	 * @param secondPoint {@code Point3D}
	 * @return {@code double} distance from first point to second
	 */
	public static double calculateDistance(Point3D firstPoint, Point3D secondPoint) {
		double xDistance = secondPoint.x - firstPoint.x;
		double yDistance = secondPoint.y - firstPoint.y;
		double zDistance = secondPoint.z - firstPoint.z;
		
		return Math.sqrt(Math.pow(xDistance, 2) + 
						 Math.pow(yDistance, 2) +
						 Math.pow(zDistance, 2));
		
	}

}
