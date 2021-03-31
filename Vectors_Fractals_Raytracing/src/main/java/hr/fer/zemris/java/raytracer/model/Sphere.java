package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.RayCasterUtil;

/**
 * Class that models the graphical object, sphere. It offers the 
 * implementation for getting the closest ray intersection with this object.
 * Closest ray intersection is the one that is closest to viewers eye.
 * With sphere there will be always two outer intersections, but by determining
 * the distance between the viewers position and intersections we can define which 
 * is closer.
 * 
 * @author Marko
 *
 */
public class Sphere extends GraphicalObject{
	
	
	/**
	 * Class that implements the ray intersection with sphere
	 * 
	 * @author Marko
	 *
	 */
	static class SphereRayIntersection extends RayIntersection{
		
		
		private Sphere sphere;
		/**
		 * Constructor.
		 * 
		 * @param point {@code Point3D} intersection point
		 * @param distance {@code double} distance from viewer
		 * @param outer
		 */
		public SphereRayIntersection(Point3D point, double distance, boolean outer, Sphere sphere) {
			super(point, distance, outer);
			this.sphere = sphere;
		}

		@Override
		public Point3D getNormal() {
			Point3D point = getPoint();
			double x = (point.x - sphere.center.x)/sphere.radius;
			double y = (point.y - sphere.center.y)/sphere.radius;
			double z = (point.z - sphere.center.z)/sphere.radius;
			
			return new Point3D(x, y, z).normalize();
		}

		@Override
		public double getKdr() {
			return sphere.kdr;
		}

		@Override
		public double getKdg() {
			return sphere.kdg;
		}

		@Override
		public double getKdb() {
			return sphere.kdb;
		}

		@Override
		public double getKrr() {
			return sphere.krr;
		}

		@Override
		public double getKrg() {
			return sphere.krg;
		}

		@Override
		public double getKrb() {
			return sphere.krb;
		}

		@Override
		public double getKrn() {
			return sphere.krn;
		}
	}
	
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	
	/**
	 * Constructor.
	 * 
	 * @param center of the sphere object
	 * @param radius of the sphere object
	 * @param kdr diffuse component for red colour
	 * @param kdg diffuse component for green colour
	 * @param kdb diffuse component for blue colour
	 * @param krr reflective component for red colour
	 * @param krg reflective component for green colour
	 * @param krb reflective component for blue colour
	 * @param krn coefficient for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
				  double kdb, double krr, double krg, double krb, double krn) {
		
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
		
	}

	
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D eye = ray.start;
		Point3D vectorEyeCenter = center.sub(eye);
		
		double a = 1;
		double b = ray.direction.scalarProduct(vectorEyeCenter)*-2;
		double c = vectorEyeCenter.scalarProduct(vectorEyeCenter) - radius*radius;
		
		double[] intersections = solveQuadratic(a, b, c);
		double closestIntersectionScalar = 0;
		if(intersections == null) {
			return null;
		}
		else if(intersections.length == 1) {
			if(intersections[0] < 0) {
				return null;
			}
			closestIntersectionScalar = intersections[0];
		}
		else {
			if(intersections[1] > 0 && intersections[0] > 0) {
				closestIntersectionScalar = intersections[0] > intersections[1]? intersections[1] : intersections[0];
			}
			else if(intersections[0] < 0 && intersections[1] < 0) {
				return null;
			}
			else {
				if(intersections[1] < intersections[0]) {
					closestIntersectionScalar = intersections[1];
				}
				else {
					closestIntersectionScalar = intersections[0];
				}
			}
			
			
		}
		
		
		Point3D intersectionPoint = eye.add(
			ray.direction.scalarMultiply(closestIntersectionScalar)
		);
		double distance = RayCasterUtil.calculateDistance(ray.start, intersectionPoint);
		
		return new SphereRayIntersection(intersectionPoint, distance, true, this);
	}
	
	
	/**
	 * Method that calculates quadratic equation: ax*x + bx + c = 0.
	 * 
	 * @param a {@code a}
	 * @param b {@code b}
	 * @param c {@code c}
	 * @return array of solutions, it can be length of one or two.
	 * It returns null if there is no real solutions
	 */
	private double[] solveQuadratic(double a, double b, double c) {
		double underRoot = Math.pow(b, 2) - (4 * a * c);
		if(underRoot < 0) {
			return null;
		}
		else if(underRoot == 0) {
			return new double[] {-b/(2 * a)};
		}
		else {
			double rootValue = Math.sqrt(underRoot);
			return new double[] {
					(-b + rootValue)/(2 * a),
					(-b - rootValue)/(2 * a)
			};
		}
		
	}
	
	
	

}
