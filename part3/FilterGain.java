package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Changes the contrast among pixels in an image by scaling all color components
 * by X. X must be > 0. X < 1 decreases contrast and makes the image darker. X >
 * 1 increases contrast and makes the image lighter.
 * 
 * @author Erin Parker, CS 1410 class, and Christina Foreman
 */
public class FilterGain extends ImageRegionFilter {

	// Accept a double for the gain factor
	public BufferedImage filter(BufferedImage img, double f) {

		// gain factor (from slider)
		final double X = f;

		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = result.createGraphics();
		g2.drawImage(img, 0, 0, null);

		// If a region is selected
		if (selectedTrue()) {
			// For each pixel in the image
			for (int y = 0; y < img.getHeight(); y++)
				for (int x = 0; x < img.getWidth(); x++) {

					// If the pixel is within the selected region
					if (y >= getMinY() && y <= getMaxY() && x >= getMinX()
							&& x <= getMaxX()) {

						// Decompose the pixel
						int pixel = img.getRGB(x, y);
						int redAmount = (pixel >> 16) & 0xff;
						int greenAmount = (pixel >> 8) & 0xff;
						int blueAmount = (pixel >> 0) & 0xff;

						// scale each component by X, clamp to 255 as needed
						redAmount *= X;
						if (redAmount > 255)
							redAmount = 255;

						greenAmount *= X;
						if (greenAmount > 255)
							greenAmount = 255;

						blueAmount *= X;
						if (blueAmount > 255)
							blueAmount = 255;

						// Compose new pixel
						int newPixel = (redAmount << 16) | (greenAmount << 8)
								| blueAmount;

						// Set the pixel of the new image
						result.setRGB(x, y, newPixel);
					} 
					// Copy the pixels outside of the selected area without
					// changing them
					else
						result.setRGB(x, y, img.getRGB(x, y));
				}
		// If there is no region selected, filter the entire image
		} else {
			// For each pixel in the image
			for (int y = 0; y < img.getHeight(); y++)
				for (int x = 0; x < img.getWidth(); x++) {

					// Decompose the pixel
					int pixel = img.getRGB(x, y);
					int redAmount = (pixel >> 16) & 0xff;
					int greenAmount = (pixel >> 8) & 0xff;
					int blueAmount = (pixel >> 0) & 0xff;

					// scale each component by X, clamp to 255 as needed
					redAmount *= X;
					if (redAmount > 255)
						redAmount = 255;

					greenAmount *= X;
					if (greenAmount > 255)
						greenAmount = 255;

					blueAmount *= X;
					if (blueAmount > 255)
						blueAmount = 255;

					// Compose new pixel
					int newPixel = (redAmount << 16) | (greenAmount << 8)
							| blueAmount;

					// Set the pixel of the new image
					result.setRGB(x, y, newPixel);
				}
		}
		return result;
	}

	@Override
	public BufferedImage filter(BufferedImage i, int f) {
		// Do nothing
		return null;
	}

	@Override
	public BufferedImage filter(BufferedImage i) {
		// Do nothing
		return null;
	}
}
