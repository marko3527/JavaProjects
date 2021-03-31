package hr.fer.zemris.java.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;

class RayCasterTest {
	
	Point3D eye = new Point3D(10, 0, 0);
	Point3D view = new Point3D(0, 0, 0);
	Point3D viewUp = new Point3D(0, 0, 10);
	int width = 500;
	int height = 500;
	int horizontal = 20;
	int vertical = 20;
	Point3D axis[] = RayCasterUtil.calculateAxis(eye, view, viewUp);
	Point3D xAxis = axis[0];
	Point3D yAxis = axis[1];

	@Test
	void pointsTest() {
		
		int[] xValue = {
			0, 166, 250, 333, 499, 0, 166, 250, 333, 499, 0, 166,
			250, 333, 499, 0, 166, 250, 333, 499, 0, 166, 250, 333,
			499
		};

		int[] yValue = {
			0, 0, 0, 0, 0, 166, 166, 166, 166, 166, 250, 250, 250, 250,
			250, 333, 333, 333, 333, 333, 499, 499, 499, 499, 499
		};
		
		String[] results = {
				"Screen-point: (0.000000, -10.000000, 10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.577350, -0.577350, 0.577350)",
				"Screen-point: (0.000000, -3.346693, 10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, -0.230287, 0.688102)",
				"Screen-point: (0.000000, 0.020040, 10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.707106, 0.001417, 0.707106)",
				"Screen-point: (0.000000, 3.346693, 10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, 0.230287, 0.688102)",
				"Screen-point: (0.000000, 10.000000, 10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.577350, 0.577350, 0.577350)",
				"Screen-point: (0.000000, -10.000000, 3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, -0.688102, 0.230287)",
				"Screen-point: (0.000000, -3.346693, 3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.903874, -0.302499, 0.302499)",
				"Screen-point: (0.000000, 0.020040, 3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.948301, 0.001900, 0.317367)",
				"Screen-point: (0.000000, 3.346693, 3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.903874, 0.302499, 0.302499)",
				"Screen-point: (0.000000, 10.000000, 3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, 0.688102, 0.230287)",
				"Screen-point: (0.000000, -10.000000, -0.020040)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.707106, -0.707106, -0.001417)",
				"Screen-point: (0.000000, -3.346693, -0.020040)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.948301, -0.317367, -0.001900)",
				"Screen-point: (0.000000, 0.020040, -0.020040)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.999996, 0.002004, -0.002004)",
				"Screen-point: (0.000000, 3.346693, -0.020040)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.948301, 0.317367, -0.001900)",
				"Screen-point: (0.000000, 10.000000, -0.020040)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.707106, 0.707106, -0.001417)",
				"Screen-point: (0.000000, -10.000000, -3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, -0.688102, -0.230287)",
				"Screen-point: (0.000000, -3.346693, -3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.903874, -0.302499, -0.302499)",
				"Screen-point: (0.000000, 0.020040, -3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.948301, 0.001900, -0.317367)",
				"Screen-point: (0.000000, 3.346693, -3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.903874, 0.302499, -0.302499)",
				"Screen-point: (0.000000, 10.000000, -3.346693)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, 0.688102, -0.230287)",
				"Screen-point: (0.000000, -10.000000, -10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.577350, -0.577350, -0.577350)",
				"Screen-point: (0.000000, -3.346693, -10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, -0.230287, -0.688102)",
				"Screen-point: (0.000000, 0.020040, -10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.707106, 0.001417, -0.707106)",
				"Screen-point: (0.000000, 3.346693, -10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.688102, 0.230287, -0.688102)",
				"Screen-point: (0.000000, 10.000000, -10.000000)\nRay: start=(10.000000, 0.000000, 0.000000), direction=(-0.577350, 0.577350, -0.577350)",
				
		};
			
		for(int i = 0; i < xValue.length; i++) {
			assertEquals(results[i], calculateRay(xValue[i], yValue[i]));
		}
				
	}
		
	
	
	
	private String calculateRay(int x, int y) {
		Point3D screenCorner = RayCasterUtil.calculateScreenCorner(view, xAxis, yAxis, horizontal, vertical);
		Point3D screenPoint = RayCasterUtil.calculateScreenPoint(xAxis, yAxis, x, y, width, height, horizontal, vertical, screenCorner);
		Ray ray = Ray.fromPoints(eye, screenPoint);
			
		
		StringBuilder sb = new StringBuilder();
		sb.append("Screen-point: (");
		sb.append(String.format("%.6f, %.6f, %.6f)", screenPoint.x, screenPoint.y, screenPoint.z));
		sb.append("\nRay: start=(");
		sb.append(String.format("%.6f, %.6f, %.6f)", ray.start.x, ray.start.y, ray.start.z));
		sb.append(String.format(", direction=(%.6f, %.6f, %.6f)", ray.direction.x, ray.direction.y, ray.direction.z));
		return sb.toString();
	}
	

}
