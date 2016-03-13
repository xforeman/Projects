package part3;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * Sliders and labels for bias filter, gain filter and blur filter.
 * 
 * @author Christina Foreman
 * 
 */
public class ImageSliders extends JSlider {

	/**
	 * Instance variables
	 */
	// Sliders and their labels
	private JSlider biasFactorSlider;
	private JLabel biasLabel;
	private JSlider gainFactorSlider;
	private JLabel gainLabel;
	private JSlider blurFactorSlider;
	private JLabel blurLabel;

	/**
	 * Create the sliders for bias, gain and blur filters.
	 */
	public ImageSliders() {
		// Slider for bias filter. Ranges from -200 to 200, with tick markers
		// for every 100
		// Area for slider is small, so there are few tick marks.
		biasFactorSlider = new JSlider(-200, 200);
		biasFactorSlider
				.setToolTipText("Slide left to make the image darker. Slide right to make the image brighter");
		biasFactorSlider.setMajorTickSpacing(100);
		biasFactorSlider.setPaintTicks(false); // Do not show tick lines for
												// aesthetic purposes.
		biasFactorSlider.setPaintLabels(true); // Show tick numbers
		biasFactorSlider.setEnabled(false); // Slider is disabled
		biasLabel = new JLabel("Adjust bias factor"); // Describe slider
		biasLabel
				.setToolTipText("Adjust bias factor; Increase or decrease brightness.");
		biasLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label
		biasLabel.setEnabled(false); // Slider label is disabled

		// Slider for gain filter. Ranges from 2 to 8. State changed listener
		// multiplies these values by .25 so that the filter
		// can accept the appropriate values (need doubles but parameters only
		// accept integers).
		gainFactorSlider = new JSlider(2, 8);
		gainFactorSlider
				.setToolTipText("Slide left to decrease the contrast and brightness. Slide right to increase the contrast and brightness.");
		Hashtable<Integer, JLabel> labelTable1 = new Hashtable<Integer, JLabel>();
		labelTable1.put(2, new JLabel("0.5"));
		labelTable1.put(3, new JLabel("0.75"));
		// skip gain factor 1.0, since that produces an image the same as the
		// original
		labelTable1.put(5, new JLabel("1.25"));
		labelTable1.put(6, new JLabel("1.5"));
		labelTable1.put(7, new JLabel("1.75"));
		labelTable1.put(8, new JLabel("2.0"));
		gainFactorSlider.setLabelTable(labelTable1);
		gainFactorSlider.setPaintLabels(true);
		gainFactorSlider.setEnabled(false); // Slider is disabled
		gainLabel = new JLabel("Adjust gain factor"); // Describe slider
		gainLabel
				.setToolTipText("Adjust gain factor; Increase or decrease contrast and brightness.");
		gainLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label
		gainLabel.setEnabled(false); // Slider label is disabled

		// Slider for blur filter. Ranges from 1 to 7. 1 blurs the image by
		// using the neighboring pixels in a 3x3 block.
		// 2 blurs the image with a 5x5 block, 3 with a 7x7 block, and 4 with a
		// 9x9 block.
		blurFactorSlider = new JSlider(1, 4, 1);
		blurFactorSlider
				.setToolTipText("Slide right to make the image blurrier; Blur with a 3x3 block of neighboring pixels, "
						+ "a 5x5 block, a 7x7 block or a 9x9 block. The larger the block, the blurrier the image. "
						+ "Note that a larger block will take more time to load.");
		Hashtable<Integer, JLabel> labelTable2 = new Hashtable<Integer, JLabel>();
		labelTable2.put(1, new JLabel("3x3"));
		labelTable2.put(2, new JLabel("5x5"));
		labelTable2.put(3, new JLabel("7x7"));
		labelTable2.put(4, new JLabel("9x9"));
		blurFactorSlider.setLabelTable(labelTable2);
		blurFactorSlider.setPaintLabels(true);
		blurFactorSlider.setEnabled(false); // Slider is disabled
		blurLabel = new JLabel("Adjust blur factor"); // Describe slider
		blurLabel
				.setToolTipText("Adjust the blur factor; Amount of blur is dependent on the number of neighboring pixels used. A larger block uses more pixels, thus making the image blurrier.");
		blurLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label
		blurLabel.setEnabled(false); // Slider label is disabled

	}

	// Getters for sliders and labels

	/**
	 * Getter for biasFactorSlider
	 */
	public JSlider getBiasFactorSlider() {
		return biasFactorSlider;
	}

	/**
	 * Getter for biasLabel
	 */
	public JLabel getBiasLabel() {
		return biasLabel;
	}

	/**
	 * Getter for gainFactorSlider
	 */
	public JSlider getGainFactorSlider() {
		return gainFactorSlider;
	}

	/**
	 * Getter for gainLabel
	 */
	public JLabel getGainLabel() {
		return gainLabel;
	}

	/**
	 * Getter for blurFactorSlider
	 */
	public JSlider getBlurFactorSlider() {
		return blurFactorSlider;
	}

	/**
	 * Getter for blurLabel
	 */
	public JLabel getBlurLabel() {
		return blurLabel;
	}

}
