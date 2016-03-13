package part3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * A JPanel for displaying a BufferedImage.
 * 
 * @author Erin Parker and Christina Foreman
 */
public class ImagePanel extends JPanel implements MouseListener,
		MouseMotionListener {

	/**
	 * Instance variables
	 */
	private BufferedImage image;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	private boolean selected;

	/**
	 * Takes a BufferedImage as a parameter, sets the size of the panel based on
	 * the image's size and adds MouseListener and MouseMotionListener to allow
	 * a region of the image to be selected.
	 * 
	 * @param BufferedImage
	 */
	public ImagePanel(BufferedImage img) {
		setLayout(new BorderLayout());
		image = img;
		setPreferredSize(new Dimension(img.getHeight(), image.getWidth()));
		image.getGraphics().drawImage(image, 0, 0, this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Paints the selected region.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(image, 0, 0, this);
		g.setColor(new Color(105, 105, 105, 125));
		g.fillRect(minX, minY, maxX - minX, maxY - minY);
	}

	/**
	 * Returns selected region's coordinates
	 */
	public Region2d getSelectedRegion() {
		return new Region2d(minX, maxX, minY, maxY);
	}

	/**
	 * Returns a boolean indicating whether there is a region selected or not.
	 */
	public boolean selectedTrue() {
		return selected;
	}

	/**
	 * Gets the region's max x and y coordinates determined by a mouse drag.
	 * Repaints. Determines whether or not there is a region selected or not. If
	 * the length of y or x in the selected region is greater than 0, then the
	 * boolean "selected" is true; If the lengths are 0 or less, the boolean
	 * "selected" is false.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		maxX = e.getX();
		maxY = e.getY();
		repaint();
		// after the mouse is dragged, check to see if a region is selected
		if ((maxY - minY > 0) || (maxX - minX > 0))
			selected = true;
		else
			selected = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * When the mouse is clicked anywhere on the original image, the selected region is cleared out.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		repaint();
		selected = false;

	}

	/**
	 * Gets the region's min x and y coordinates.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		minX = e.getX();
		minY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
