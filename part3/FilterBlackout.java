package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Changes the pixels with a high amount of red and turns the pixel black (by
 * changing RGB values to 0).
 * 
 * @author Erin Parker and Christina Foreman.
 */
public class FilterBlackout extends ImageRegionFilter {

	public BufferedImage filter(BufferedImage img) {

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

						int pixel = img.getRGB(x, y);

						// Decompose the pixel in the amounts of red, green, and
						// blue.
						int redAmount = (pixel >> 16) & 0xff;
						int greenAmount = (pixel >> 8) & 0xff;
						int blueAmount = (pixel >> 0) & 0xff;

						// If the amount of red is high, set the color of the
						// pixel to
						// BLACK.
						if (redAmount > 135) {
							redAmount = 0;
							greenAmount = 0;
							blueAmount = 0;
						}
						// Compose the new pixel.
						int newPixel = (redAmount << 16) | (greenAmount << 8)
								| blueAmount;

						// Set the pixel of the new image.
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

					int pixel = img.getRGB(x, y);

					// Decompose the pixel in the amounts of red, green, and
					// blue.
					int redAmount = (pixel >> 16) & 0xff;
					int greenAmount = (pixel >> 8) & 0xff;
					int blueAmount = (pixel >> 0) & 0xff;

					// If the amount of red is high, set the color of the pixel
					// to
					// BLACK.
					if (redAmount > 135) {
						redAmount = 0;
						greenAmount = 0;
						blueAmount = 0;
					}

					// Compose the new pixel.
					int newPixel = (redAmount << 16) | (greenAmount << 8)
							| blueAmount;

					// Set the pixel of the new image.
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
	public BufferedImage filter(BufferedImage i, double f) {
		// Do nothing
		return null;
	}

}
