package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class that implements the main program for ray casting algorithm.
 * 
 * 
 * @author Marko
 *
 */
public class RayCaster {
	
	/**
	 * Main method from which program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
		new Point3D(10, 0, 0),
		new Point3D(0, 0, 0),
		new Point3D(0, 0, 10),
		20, 20);
	}

	
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Starting the calculations!");
				
				short[] red = new short[height * width];
				short[] green = new short[height * width];
				short[] blue = new short[height * width];
				
				Point3D[] axis = RayCasterUtil.calculateAxis(eye, view, viewUp);
				Point3D xAxis = axis[0];
				Point3D yAxis = axis[1];
				
				Point3D screenCorner = RayCasterUtil.calculateScreenCorner(view, xAxis, yAxis,
									   horizontal, vertical);
						
						
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						
						Point3D screenPoint = RayCasterUtil.calculateScreenPoint(
							xAxis, yAxis, x, y, width, height, horizontal, vertical,screenCorner
						);
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255? 255 : rgb[0];
						green[offset] = rgb[1] > 255? 255 : rgb[1];
						blue[offset] = rgb[2] > 255? 255 : rgb[2];
						
						offset++;
					}
				}
				
				System.out.println("Calculations done ...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Call for drawing done ...");
			}

		};
	}
	
	
	
	
	
	/**
	 * Method that cast a ray and checks whether the ray
	 * intersects with any object in scene and then colours it 
	 * appropriately.
	 * 
	 * @param scene
	 * @param ray
	 * @param rgb
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection closest = RayCasterUtil.findClosestIntersection(scene, ray);
		
		if(closest == null) {
			return;
		}
		
		short[] color = RayCasterUtil.constructColor(scene, closest, ray.direction);
		rgb[0] = color[0];
		rgb[1] = color[1];
		rgb[2] = color[2];
		
	}
	
	
	


}
