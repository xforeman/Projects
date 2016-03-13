package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Increases or decreases the brightness of an image by adding X to each color
 * component. X > 0 increases brightness. X < 0 decreases brightness.
 * 
 * @author Erin Parker, CS 1410 class and Christina Foreman.
 */
public class FilterBias extends ImageRegionFilter {

	// Accept integer for bias factor
	public BufferedImage filter(BufferedImage img, int f) {

		// Bias factor from slider (dependent on user)
		final int X = f;

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
						// Get pixel's RGB amounts
						int pixel = img.getRGB(x, y);
						int redAmount = (pixel >> 16) & 0xff;
						int greenAmount = (pixel >> 8) & 0xff;
						int blueAmount = (pixel >> 0) & 0xff;

						// Add X to redAmount.
						redAmount += X;
						// If redAmount is negative (in the case where X is
						// negative), set redAmount to 0.
						if (redAmount < 0)
							redAmount = 0;
						// If redAmount is greater than 255 after adding X, set
						// redAmount to 255.
						else if (redAmount > 255)
							redAmount = 255;

						// Add X to greenAmount, checking for negative and
						// greater than 255 (setting to min and max)
						greenAmount += X;
						if (greenAmount < 0)
							greenAmount = 0;
						else if (greenAmount > 255)
							greenAmount = 255;

						// Add X to blueAmount, checking for negative and
						// greater than 255 (setting to min and max)
						blueAmount += X;
						if (blueAmount < 0)
							blueAmount = 0;
						else if (blueAmount > 255)
							blueAmount = 255;

						// Set newPixel with the new red, green and blue
						// amounts.
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
					// Get pixel's RGB amounts
					int pixel = img.getRGB(x, y);
					int redAmount = (pixel >> 16) & 0xff;
					int greenAmount = (pixel >> 8) & 0xff;
					int blueAmount = (pixel >> 0) & 0xff;

					// Add X to redAmount.
					redAmount += X;
					// If redAmount is negative (in the case where X is
					// negative), set redAmount to 0.
					if (redAmount < 0)
						redAmount = 0;
					// If redAmount is greater than 255 after adding X, set
					// redAmount to 255.
					else if (redAmount > 255)
						redAmount = 255;

					// Add X to greenAmount, checking for negative and greater
					// than 255 (setting to min and max)
					greenAmount += X;
					if (greenAmount < 0)
						greenAmount = 0;
					else if (greenAmount > 255)
						greenAmount = 255;

					// Add X to blueAmount, checking for negative and greater
					// than 255 (setting to min and max)
					blueAmount += X;
					if (blueAmount < 0)
						blueAmount = 0;
					else if (blueAmount > 255)
						blueAmount = 255;

					// Set newPixel with the new red, green and blue amounts.
					int newPixel = (redAmount << 16) | (greenAmount << 8)
							| blueAmount;

					// Set the pixel of the new image
					result.setRGB(x, y, newPixel);
				}
		}
		return result;
	}

	@Override
	public BufferedImage filter(BufferedImage i, double f) {
		// Do nothing
		return null;
	}

	@Override
	public BufferedImage filter(BufferedImage i) {
		// Do nothing
		return null;
	}
}
