package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Rotates image counter-clockwise, by changing x and y coordinates of each pixel.
 * New x coordinate is the original y coordinate.
 * New y coordinate is the (greatest original x coordinate) - original x coordinate.
 *
 * @author Christina Foreman
 *
 */
public class FilterRotateCounterClockwise extends ImageRegionFilter  {
	
	public BufferedImage filter(BufferedImage img) {

		BufferedImage result = new BufferedImage(img.getHeight(),
				img.getWidth(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2 = result.createGraphics();
		g2.drawImage(img, 0, 0, null);

			// For each pixel in the image 
			for (int y = 0; y < img.getHeight(); y++)
				for (int x = 0; x < img.getWidth(); x++) {
		
						// Set up new x and y coordinates
						int newX = y;
						int newY = (img.getWidth() - x)-1;
		
						// Original pixel will be copied exactly. Only the coordinate changes.
						int pixel = img.getRGB(x, y);
		
						// Set the pixel of the new image with newX and newY coordinates.
						result.setRGB(newX, newY, pixel);
					}
		return result;
	}

	@Override
	public BufferedImage filter(BufferedImage i, int f) {
		// Do nothing
		return null;
	}

	@Override
	public BufferedImage filter(BufferedImage i, double f) {
		// Do nothing
		return null;
	}
}