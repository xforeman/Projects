package part3;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Creates a blurry image. Takes the individual average of each color in a pixel
 * and its neighboring pixels (to the NW, N, NE, W, E, SW, S, SE), and creates a
 * new pixel with these new averaged RGB values.
 * 
 * @author Christina Foreman.
 */
public class FilterBlur extends ImageRegionFilter {

	private int redAmount;
	private int greenAmount;
	private int blueAmount;
	private int redAverage;
	private int greenAverage;
	private int blueAverage;

	// Accept an integer for the blur factor
	public BufferedImage filter(BufferedImage img, int blurFactor) {

		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = result.createGraphics();
		g2.drawImage(img, 0, 0, null);

		// If a region is selected
		if (selectedTrue()) {

			// For each pixel in the image . . .
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {

					// If the pixel is within the selected region
					if (y >= getMinY() && y <= getMaxY() && x >= getMinX()
							&& x <= getMaxX()) {

						// Get total red, green and blue amounts from the origin
						// pixel
						// and its neighbors.
						// Red from each pixel is added up, green from each
						// pixel is
						// added up, blue from each pixel is added up
						int pixel;
						int xF;
						int yF;

						int pixelCount = 1;

						// blurFactor is taken from the blur slider (dependent
						// on user)
						// Cycle through each pixel in the block
						for (xF = -blurFactor; xF < (blurFactor * 2); xF++) {
							for (yF = -blurFactor; yF < (blurFactor * 2); yF++) {
								if ((x + xF < img.getWidth())
										&& (y + yF < img.getHeight())
										&& (x + xF >= 0) && (y + yF >= 0)) { // in-bounds
																				// check
									pixel = img.getRGB(x + xF, y + yF);
									redAmount = (pixel >> 16) & 0xff; // get red
									greenAmount = (pixel >> 8) & 0xff; // get green
									blueAmount = (pixel >> 0) & 0xff;// get blue
									redAverage = redAverage + redAmount; 
									// add red to the average
									greenAverage = greenAverage + greenAmount; 
									// add green to the average
									blueAverage = blueAverage + blueAmount; 
									// add blue to the average
									pixelCount++; // add the number of relevant
													// pixels
								}
							}
						}
						// Color amounts from each pixel are averaged out to
						// create a
						// new red, green and blue for the new pixel.
						redAverage = redAverage / pixelCount; //
						greenAverage = greenAverage / pixelCount;
						blueAverage = blueAverage / pixelCount;

						// Compose the new pixel.
						int newPixel = (redAverage << 16) | (greenAverage << 8)
								| (blueAverage);

						// Set the pixel of the new image.
						result.setRGB(x, y, newPixel);
					} 
					// Copy the pixels outside of the selected area without
					// changing them
					else
						result.setRGB(x, y, img.getRGB(x, y));
				}
			}
		// If there is no region selected, filter the entire image
		} else {
			// For each pixel in the image . . .
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {

					// Get total red, green and blue amounts from the origin
					// pixel
					// and its neighbors.
					// Red from each pixel is added up, green from each pixel is
					// added up, blue from each pixel is added up
					int pixel;
					int xF;
					int yF;

					int pixelCount = 1;

					// blurFactor is taken from the blur slider (dependent on
					// user)
					// Cycle through each pixel in the block
					for (xF = -blurFactor; xF < (blurFactor * 2); xF++) {
						for (yF = -blurFactor; yF < (blurFactor * 2); yF++) {
							if ((x + xF < img.getWidth())
									&& (y + yF < img.getHeight())
									&& (x + xF >= 0) && (y + yF >= 0)) { // in-bounds
																			// check
								pixel = img.getRGB(x + xF, y + yF);
								redAmount = (pixel >> 16) & 0xff; // get red
								greenAmount = (pixel >> 8) & 0xff; // get green
								blueAmount = (pixel >> 0) & 0xff;// get blue
								redAverage = redAverage + redAmount; 
								// add red to the average
								greenAverage = greenAverage + greenAmount; 
								// add green to the average
								blueAverage = blueAverage + blueAmount; 
								// add blue to the average
								pixelCount++; // add the number of relevant
												// pixels
							}
						}
					}
					// Color amounts from each pixel are averaged out to create
					// a
					// new red, green and blue for the new pixel.
					redAverage = redAverage / pixelCount; //
					greenAverage = greenAverage / pixelCount;
					blueAverage = blueAverage / pixelCount;

					// Compose the new pixel.
					int newPixel = (redAverage << 16) | (greenAverage << 8)
							| (blueAverage);

					// Set the pixel of the new image.
					result.setRGB(x, y, newPixel);
				}
			}
		}
		return result;
	}

	@Override
	public BufferedImage filter(BufferedImage i) {
		// Do nothing
		return null;
	}

	@Override
	public BufferedImage filter(BufferedImage i, double f) {
		// Do nothing
		return null;
	}
}
