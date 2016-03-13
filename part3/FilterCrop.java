package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Crops the selected region
 * 
 * @author Christina Foreman
 * 
 */
public class FilterCrop extends ImageRegionFilter {

	public BufferedImage filter(BufferedImage img) {
		int width = getMaxX() - getMinX();
		int height = getMaxY() - getMinY();
		boolean selected = false;
		BufferedImage result;

		// If a region is selected
		if (selectedTrue()) {
			// Result image is the size of the selected area. +1 keeps in bounds
			result = new BufferedImage(width + 1, height + 1,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D g2 = result.createGraphics();
			g2.drawImage(result, 0, 0, null);

			// For each pixel in the image
			// Pixels from the selected area will be copied to the result image.
			// Result image starts at 0,0 regardless of where selected area
			// begins.
			// newX and newY will only increment when a pixel is copied from the
			// selected area
			for (int y = 0, newY = 0; y < img.getHeight(); y++) {
				for (int x = 0, newX = 0; x < img.getWidth(); x++) {
					if (selected) // increment newX if within the selected area
						newX++;

					// If the pixel is within the selected region
					if (y >= getMinY() && y <= getMaxY() && x >= getMinX()
							&& x <= getMaxX()) {
						selected = true;
						int pixel = img.getRGB(x, y);

						// Set the pixel of the new image.
						result.setRGB(newX, newY, pixel);

						// when newX reaches the end of the selected area,
						// increment newY and reset newX
						if (newX >= width) {
							newY++;
							newX = 0;
						}
					} else
						selected = false; // This keeps result image's newX
											// coordinate from incrementing
				}
			}
		// If there is no region selected, filter the entire image
		} else {
			result = new BufferedImage(img.getWidth(), img.getHeight(),
					BufferedImage.TYPE_INT_RGB);

			Graphics2D g2 = result.createGraphics();
			g2.drawImage(result, 0, 0, null);

			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					int pixel = img.getRGB(x, y);

					// Set the pixel of the new image.
					result.setRGB(x, y, pixel);
				}
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
