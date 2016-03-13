package part3;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menu bar containing a File menu, Filter menu, and a Filter Options menu.
 * 
 * @author Christina Foreman
 * 
 */
public class ImageMenuBar extends JMenuBar {

	/**
	 * Instance variables
	 */
	// Menu and menu items
	private JMenu filterMenu;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem filterRedGreenSwap;
	private JMenuItem filterRedBlueSwap;
	private JMenuItem filterGreenBlueSwap;
	private JMenuItem filterBlackWhite;
	private JMenuItem filterRotateClockwise;
	private JMenuItem filterRotateCounterClockwise;
	private JMenuItem filterGain;
	private JMenuItem filterBias;
	private JMenuItem filterBlur;
	private JMenuItem filterColoringBook;
	private JMenuItem filterBlackout;
	private JMenuItem filterMirror;
	private JMenuItem filterCrop;
	private JMenu filterOptionsMenu;
	private JMenuItem stackFiltersMenuItem;
	private JMenuItem removeAllMenuItem;

	/**
	 * Create the menubar containing File menu, Filter menu and Options menu and their menu items.
	 */
	public JMenuBar createMenuBar() {
		// MENUBAR, FILE MENU
		// File Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setToolTipText("Open or save an image.");
		// Open
		openItem = new JMenuItem("Open File");
		openItem.setToolTipText("Open an image");
		fileMenu.add(openItem);
		// Save
		saveItem = new JMenuItem("Save File");
		saveItem.setEnabled(false);
		saveItem.setToolTipText("Save your filtered image.");
		fileMenu.add(saveItem);
		menuBar.add(fileMenu);

		// FILTER MENU
		// Create Filter menu items, add to filter menu, set tool tip text
		filterMenu = new JMenu("Filter");
		filterMenu.setEnabled(false);
		filterMenu.setToolTipText("Select a filter to apply to your image.");
		// RedGreenSwap
		filterRedGreenSwap = new JMenuItem("Red-green Swap");
		filterMenu.add(filterRedGreenSwap);
		filterRedGreenSwap.setToolTipText("Swap red and green");
		// RedBlueSwap
		filterRedBlueSwap = new JMenuItem("Red-blue Swap");
		filterMenu.add(filterRedBlueSwap);
		filterRedBlueSwap.setToolTipText("Swap red and blue");
		// GreenBlueSwap
		filterGreenBlueSwap = new JMenuItem("Green-blue Swap");
		filterMenu.add(filterGreenBlueSwap);
		filterGreenBlueSwap.setToolTipText("Swap red and green");
		// BlackWhite
		filterBlackWhite = new JMenuItem("Black and White");
		filterMenu.add(filterBlackWhite);
		filterBlackWhite
				.setToolTipText("Set colors to black and white (grayscale)");
		// RotateClockwise
		filterRotateClockwise = new JMenuItem("Rotate Clockwise");
		filterMenu.add(filterRotateClockwise);
		filterRotateClockwise.setToolTipText("Rotate clockwise");
		// RotateCounterClockwise
		filterRotateCounterClockwise = new JMenuItem("Rotate Counter-clockwise");
		filterMenu.add(filterRotateCounterClockwise);
		filterRotateCounterClockwise.setToolTipText("Rotate Counter-clockwise");
		// Gain
		filterGain = new JMenuItem("Gain");
		filterMenu.add(filterGain);
		filterGain.setToolTipText("Gain; increase or decrease contrast");
		// Bias
		filterBias = new JMenuItem("Bias");
		filterMenu.add(filterBias);
		filterBias.setToolTipText("Bias; lighten or darken the image");
		// Blur
		filterBlur = new JMenuItem("Blur");
		filterMenu.add(filterBlur);
		filterBlur.setToolTipText("Blur the image");
		// ColoringBook
		filterColoringBook = new JMenuItem("Coloring Book");
		filterMenu.add(filterColoringBook);
		filterColoringBook.setToolTipText("Black and white; Coloring book");
		// Blackout
		filterBlackout = new JMenuItem("Blackout");
		filterMenu.add(filterBlackout);
		filterBlackout.setToolTipText("Remove red; black-out image");
		// Mirror
		filterMirror = new JMenuItem("Mirror");
		filterMenu.add(filterMirror);
		filterMirror.setToolTipText("Mirror the image");
		// Crop
		filterCrop = new JMenuItem("Crop");
		filterMenu.add(filterCrop);
		filterMirror.setToolTipText("Crop the image");

		// Add Filter menu to menuBar with tool tip text
		menuBar.add(filterMenu);
		filterMenu.setToolTipText("Select a filter for your image");

		filterOptionsMenu = new JMenu("Options");
		filterOptionsMenu.setToolTipText("Options for the filtered image");
		stackFiltersMenuItem = new JMenuItem("Stack Filters"); // Accumulate filters
		stackFiltersMenuItem.setToolTipText("Click to stack filters. Filters will be applied cumulatively. [Stacking is currently disabled]");	
		filterOptionsMenu.add(stackFiltersMenuItem);
		
		removeAllMenuItem = new JMenuItem("Remove All Filters");
		removeAllMenuItem
				.setToolTipText("Remove all filters on image; return to original image");
		removeAllMenuItem.setEnabled(false);
		filterOptionsMenu.add(removeAllMenuItem);

		menuBar.add(filterOptionsMenu);
		filterOptionsMenu.setEnabled(false);
		stackFiltersMenuItem.setEnabled(false);
		stackFiltersMenuItem
				.setToolTipText("Click to stack filters. Filters will be applied cumulatively. [Stacking is currently disabled]");

		return menuBar;
	}

	// Getters for filter menu and menu items.

	/**
	 * Getter for filterMenu
	 */
	public JMenu getFilterMenu() {
		return filterMenu;
	}

	/**
	 * Getter for openItem menu item
	 */
	public JMenuItem getOpenItem() {
		return openItem;
	}

	/**
	 * Getter for saveItem menu item
	 */
	public JMenuItem getSaveItem() {
		return saveItem;
	}

	/**
	 * Getter for filterRedGreenSwap menu item
	 */
	public JMenuItem getFilterRedGreenSwap() {
		return filterRedGreenSwap;
	}

	/**
	 * Getter for filterRedBlueSwap menu item
	 */
	public JMenuItem getFilterRedBlueSwap() {
		return filterRedBlueSwap;
	}

	/**
	 * Getter for filterGreenBlueSwap menu item
	 */
	public JMenuItem getFilterGreenBlueSwap() {
		return filterGreenBlueSwap;
	}

	/**
	 * Getter for filterBlackWhite menu item
	 */
	public JMenuItem getFilterBlackWhite() {
		return filterBlackWhite;
	}

	/**
	 * Getter for filterRotateClockwise menu item
	 */
	public JMenuItem getFilterRotateClockwise() {
		return filterRotateClockwise;
	}

	/**
	 * Getter for filterRotateCounterClockwise menu item
	 */
	public JMenuItem getFilterRotateCounterClockwise() {
		return filterRotateCounterClockwise;
	}

	/**
	 * Getter for filterGain menu item
	 */
	public JMenuItem getFilterGain() {
		return filterGain;
	}

	/**
	 * Getter for filterBias menu item
	 */
	public JMenuItem getFilterBias() {
		return filterBias;
	}

	/**
	 * Getter for filterBlur menu item
	 */
	public JMenuItem getFilterBlur() {
		return filterBlur;
	}

	/**
	 * Getter for filterColoringBook menu item
	 */
	public JMenuItem getFilterColoringBook() {
		return filterColoringBook;
	}

	/**
	 * Getter for filterBlackout menu item
	 */
	public JMenuItem getFilterBlackout() {
		return filterBlackout;
	}

	/**
	 * Getter for filterMirror menu item
	 */
	public JMenuItem getFilterMirror() {
		return filterMirror;
	}

	/**
	 * Getter for filterCrop menu item
	 */
	public JMenuItem getFilterCrop() {
		return filterCrop;
	}

	/**
	 * Getter for filterOptionsMenu
	 */
	public JMenu getFilterOptionsMenu() {
		return filterOptionsMenu;
	}

	/**
	 * Getter for stackFiltersMenuItem menu item
	 */
	public JMenuItem getStackFiltersMenuItem() {
		return stackFiltersMenuItem;
	}

	/**
	 * Setter for stackFiltersMenuItem menu item
	 */
	public void setStackFiltersMenuItem(JMenuItem stackFiltersMenuItem) {
		this.stackFiltersMenuItem = stackFiltersMenuItem;
	}

	/**
	 * Getter for removeAllMenuItem menu item
	 */
	public JMenuItem getRemoveAllMenuItem() {
		return removeAllMenuItem;
	}

}
