package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Web worker that draws the circle in the middle of the page
 * It is mapped to the /cw
 * 
 * @author Marko
 *
 */
public class CircleWorker implements IWebWorker {

	
	/**
	 * {@inheritDoc}
	 * CircleWorker creates a new image of 200 width * 200 height and
	 * draws a green circle over that whole image. As background of that
	 * image worker draws a black rectangle.
	 * 
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("image/png");
		
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		g2d.setColor(Color.green);
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
