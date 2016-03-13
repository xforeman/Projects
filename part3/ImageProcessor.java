package part3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is an image filterer. User opens an image file, views it in a pane,
 * selects a filter and can view the filtered image. Valid image files that can
 * be opened: .jpg, .jpeg, .png, .bmp, .gif User can save their new, filtered
 * image, which may only be saved as .jpg.
 * 
 * @author Christina Foreman
 * 
 */
public class ImageProcessor extends JFrame implements ActionListener,
		ChangeListener, MouseListener {

	public static void main(String[] args) {
		new ImageProcessor();
	}

	/**
	 * Instance variables
	 */
	// Images
	private BufferedImage imageOriginal;
	private BufferedImage imageFiltered;
	private File imgFile;

	// Menu, sliders, panes and panels
	private ImageMenuBar menuBar;
	private ImageSliders sliders;
	private JTabbedPane pane;
	private JPanel panel;
	private ImagePanel originalPanel;
	private ImagePanel filteredPanel;
	private JPanel sliderPanel;
	
	// Filter label and array
	private JLabel filterLabel;
	private ArrayList<String> filterArray;

	// Booleans for displaying sliders
	private boolean gainOn;
	private boolean biasOn;
	private boolean blurOn;

	// For applying filters cumulatively
	private boolean stackFilters;
	private int filterCount;

	// Filter
	private ImageRegionFilter f;
	
	// JFrame as instance variable so the title can be changed
	// when an image is loaded
	private JFrame frameMain;

	/**
	 * ImageProcessor constructor opens a frame, and allows user to open an
	 * image file from the File menu. Image opens in an JTabbedPane and user can
	 * select a filter from the Filter menu. Filtered image opens in the second
	 * tab in the JTabbedPane. Sliders for gain, bias and blur filters allow
	 * user to adjust their filtered image. User can save the filtered image as
	 * a .jpg through the File menu.
	 */
	public ImageProcessor() {
		// Create the main frame. All components will be added to this.
		frameMain = new JFrame("Image Processor");
		frameMain.setLayout(new BorderLayout());

		filterArray = new ArrayList<String>();
		
		stackFilters = false; // ability to accumulate filters is disabled at
								// first
		filterCount = 0; // initially, 0 filters are applied

		// Components to be added to the main frame

		// SLIDERPANEL JPanel
		// Add sliders and their labels
		sliders = new ImageSliders();
		sliderPanel = new JPanel();
		// Corresponding labels appear below slider. Padding between sliders is
		// 30.
		sliderPanel.setLayout(new GridLayout(2, 3, 30, 0));
		sliderPanel.add(sliders.getGainFactorSlider());
		sliders.getGainFactorSlider().addChangeListener(this);
		sliderPanel.add(sliders.getBiasFactorSlider());
		sliders.getBiasFactorSlider().addChangeListener(this);
		sliderPanel.add(sliders.getBlurFactorSlider());
		sliders.getBlurFactorSlider().addChangeListener(this);
		sliderPanel.add(sliders.getGainLabel());
		sliderPanel.add(sliders.getBiasLabel());
		sliderPanel.add(sliders.getBlurLabel());
		biasOn = false;
		gainOn = false;
		blurOn = false;

		// PANEL JPanel
		// Organizes layout of second tab. Slider panel added here.
		// ImagePanel with filteredImage will be added to this when the filter
		// is applied
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(sliderPanel, BorderLayout.SOUTH); // Slider panel added to the
													// bottom.
		filterLabel = new JLabel("Filters applied: ");
		filterLabel.setToolTipText("Displays which filters are currently being applied");
		panel.add(filterLabel, BorderLayout.NORTH);
		
		// PANE JTabbedPane
		// First tab will be used to show original image (once loaded).
		// Second tab displays filtered image with sliders: contains panel with
		// the ImagePanel and sliderPanel (for the sliders)
		pane = new JTabbedPane();
		frameMain.add(pane); // Add tabbed pane

		menuBar = new ImageMenuBar();
		frameMain.setJMenuBar(menuBar.createMenuBar()); // Add menu bar to frame

		// Action listeners for menu items
		menuBar.getOpenItem().addActionListener(this);
		menuBar.getSaveItem().addActionListener(this);
		menuBar.getFilterRedGreenSwap().addActionListener(this);
		menuBar.getFilterRedBlueSwap().addActionListener(this);
		menuBar.getFilterGreenBlueSwap().addActionListener(this);
		menuBar.getFilterBlackWhite().addActionListener(this);
		menuBar.getFilterRotateClockwise().addActionListener(this);
		menuBar.getFilterRotateCounterClockwise().addActionListener(this);
		menuBar.getFilterGain().addActionListener(this);
		menuBar.getFilterBias().addActionListener(this);
		menuBar.getFilterBlur().addActionListener(this);
		menuBar.getFilterColoringBook().addActionListener(this);
		menuBar.getFilterBlackout().addActionListener(this);
		menuBar.getFilterMirror().addActionListener(this);
		menuBar.getFilterCrop().addActionListener(this);
		menuBar.getFilterOptionsMenu().addActionListener(this);
		menuBar.getStackFiltersMenuItem().addActionListener(this);
		menuBar.getRemoveAllMenuItem().addActionListener(this);

		frameMain.setPreferredSize(new Dimension(800, 800));
		frameMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frameMain.pack(); // Sized to fit in all the components
		frameMain.setVisible(true); // Display frame on startup
	}

	/**
	 * Actions to be performed when a menu item is selected. Open an image file;
	 * Save a filtered image. Filter menu items call displayFiltered() method to
	 * filter the image and display it. StackFiltersMenuItem toggles the option
	 * to accumulate filters or to apply only one filter. RemoveAllMenuItem
	 * removes the filtered pane to display only the original image.
	 */
	public void actionPerformed(ActionEvent e) {
		if (filterCount == 0 || stackFilters == false) {
			filterArray.removeAll(filterArray);
		}
		Object src = e.getSource();
		// Open image
		if (src == menuBar.getOpenItem()) {
			chooseFile(); // open dialog box, also opens file in original pane
			// Save filtered image
		} else if (src == menuBar.getSaveItem()) {
			saveFile();
		} else if (src == menuBar.getFilterMenu()) {
			toggleFilters();
			// Red-green swap
		} else if (src == menuBar.getFilterRedGreenSwap()) {
			f = new FilterRedGreenSwap();
			filterArray.add("Red-green swap");
			displayFiltered(f);
			// Red-blue swap
		} else if (src == menuBar.getFilterRedBlueSwap()) {
			f = new FilterRedBlueSwap();
			filterArray.add("Red-blue swap");
			displayFiltered(f);
			// Green-blue swap
		} else if (src == menuBar.getFilterGreenBlueSwap()) {
			f = new FilterGreenBlueSwap();
			filterArray.add("Green-blue swap");
			displayFiltered(f);
			// Black and white
		} else if (src == menuBar.getFilterBlackWhite()) {
			f = new FilterBlackWhite();
			filterArray.add("Black and White");
			displayFiltered(f);
			// Rotate clockwise
		} else if (src == menuBar.getFilterRotateClockwise()) {
			f = new FilterRotateClockwise();
			filterArray.add("Rotate Clockwise");
			displayFiltered(f);
			// Rotate counter-clockwise
		} else if (src == menuBar.getFilterRotateCounterClockwise()) {
			f = new FilterRotateCounterClockwise();
			filterArray.add("Rotate Counter-clockwise");
			displayFiltered(f);
			// Gain
		} else if (src == menuBar.getFilterGain()) {
			f = new FilterGain();
			filterArray.add("Gain");
			gainOn = true;
			displayFiltered(f);
			// Bias
		} else if (src == menuBar.getFilterBias()) {
			f = new FilterBias();
			filterArray.add("Bias");
			biasOn = true;
			displayFiltered(f);
			// Blur
		} else if (src == menuBar.getFilterBlur()) {
			f = new FilterBlur();
			filterArray.add("Blur");
			blurOn = true;
			displayFiltered(f);
			// Coloring Book
		} else if (src == menuBar.getFilterColoringBook()) {
			f = new FilterColoringBook();
			filterArray.add("Coloring Book");
			displayFiltered(f);
			// Blackout
		} else if (src == menuBar.getFilterBlackout()) {
			f = new FilterBlackout();
			filterArray.add("Blackout");
			displayFiltered(f);
			// Mirror
		} else if (src == menuBar.getFilterMirror()) {
			f = new FilterMirror();
			filterArray.add("Mirror");
			displayFiltered(f);
			// Crop
		} else if (src == menuBar.getFilterCrop()) {
			f = new FilterCrop();
			filterArray.add("Crop");
			displayFiltered(f);
			// Stack Filters
		} else if (src == menuBar.getStackFiltersMenuItem()) {
			// default disabled, including when a new file is opened.
			if (stackFilters) {
				menuBar.getStackFiltersMenuItem().setText("Enable stacking");
				menuBar.getStackFiltersMenuItem()
						.setToolTipText(
								"Click to stack filters. Filters will be applied cumulatively. [Stacking is currently disabled]");
				stackFilters = false;
			} else {
				menuBar.getStackFiltersMenuItem().setText("Disable Stacking");
				menuBar.getStackFiltersMenuItem()
						.setToolTipText(
								"Disable filter stacking: filters will no longer be applied cumulatively. [Stacking is currently enabled]");
				stackFilters = true;
			}
			filterCount = 0;
			filterArray.removeAll(filterArray);
			// Remove All Filters
		} else if (src == menuBar.getRemoveAllMenuItem()) {
			pane.remove(1);
			menuBar.getRemoveAllMenuItem().setEnabled(false);
			menuBar.getSaveItem().setEnabled(false);
			filterCount = 0; // reset stacked filters
			filterArray.removeAll(filterArray);
		}
	}

	/**
	 * Displays the filtered image in a new pane. Resets the pane if a new
	 * filter is selected. Enables Save menu item and Options menu. Disables all
	 * sliders and slider panels.
	 * 
	 * @param f
	 *            ImageFilter to implement
	 */
	public void displayFiltered(ImageRegionFilter f) {
		sliders.getGainFactorSlider().setEnabled(false);
		sliders.getBiasFactorSlider().setEnabled(false);
		sliders.getBlurFactorSlider().setEnabled(false);
		sliders.getGainLabel().setEnabled(false);
		sliders.getBiasLabel().setEnabled(false);
		sliders.getBlurLabel().setEnabled(false);
		menuBar.getRemoveAllMenuItem().setEnabled(true);
		menuBar.getSaveItem().setEnabled(true);
		f.setRegion(originalPanel.getSelectedRegion());
		//Update filter label to show which filters are being applied
		filterLabel.setText("Filters applied: " + filterArray.toString());
		// Enable sliders for appropriate filter. Filter original image if
		// stackFilters is false; Filter filteredImage if stackFilters is true.
		// Specific filters require specific arguments. All filters that do not
		// require an additional argument are in the "else" block
		if (gainOn) {
			sliders.getGainFactorSlider().setEnabled(true);
			sliders.getGainLabel().setEnabled(true);
			// filter original image if filterCount is 0 or if stackFilters is
			// disabled
			if (filterCount == 0 || stackFilters == false) {
				imageFiltered = f.filter(imageOriginal, 1.5);
				// filter the filtered image if filterCount is > 0 and
				// stackFilters is enabled
			} else if (filterCount > 0 && stackFilters == true) {
				imageFiltered = f.filter(imageFiltered, 1.5);
			}
			gainOn = false;
		} else if (biasOn) {
			sliders.getBiasFactorSlider().setEnabled(true);
			sliders.getBiasLabel().setEnabled(true);
			if (filterCount == 0 || stackFilters == false) {
				imageFiltered = f.filter(imageOriginal, 100);
			} else if (filterCount > 0 && stackFilters == true) {
				imageFiltered = f.filter(imageFiltered, 100);
			}
			biasOn = false;
		} else if (blurOn) {
			sliders.getBlurFactorSlider().setEnabled(true);
			sliders.getBlurLabel().setEnabled(true);
			if (filterCount == 0 || stackFilters == false) {
				imageFiltered = f.filter(imageOriginal, 1);
			} else if (filterCount > 0 && stackFilters == true) {
				imageFiltered = f.filter(imageFiltered, 1);
			}
			blurOn = false;
		} else { //
			if (filterCount == 0 || stackFilters == false) {
				imageFiltered = f.filter(imageOriginal);
			} else if (filterCount > 0 && stackFilters == true) {
				imageFiltered = f.filter(imageFiltered);
			}
		}
		// filteredPanel holds the filtered image
		filteredPanel = new ImagePanel(imageFiltered);
		// Need this to refresh the filtered pane when switching between
		// original and filtered
		panel.removeAll();
		// add the sliders
		panel.add(sliderPanel, BorderLayout.SOUTH);
		// add the filter label
		panel.add(filterLabel, BorderLayout.NORTH);
		// add filteredPanel which holds the filtered image
		panel.add(filteredPanel, BorderLayout.CENTER);
		filterCount++; // increment each time a filter is applied
		// Add a Filtered tab if one isn't already open
		if (pane.getTabCount() == 1)
			pane.add(panel, "Filtered Image"); // Add panel to pane
		// If the filtered tab is already open, just set the filtered pane to
		// the new filtered image
		else
			pane.setComponentAt(1, panel);
		// Enable Save menu item.
		menuBar.getSaveItem().setEnabled(true);
	}

	/**
	 * Provides actions for sliders when their state has changed. Ignores values
	 * while the slider is moving. Uses original image if stackFilters is
	 * disabled. Uses filtered image if stackFilters is enabled. Resets panel
	 * and refreshes filtered pane to display the filtered image. Increments
	 * filterCount.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider src = (JSlider) e.getSource();

		// Only take values if the slider is not moving
		if (!src.getValueIsAdjusting()) {
			int val = (int) src.getValue();
			// Bias slider
			if (sliders.getBiasFactorSlider() == e.getSource()) {
				f = new FilterBias();
				f.setRegion(originalPanel.getSelectedRegion());
				// FilterBias accepts an integer
				if (filterCount == 0 || stackFilters == false) {
					imageFiltered = f.filter(imageOriginal, val);
				} else if (filterCount > 0 && stackFilters == true) {
					imageFiltered = f.filter(imageFiltered, val);
				}
				filteredPanel = new ImagePanel(imageFiltered);
				// Gain slider
			} else if (sliders.getGainFactorSlider() == e.getSource()) {
				f = new FilterGain();
				f.setRegion(originalPanel.getSelectedRegion());
				// FilterGain accepts a double
				if (filterCount == 0 || stackFilters == false) {
					imageFiltered = f.filter(imageOriginal, val * 0.25);
				} else if (filterCount > 0 && stackFilters == true) {
					imageFiltered = f.filter(imageFiltered, val * 0.25);
				}
				filteredPanel = new ImagePanel(imageFiltered);
				// Blur slider
			} else if (sliders.getBlurFactorSlider() == e.getSource()) {
				f = new FilterBlur();
				f.setRegion(originalPanel.getSelectedRegion());
				// FilterBlur accepts an integer
				if (filterCount == 0 || stackFilters == false) {
					imageFiltered = f.filter(imageOriginal, val);
				} else if (filterCount > 0 && stackFilters == true) {
					imageFiltered = f.filter(imageFiltered, val);
				}
				filteredPanel = new ImagePanel(imageFiltered);
			}
			// Reset the panel and reset the filtered pane
			panel.removeAll();
			//Update filter label to show which filters are being applied
			filterLabel.setText("Filters applied: " + filterArray.toString());
			panel.add(filterLabel, BorderLayout.NORTH);
			panel.add(filteredPanel, BorderLayout.CENTER);
			panel.add(sliderPanel, BorderLayout.SOUTH);
			pane.setComponentAt(1, panel);
			filterCount++;
		}
	}

	/**
	 * Every time the Filter menu is moused over, originalPanel is checked for a
	 * selected region. toggleFilters enables and disables menu items
	 * accordingly.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Object src = e.getSource();
		if (src == menuBar.getFilterMenu())
			toggleFilters();
	}

	/**
	 * originalPanel checks to see if there is a region selected. If a region is
	 * selected, filterRotateClockwise, filterRotateCounterClockwise and
	 * filterMirror are disabled and filterCrop is enabled. If there is no
	 * region selected, filterRotateClockwise, filterRotateCounterClockwise and
	 * filterMirror are enabled and filterCrop is disabled.
	 */
	public void toggleFilters() {
		if (originalPanel.selectedTrue()) { // a region is selected
			menuBar.getFilterCrop().setEnabled(true);
			menuBar.getFilterRotateClockwise().setEnabled(false);
			menuBar.getFilterRotateCounterClockwise().setEnabled(false);
			menuBar.getFilterMirror().setEnabled(false);
		} else if (!originalPanel.selectedTrue()) { // no region is selected
			menuBar.getFilterCrop().setEnabled(false);
			menuBar.getFilterRotateClockwise().setEnabled(true);
			menuBar.getFilterRotateCounterClockwise().setEnabled(true);
			menuBar.getFilterMirror().setEnabled(true);
		}
	}

	/**
	 * Opens an image file (jpg, jpeg, png, bmp, gif) in a new JTabbedPane.
	 * Removes all panes if there is already a pane (or two) open, then adds a
	 * new one with the image. Enables menu items: Filter menu,
	 * stackFiltersMenuItem, removeAllMenuItem. Disables saveItem.
	 */
	public void chooseFile() {
		// Set up FileChooser
		JFileChooser chooser = new JFileChooser();
		// "File Format" box will display these extensions
		FileFilter filter = new FileNameExtensionFilter(
				"JPG, JPEG, PNG, BMP, GIF.", "jpg", "jpeg", "png", "bmp", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			imgFile = chooser.getSelectedFile();
			// Only accept .jpg, .jpeg, .png, .bmp, .gif
			if (imgFile.canRead()
					&& imgFile.getName().toLowerCase().contains(".jpg")
					|| imgFile.getName().toLowerCase().contains(".jpeg")
					|| imgFile.getName().toLowerCase().contains(".png")
					|| imgFile.getName().toLowerCase().contains(".bmp")
					|| imgFile.getName().toLowerCase().contains(".gif")) {
				try {
					BufferedImage i = ImageIO.read(imgFile);
					imageOriginal = new BufferedImage(i.getWidth(),
							i.getHeight(), BufferedImage.TYPE_INT_RGB);
					// Not sure if I use Graphics2D or Graphics
					Graphics2D g2 = imageOriginal.createGraphics();
					g2.drawImage(i, 0, 0, pane);
					// Change frame's title to display the image's file name
					frameMain.setTitle("Image Processor | " + imgFile.getName());
					menuBar.getFilterMenu().setEnabled(true);
					menuBar.getFilterOptionsMenu().setEnabled(true);
					menuBar.getStackFiltersMenuItem().setEnabled(true);
					stackFilters = false;
					filterCount = 0;
					filterArray.removeAll(filterArray);
					menuBar.getRemoveAllMenuItem().setEnabled(false);
					originalPanel = new ImagePanel(imageOriginal);
					menuBar.getFilterMenu().addMouseListener(this);

					// Create a new ImagePanel with this image
					if (pane.getTabCount() == 0) {
						pane.add("Original Image", originalPanel);
					} else { // remove all panels if there is one or more panels
								// before adding a new ImagePanel with original
								// image
						pane.removeAll();
						pane.add("Original Image", originalPanel);
					}
					menuBar.getSaveItem().setEnabled(false);
					// menuBar.getFilterCrop().setEnabled(false);

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else { // If file cannot be read, return to application
				return;
			}
		} else { // If user pushes cancel, return to application
			return;
		}
	}

	/**
	 * Writes the image to a .jpg file. If the dialog is canceled, return to
	 * program. If the file cannot be written, return to program. Appends
	 * "_copy.jpg" to file's name for user's convenience (and to avoid
	 * overwriting original file).
	 */
	public void saveFile() {
		// Set up FileChooser
		JFileChooser saver = new JFileChooser();
		// Get original file name
		String imgFileName = imgFile.getName();
		// Remove extension from original file name
		int pos = imgFileName.lastIndexOf(".");
		if (pos > 0) {
			// if "." is found (will return -1 if it is not found)
			// make a new string beginning at index 0 to the
			// position where "." is found
			imgFileName = imgFileName.substring(0, pos);
		}
		// Default file name is original file name (without extension) +
		// _copy.jpg
		saver.setSelectedFile(new File(imgFileName + "_copy.jpg"));
		// Create file filter for JPG
		saver.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				else
					return (f.getName().toLowerCase().endsWith(".jpg"));
			}

			@Override
			public String getDescription() {
				return "JPG";
			}
		});
		// Show save dialog
		int returnVal = saver.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File newFile = saver.getSelectedFile();
			// if the file name does not have .jpg on it, append .jpg
			if (!saver.getFileFilter().accept(newFile)) {
				// .jpg is appended for the user
				newFile = new File(newFile.getAbsolutePath() + ".jpg");
				// Write the file
				try {
					ImageIO.write(imageFiltered, "jpg", newFile);
				} catch (IOException e) { // If there is an error writing the
											// file, return to program.
					e.printStackTrace();
					return;
				}
			} else { // If the file does have .jpg on it
				try {
					// write file as .jpg
					ImageIO.write(imageFiltered, "jpg", newFile);
				} catch (IOException e) { // If there is an error writing the
											// file, return to program.
					e.printStackTrace();
					return;
				}
			}
		} else { // If user selects cancel, return to application
			return;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not used.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not used.
	}
}
