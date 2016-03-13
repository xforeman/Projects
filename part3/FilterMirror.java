package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Mirrors the image, one pixel at a time. RGB in pixel remains the same, pixel just changes location.
 * New x-value is (greatest original x-coordinate) - original x-coordinate. 
 * New y-value stays the same.
 * 
 * @author Christina Foreman
 * 
 */
public class FilterMirror extends ImageRegionFilter  {

	public BufferedImage filter(BufferedImage img) {

		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = result.createGraphics();
		g2.drawImage(img, 0, 0, null);

			// For each pixel in the image
			for (int y = 0; y < img.getHeight(); y++)
				for (int x = 0; x < img.getWidth(); x++) {
		
						// Set up new x and y coordinates
						int newX = (img.getWidth() - x) -1;
						int newY = y;
		
						// Original pixel will be copied exactly. Only the coordinate
						// changes.
						int pixel = img.getRGB(x, y);
		
						// Set the pixel of the new image with newX and newY
						// coordinates.
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
