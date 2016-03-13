package part3;

import java.awt.image.BufferedImage;

/**
 * An interface that ensures all classes representing an image filter
 * implement the filter method as specified below. Allows a filter to 
 * accept integer or double arguments (for sliders), or no additional arguments
 * for filters like swapping colors.
 * 
 * @author Erin Parker and Christina Foreman
 */
public interface ImageFilter {
	
	// This is for filters that do not need an additional factor (just display the image)
	public BufferedImage filter(BufferedImage i);
	
	// Allows bias and blur filters to accept an integer argument (bias or blur factor)
	public BufferedImage filter(BufferedImage i, int f);
	
	// Allows gain filter to accept a double argument (gain factor)
	public BufferedImage filter(BufferedImage i, double f);

}