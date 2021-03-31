package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that shows the usage of fork join parallelization 
 * on ray casting algorithm for predefined scene.
 * 
 * @author Marko
 *
 */
public class RayCasterParallel {
	
	
	public static class JobCalculation extends RecursiveAction{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int threshold;
		private int yEnd;
		private int yStart;
		private int width;
		private int offset;
		private short[] red;
		private short[] green;
		private short[] blue;
		private Scene scene;
		private Point3D eye;
		AtomicBoolean cancel;
		private Point3D xAxis;
		private Point3D yAxis;
		private int height;
		private double horizontal;
		private double vertical;
		private Point3D corner;
		
		public JobCalculation(int yStart, int yEnd, int width, short[] red,
							  short[] green, short[] blue, Scene scene, Point3D eye,
							  AtomicBoolean cancel, Point3D xAxis, Point3D yAxis,
							  int height, double horizontal, double vertical, Point3D corner) {
			this.yEnd = yEnd;
			this.yStart = yStart;
			this.width = width;
			this.offset = yStart * width;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
			this.eye = eye;
			this.cancel = cancel;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.corner = corner;
			this.threshold = height/32;
			
		}

		@Override
		public void compute() {
			if((yEnd - yStart) <= threshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new JobCalculation(yStart, (yEnd - yStart)/2 + yStart, width, red, green, blue, scene, eye,
							cancel, xAxis, yAxis, height, horizontal, vertical, corner),
					new JobCalculation((yEnd - yStart)/2 + yStart, yEnd, width, red, green, blue, scene, eye,
							cancel, xAxis, yAxis, height, horizontal, vertical, corner)
			);
			
		}
		
		public void computeDirect() {
			System.out.println("Racunam od "+yStart+" do "+yEnd);
			short[] rgb = new short[3];
			for(int y = yStart; y < yEnd; y++) {
				for(int x = 0; x < width; x++) {
					
					Point3D screenPoint = RayCasterUtil.calculateScreenPoint(
						xAxis, yAxis, x, y, width, height, horizontal, vertical,corner
					);
					
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
					tracer(scene, ray, rgb);
					
					
					red[offset] = rgb[0] > 255? 255 : rgb[0];
					green[offset] = rgb[1] > 255? 255 : rgb[1];
					blue[offset] = rgb[2] > 255? 255 : rgb[2];
					
					offset++;
				}
			}

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
				
				ForkJoinPool pool = new ForkJoinPool();
				
				pool.invoke(new JobCalculation(0, height, width, red, green, blue, scene, eye, cancel, xAxis,
							yAxis, height, horizontal, vertical, screenCorner));
				pool.shutdown();
				
				System.out.println("Calculations done ...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Call for drawing done ...");
				
			}
		};
	}

}
