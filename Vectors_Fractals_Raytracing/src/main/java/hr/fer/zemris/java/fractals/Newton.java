package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that implements main program for calculating the fractal
 * based on Newton-Raphson iteration. User is asked to input roots 
 * and then the picture of calculated fractal is shown.
 * 
 * @author Marko
 *
 */
public class Newton {
	
	private static ComplexRootedPolynomial rootedPolynomial;
	
	public static class FractalCalc implements Callable<Void>{
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int maxIteration;
		short[] data;
		AtomicBoolean cancel;
		int offset;
		
		public FractalCalc(double reMin, double reMax, double imMin,
						   double imMax, int width, int height, int yMin, int yMax, 
						   int maxIteration, short[] data, AtomicBoolean cancel, int offset) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIteration = maxIteration;
			this.data = data;
			this.cancel = cancel;
			this.offset = offset;
		}
		

		@Override
		public Void call() throws Exception {
			new NewtonRaphson(rootedPolynomial).calculate(reMin, reMax, imMin, imMax, width, height,
									yMin, yMax, maxIteration, data, cancel,offset);
			
			return null;
		}
		
	}
	
	public static class FractalProducer implements IFractalProducer {
		private ExecutorService pool;
		private int numberOfProcessors;
		
		public FractalProducer(int numberOfProcessors) {
			this.numberOfProcessors = numberOfProcessors;
			ThreadFactory daemonicThreadFactory = new DaemonicThreadFactory();
			pool = Executors.newFixedThreadPool(numberOfProcessors, daemonicThreadFactory);
			
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, 
							int width, int height, long requestNo,
							IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int maxIteration = 16*16*16;
			short[] data = new short[width*height];
			int numberOfYForEachThread = height/(8*numberOfProcessors);
			
			List<Future<Void>> results = new ArrayList<>();
			
			int numberOfJobs = numberOfProcessors * 8;
			
			for(int i = 0; i < numberOfJobs; i++) {
				int yMin = i*numberOfYForEachThread;
				int yMax = (i+1)*numberOfYForEachThread;
				if(i == numberOfJobs - 1) {
					yMax = height;
				}
				
				int offset = i*numberOfYForEachThread*width;
				FractalCalc partialFractal = new FractalCalc(
						reMin, reMax, imMin, imMax, width, height,
						yMin, yMax, maxIteration, data, cancel,offset
				);
				results.add(pool.submit(partialFractal));
			}
			
			
			
			for(Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			observer.acceptResult(data, 
								 (short)(rootedPolynomial.toComplexPolynom().order() + 1),
								  requestNo);
			
		}
		
	}
	
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		List<Complex> roots = new LinkedList<>();
		
		String message = "Welcome to Newton-Raphson iteration-based fraction viewer.";
		message += "\nPlease enter at least two roots, one root per line. Enter 'done' when done.";
		System.out.print(message + "\n");
		
		roots = FractalUtil.readRoots(sc);
		Complex[] polyRoots = new Complex[roots.size()];
		roots.toArray(polyRoots);
		
		rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, polyRoots);
		
		System.out.print("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new FractalProducer(Runtime.getRuntime().availableProcessors()));
		
	}
	
	

}
